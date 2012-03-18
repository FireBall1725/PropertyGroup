package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class SaveGroupCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public SaveGroupCommand(PropertyGroup plugin) {
        super(plugin);
        name = "SaveGroup";
        description = "Saves the property group";
        usage = "/property savegroup";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("property savegroup");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	
    	// Validate permissions level
    	if (!sender.hasPermission("propertygroup.create"))
    	{
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You do not have permission to use this command");
    		return;
    	}
    	
    	// Verify that there is a property group being created
    	if (plugin.getPropertyName() == null)
    	{
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"You must first create property group");
    		return;
    	}
    	
    	String propertyGroup = plugin.getPropertyName();
    	
    	// Verify that we are not in edit mode
    	
    	
    	// If we are in edit mode, clear propertyname
    	
    	
    	// Verify the region is not being shown, if it is, hide it first
    	
    	
    	// Store Property Info
    	int Rows = propertyConfig.getInt(propertyGroup+".rows");
		int Cols = propertyConfig.getInt(propertyGroup+".cols");
		
		int i = 1;
		for(int r=1; r<=Rows; r++){
			for(int c=1; c<=Cols; c++){
				propertyConfig.set(propertyGroup+".properties."+i+".created", false);
				propertyConfig.set(propertyGroup+".properties."+i+".row", r);
				propertyConfig.set(propertyGroup+".properties."+i+".col", c);
				i++;
			}
		}
		
		// Clear out stuff
		plugin.setPropertyName(null);
		plugin.setBlockData(null);
		
		// Save Configuration File
		sender.sendMessage(plugin.getTag() + "'" + propertyGroup + "' Saved.");
		plugin.savePropertyConfig();
    }
}
