package com.Alvaeron.utils;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.Alvaeron.Engine;
import com.Alvaeron.player.RoleplayPlayer;

public class Card {
	private Engine plugin;

	public Card(Engine plugin) {
		this.plugin = plugin;
	}

	public void sendCard(RoleplayPlayer rpp) {
		rpp.getPlayer().sendMessage(Lang.CARD_OWN.toString());
		rpp.getPlayer().sendMessage(Lang.CARD_CLICK_TO_EDIT_FIELDS.toString());
		sendJson(rpp.getPlayer(), "[\"\",{\"text\":\"" + Lang.CARD_FIELD_NAME.toString() + ": \",\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name " + rpp.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}},{\"text\":\"" + rpp.getName() + "\",\"color\":\"white\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/card name " + rpp.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",\"color\":\"aqua\"}]}}}]");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_AGE.toString() + ": \", color:\"green\", extra:[{text:\"" + rpp.getAge() + "\",color:\"white\"}],hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",color:\"aqua\"}},clickEvent:{action:\"suggest_command\",value:\"/card age " + rpp.getAge() + "\"}}");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_GENDER.toString() + ": \", color:\"green\", extra:[{text:\"" + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().name().toUpperCase()) + "\",color:\"white\"}],hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card gender\"}}");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_RACE.toString() + ": \", color:\"green\", extra:[{text:\"" + rpp.getRace() + "\",color:\"white\"}],hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card race\"}}");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_NATION.toString() + ": \", color:\"green\", extra:[{text:\"" + rpp.getNation() + "\",color:\"white\"}],hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card nation\"}}");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_DESC.toString() + ": \", color:\"green\", extra:[{text:\"" + rpp.getDesc() + "\",color:\"white\"}],hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_EDIT.toString() + "\",color:\"aqua\"}},clickEvent:{action:\"suggest_command\",value:\"/card desc " + rpp.getDesc() + "\"}}");
	}

	public void sendCardOther(RoleplayPlayer rpp, Player reciever) {
		reciever.sendMessage(Lang.CARD_OTHERS.toString().replace("%p", rpp.getPlayer().getName()));
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NAME.toString() + ": " + ChatColor.WHITE + rpp.getName());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_AGE.toString() + ": " + ChatColor.WHITE + rpp.getAge());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_GENDER.toString() + ": " + ChatColor.WHITE + Lang.valueOf("CARD_FIELD_GENDER_" + rpp.getGender().getName().toUpperCase()).toString());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_RACE.toString() + ": " + ChatColor.WHITE + rpp.getRace());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_NAME.toString() + ": " + ChatColor.WHITE + rpp.getNation());
		reciever.sendMessage(ChatColor.GREEN + Lang.CARD_FIELD_DESC.toString() + ": " + ChatColor.WHITE + rpp.getDesc());
	}

	public void sendGenderSelect(RoleplayPlayer rpp) {
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_GENDER.toString());
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_GENDER_MALE.toString() + "\", color:\"blue\",hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_MALE.toString()) + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card gender male\"}}");
		sendJson(rpp.getPlayer(), "{text:\"" + Lang.CARD_FIELD_GENDER_FEMALE.toString() + "\", color:\"light_purple\",hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_GENDER.toString().replace("%g", Lang.CARD_FIELD_GENDER_FEMALE.toString()) + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card gender female\"}}");
	}

	public void sendRaces(RoleplayPlayer rpp) {
		final Set<String> races = plugin.getConfig().getConfigurationSection("Races").getKeys(false);
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_RACE.toString());
		for (String race : races) {
			sendJson(rpp.getPlayer(), "{text:\"" + race + "\", color:\"" + plugin.getConfig().getString("Races." + race + ".Color") + "\",hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_RACE.toString().replace("%r", race) + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card race " + race + "\"}}");
		}
	}

	public void sendNations(RoleplayPlayer rpp) {
		final Set<String> nations = plugin.getConfig().getConfigurationSection("Nations").getKeys(false);
		rpp.getPlayer().sendMessage(Lang.CARD_SELECT_RACE.toString());
		for (String nation : nations) {
			sendJson(rpp.getPlayer(), "{text:\"" + nation + "\", color:\"white\",hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_NATION.toString().replace("%n", nation) + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card nation " + nation + "\"}}");
		}
	}
	public void clickToSendCard(Player p){
		sendJson(p, "{text:\"" + Lang.CARD_CLICK_TO_SHOW.toString() + "\", color:\"aqua\",hoverEvent:{action:show_text,value:{text:\"" + Lang.CARD_CLICK_TO_SHOW + "\",color:\"aqua\"}},clickEvent:{action:\"run_command\",value:\"/card\"}}");
	}
	public void sendJson(Player p, String json){
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " " + json);
	}
}
