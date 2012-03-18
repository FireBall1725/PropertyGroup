package com.randrdevelopment.propertygroup.command.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.regions.VisualTools;

public class ShowRegionCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public ShowRegionCommand(PropertyGroup plugin) {
        super(plugin);
        name = "ShowRegion";
        description = "Shows the property region with glass.";
        usage = "/property showregion";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("property showregion");
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
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"You must first create a property group");
    		return;
    	}
    	
    	String propertyGroup = plugin.getPropertyName();
    	
    	// Verify that we are not in edit mode
    	
   	
    	// Verify Rows and Cols are set
    	
    	
    	// Verify Start Position is set
		if (propertyConfig.getConfigurationSection(propertyGroup+".startlocation") == null){
			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Start Point must be set first before you can show the region");
			return;
		}
			
		// Verify region is not already being displayed
			
			
		// Set starting points
        World world = Bukkit.getWorld(propertyConfig.getString(propertyGroup+".startlocation.world"));
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
        Set<int[]> blocks = VisualTools.showRegion(world, p1, p2);
        plugin.setBlockData(blocks);
    }
}
