package com.randrdevelopment.propertygroup.command.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.regions.VisualTools;

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
    	Player player = null;
    	if (sender instanceof Player) {
    		player = (Player) sender;
    	}
    	
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
    	} else if (setOption.equalsIgnoreCase("startpoint")){
    		if (setValue.equalsIgnoreCase("here")){
    			// Verify we are not in edit mode
    			Location loc = player.getLocation().getBlock().getLocation();
        	
        		World world = loc.getWorld();
        		String worldName = world.getName();
    			
        		propertyConfig.set(propertyGroup+".startlocation.x", loc.getBlockX());
        		propertyConfig.set(propertyGroup+".startlocation.y", loc.getBlockY());
        		propertyConfig.set(propertyGroup+".startlocation.z", loc.getBlockZ());
        		propertyConfig.set(propertyGroup+".startlocation.world", worldName);
        	    			
        		plugin.savePropertyConfig();
        	    			
        		sender.sendMessage(plugin.getTag()+"Start Point set for property group '"+propertyGroup+"'");
    		}
    	} else if (setOption.equalsIgnoreCase("rows")){
    		// Verify we are not in edit mode
    		
    		try {
    			int spacing = Integer.parseInt(setValue);
    			if (spacing >= 0){
    				propertyConfig.set(propertyGroup+".rows", spacing);
    				sender.sendMessage(plugin.getTag() + "Rows set to " + spacing);
    				plugin.savePropertyConfig();
    			} else {
    				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Rows cannot be a negative number.");
    			}
    		} catch (Exception e) {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Rows must be a number");
    		}
    	} else if (setOption.equalsIgnoreCase("cols")){
    		// Verify we are not in edit mode
    		
    		try {
    			int spacing = Integer.parseInt(setValue);
    			if (spacing >= 0){
    				propertyConfig.set(propertyGroup+".cols", spacing);
    				sender.sendMessage(plugin.getTag() + "Cols set to " + spacing);
    				plugin.savePropertyConfig();
    			} else {
    				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Cols cannot be a negative number.");
    			}
    		} catch (Exception e) {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Cols must be a number");
    		}
    	} else if (setOption.equalsIgnoreCase("showregion")){
    		// Show Visual Region
    		if (setValue.equalsIgnoreCase("true")){
    			// Verify Rows and Cols are set
    			//if (propertyConfig.getConfigurationSection(propertyGroup+".rows") == null || propertyConfig.getConfigurationSection(propertyGroup+".cols") == null){
    			//	sender.sendMessage(plugin.getTag() + ChatColor.RED + "Rows and Cols must be set first before you can show the region");
    			//	return;
    			//}
    			
    			// Verify Start Position is set
    			if (propertyConfig.getConfigurationSection(propertyGroup+".startlocation") == null){
    				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Start Point must be set first before you can show the region");
    				return;
    			}
    			
    			// Verify region is not already being displayed
    			
    			
    			// Set starting points
                final World world = player.getWorld();
                int x = propertyConfig.getInt(propertyGroup+".startlocation.x");
                int y = propertyConfig.getInt(propertyGroup+".startlocation.y");
                int z = propertyConfig.getInt(propertyGroup+".startlocation.z");
				int width = propertyConfig.getInt(propertyGroup+".width");
				int length = propertyConfig.getInt(propertyGroup+".length");
				int rows = propertyConfig.getInt(propertyGroup+".rows");
				int cols = propertyConfig.getInt(propertyGroup+".cols");
				int spacing = propertyConfig.getInt(propertyGroup+".propertyspacing");
				
				int endx = (width * rows) + (spacing * (rows - 1)) + x;
				int endz = (length * cols) + (spacing * (cols - 1)) + z;
				
    			Location p1 = new Location(world, x, y, z);
    			Location p2 = new Location(world, endx, y, endz);    			
                
                // Show the frame.
                final Set<int[]> blocks = VisualTools.showRegion(world, p1, p2);
                plugin.setBlockData(blocks);
                
    		} else if (setValue.equalsIgnoreCase("false")){
    			// Verify region is already being displayed
    			HideFrame(player.getWorld());
    		} else {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'showregion'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'createregion'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'userteleport'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'assignhome'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'pvp'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'mob-damage'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'mob-spawning'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'creeper-explosion'.  Valid options are True and False");
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
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Invalid option for 'ghast-fireball'.  Valid options are True and False");
    		}
    	} else {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + setOption + " is not a valid option");
    	}
    }
    
    public void HideFrame(World world) {
    	Set<int[]> blocks = plugin.getBlockData();
    	
    	Block newBlock;
    	
        // Hide the frame.
        for (int[] buffer : blocks) {
        	newBlock = world.getBlockAt(buffer[0], buffer[1], buffer[2]);
            newBlock.setTypeId(buffer[3]);
        }        
    }
}