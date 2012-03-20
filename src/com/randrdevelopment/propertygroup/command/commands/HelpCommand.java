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
		//TODO: Create Help
	}
}
