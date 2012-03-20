package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class ReloadConfigCommand extends BaseCommand{
	public ReloadConfigCommand(PropertyGroup plugin) {
        super(plugin);
        name = "Reload";
        description = "Reloads the configuration.";
        usage = "/property reload";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("property reload");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	// Validate permissions level
    	if (!sender.hasPermission("propertygroup.reload"))
    	{
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You do not have permission to use this command");
    		return;
    	}
    	
    	// Reload configuration file.
    	plugin.reloadPropertyConfig();
    	plugin.reloadConfig();
    	sender.sendMessage(plugin.getTag() + "Configuration Reloaded");
    }
}
