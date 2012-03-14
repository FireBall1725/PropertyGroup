package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class SetCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public SetCommand(PropertyGroup plugin) {
        super(plugin);
        name = "Set";
        description = "Set Configuration Options";
        usage = "/property set <option> <value>";
        minArgs = 2;
        maxArgs = 2;
        identifiers.add("property set");
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
    	
    	// Verify that there is a property group being created or edited
    	if (plugin.getPropertyName() == null)
    	{
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"You must first create or edit a property group");
    		return;
    	}
    	
    	// Get groupName and args
    	String setOption = args[0].toLowerCase();
    	String setValue = args[1].toLowerCase();
    	
    	String propertyGroup = plugin.getPropertyName();
    	
    	// Match option
    	if (setOption.equalsIgnoreCase("propertyspacing")){
    		// Set Property Spacing
    		try {
    			int spacing = Integer.parseInt(setValue);
    			if (spacing >= 0){
    				propertyConfig.set(propertyGroup+".propertyspacing", spacing);
    				sender.sendMessage(plugin.getTag() + "Spacing set to " + spacing);
    				plugin.savePropertyConfig();
    			} else {
    				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property spacing cannot be a negative number.");
    			}
    		} catch (Exception e) {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property spacing must be a number");
    		}
    	} else if (setOption.equalsIgnoreCase("createregion")){
    		// Set Create Region
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".createregion", true);
    			sender.sendMessage(plugin.getTag() + "Create Region Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".createregion", false);
    			sender.sendMessage(plugin.getTag() + "Create Region Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'createregion' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("userteleport")){
    		// Set User Teleport
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".userteleport", true);
    			sender.sendMessage(plugin.getTag() + "User Teleport Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".userteleport", false);
    			sender.sendMessage(plugin.getTag() + "User Teleport Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'userteleport' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("assignhome")){
    		// Set Home
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".assignhome", true);
    			sender.sendMessage(plugin.getTag() + "Assign Home Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".assignhome", false);
    			sender.sendMessage(plugin.getTag() + "Assign Home Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'assignhome' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("pvp")){
    		// Set Region Flag PVP
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".pvp", true);
    			sender.sendMessage(plugin.getTag() + "Region Flag PVP Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".pvp", false);
    			sender.sendMessage(plugin.getTag() + "Region Flag PVP Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'pvp' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("mob-damage")){
    		// Set Region Flag Mob Damage
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".mob-damage", true);
    			sender.sendMessage(plugin.getTag() + "Region Flag Mob-Damage Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".mob-damage", false);
    			sender.sendMessage(plugin.getTag() + "Region Flag Mob-Damage Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'mob-damage' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("mob-spawning")){
    		// Set Region Flag Mob Spawning
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".mob-spawning", true);
    			sender.sendMessage(plugin.getTag() + "Region Flag Mob-Spawning Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".mob-spawning", false);
    			sender.sendMessage(plugin.getTag() + "Region Flag Mob-Spawning Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'mob-spawning' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("creeper-explosion")){
    		// Set Region Flag Creeper Explosion
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".creeper-explosion", true);
    			sender.sendMessage(plugin.getTag() + "Region Flag Creeper-Explosion Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".creeper-explosion", false);
    			sender.sendMessage(plugin.getTag() + "Region Flag Creeper-Explosion Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'creeper-explosion' option.  Valid options are True and False");
    		}
    	} else if (setOption.equalsIgnoreCase("ghast-fireball")){
    		// Set Region Flag Ghast Fireball
    		if (setValue.equalsIgnoreCase("true")){
    			propertyConfig.set(propertyGroup+".ghast-fireball", true);
    			sender.sendMessage(plugin.getTag() + "Region Flag Ghast Fireball Enabled");
    			plugin.savePropertyConfig();
    		} else if (setValue.equalsIgnoreCase("false")){
    			propertyConfig.set(propertyGroup+".ghast-fireball", false);
    			sender.sendMessage(plugin.getTag() + "Region Flag Ghast Fireball Disabled");
    			plugin.savePropertyConfig();
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'ghast-fireball' option.  Valid options are True and False");
    		}
    	} else {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + setOption + " is not a valid option");
    	}
    }
}