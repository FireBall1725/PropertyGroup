package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class SetStartPointCommand extends BaseCommand{
	FileConfiguration propertyConfig;

	public SetStartPointCommand(PropertyGroup plugin) {
        super(plugin);
        name = "SetStartPoint";
        description = "Sets Property Group Start Point.";
        usage = "/property setstartpoint <groupname>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("property setstartpoint");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	String propertyGroup = args[0].toLowerCase();
    	
    	if (sender instanceof Player) {
    	    if (sender.hasPermission("propertygroup.create"))
    	    {
    	    	// Check to make sure property group exists
    	    	if (propertyConfig.getConfigurationSection(propertyGroup) != null)
    	    	{
    	    		// Verify there are no properties
    	    		int qty = propertyConfig.getInt(propertyGroup+".qty");
    	    		boolean noProperties = true;
    		
    	    		for (int i=1; i<qty; i++){
    	    			if (propertyConfig.getBoolean(propertyGroup+".properties."+i+".created")){
    	    				noProperties = false;
    	    			}
    	    		}
    		
    	    		if (noProperties){
    	    			Player player = (Player) sender;
    	    			Location loc = player.getLocation().getBlock().getLocation();
            	
    	    			World world = loc.getWorld();
    	    			String worldName = world.getName();
			
    	    			propertyConfig.set(propertyGroup+".startlocation.x", loc.getBlockX());
    	    			propertyConfig.set(propertyGroup+".startlocation.y", loc.getBlockY());
    	    			propertyConfig.set(propertyGroup+".startlocation.z", loc.getBlockZ());
    	    			propertyConfig.set(propertyGroup+".startlocation.world", worldName);
    	    			
    	    			plugin.savePropertyConfig();
    	    			
    	    			sender.sendMessage(plugin.getTag()+"Start Point set for property group '"+propertyGroup+"'");
    	    		} else {
    	    			sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group '"+propertyGroup+"' can not be set, already has properties");
    	    		}
    	    	} else {
    	    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group '"+propertyGroup+"' does not exist.");
    	    	}
    	    } else {
    	    	sender.sendMessage(plugin.getTag() + ChatColor.RED + "You do not have permission to use this command");
    	    }
    	} else {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You must be a player to create property groups");
    	}   	
    }
}
