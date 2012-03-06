package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class HelpCommand extends BaseCommand{
	public HelpCommand(PropertyGroup plugin) {
		super(plugin);
		name = "Help";
		description = "Plugin Help.";
		usage = "/property help";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("property help");
	}
		
	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(plugin.getTag() + "Plugin Help");
		sender.sendMessage(ChatColor.AQUA+"/property create <groupname> <rows> <cols> - Create new property group");
		sender.sendMessage(ChatColor.AQUA+"/property setstartpoint <groupname> - Set starting point");
		sender.sendMessage(ChatColor.AQUA+"/property createproperty <groupname> [user] - Manually create property");
	}
}
