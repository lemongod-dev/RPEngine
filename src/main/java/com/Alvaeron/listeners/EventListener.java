package com.Alvaeron.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;
import com.Alvaeron.player.RoleplayPlayer.Channel;

public class EventListener implements Listener {
	private Engine plugin;

	public EventListener(Engine plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onOOCChat(AsyncPlayerChatEvent event) {
		// Halt everything if the event is already cancelled
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		String message = event.getMessage();
		String format = event.getFormat();
		RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
		if(rpp.getChannel() == Channel.OOC){
			if (plugin.vault) { // Confirms vault is installed before using it for prefixes
				if (Engine.chat.getPlayerPrefix(player) != null) {
					if (!Engine.chat.getPlayerPrefix(player).equals("")) {
						format = ChatColor.translateAlternateColorCodes('&',
								"[OOC] " + Engine.chat.getPlayerPrefix(player) + " " + player.getDisplayName()
										+ ChatColor.WHITE + Engine.chat.getPlayerSuffix(player) + ": %2$s");
					} else {
						format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
					}
				} else {
					format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
				}
			} else {
				format = "[OOC] " + player.getDisplayName() + ChatColor.WHITE + ": %2$s";
			}

			if (player.hasPermission("rpengine.chat.color")) {
				message = ChatColor.translateAlternateColorCodes('&', message);
			}

			// Removes people with OOC muted
			for (Player p : event.getRecipients()) {
				if (!Engine.manager.getPlayer(p.getUniqueId()).isOOC()) {
					event.getRecipients().remove(p);
				}
			}

			event.setMessage(message);
			event.setFormat(format);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRPChat(AsyncPlayerChatEvent event) {
		// Halt everything if the event is already cancelled
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		String message = event.getMessage();
		String format = event.getFormat();
		RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
		String race = rpp.getRace();

		if (rpp.getChannel() == Channel.RP) {

			if (message.startsWith("*")) {
				message = Engine.mu.replaceIfEven(message, "\"", "\"" + ChatColor.YELLOW);
				message = Engine.mu.replaceIfOdd(message, "\"", ChatColor.WHITE + "\"");

				message = message.substring(1);
				format = ChatColor.YELLOW + rpp.getName() + " %2$s";
				if (plugin.getConfig().getBoolean("logRP")) {
					player.getServer().getConsoleSender()
							.sendMessage(ChatColor.GRAY + "[RP] " + "*" + rpp.getName() + " " + message);
				}
			} else if ((message.length() > 2) && message.substring(0, 2).equalsIgnoreCase("((")) {
				format = ChatColor.GRAY + rpp.getName() + ": %2$s";
				message = ChatColor.GRAY + message;
				if (plugin.getConfig().getBoolean("logRP")) {
					player.getServer().getConsoleSender()
							.sendMessage(ChatColor.GRAY + "[RP] " + ChatColor.GRAY + rpp.getName() + ": " + message);
				}
			} else {
				format = Engine.mu.getRaceColour(race) + rpp.getName() + ChatColor.WHITE + ": %2$s";
				message = ChatColor.WHITE + message;
				if (plugin.getConfig().getBoolean("logRP")) {
					player.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "[RP] " + ChatColor.GRAY
							+ rpp.getName() + ChatColor.WHITE + ": " + message);
				}
			}

			// Removes people who aren't in the same world
			// Removes people who aren't in range
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (player.getWorld() != p.getWorld()
						|| (p.getLocation().distance(player.getLocation()) >= plugin.getConfig().getInt("rpRange"))) {
					event.getRecipients().remove(p);
				}
			}

			event.setMessage(message);
			event.setFormat(format);
		}
	}

	@EventHandler
	public void shiftRightClick(final PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player) {
			if (!event.getRightClicked().hasMetadata("NPC")) { // Confirms the player is not a NPC
				final Player player = (Player) event.getRightClicked();
				final Player reciever = event.getPlayer();
				RoleplayPlayer rpp = Engine.manager.getPlayer(player.getUniqueId());
				if (reciever.isSneaking()) {
					Engine.card.sendCardOther(rpp, reciever);
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		long maxHealth = 20;
		if (plugin.getConfig().contains("playerHealth")) {
			maxHealth = plugin.getConfig().getLong("playerHealth");
		}
		event.getPlayer().setMaxHealth(maxHealth);
	}
}
