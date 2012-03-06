package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class ReloadConfigCommand extends BaseCommand{
	public ReloadConfigCommand(PropertyGroup plugin) {
        super(plugin);
        name = "ReloadConfig";
        description = "Reloads the configuration.";
        usage = "/property reloadconfig";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("property reloadconfig");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	plugin.reloadPropertyConfig();
    	sender.sendMessage(plugin.getTag() + "Configuration Reloaded");
    }
}
