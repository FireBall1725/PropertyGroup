package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.PropertyGroupConfig;
import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.regions.RegionTools;
import com.randrdevelopment.propertygroup.regions.SchematicTools;

public class DeletePropertyCommand extends BaseCommand{
	private PropertyGroupConfig propertyConfig = null;
	
	public DeletePropertyCommand(PropertyGroup plugin) {
        super(plugin);
        name = "DeleteProperty";
        description = "Deletes a property";
        usage = "/property deleteproperty <property>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("property deleteproperty");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	
    	// Validate permissions level
    	if (!sender.hasPermission("propertygroup.delete"))
    	{
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You do not have permission to use this command");
    		return;
    	}
    	
    	// Get property group name and property number
    	String regionName = args[0];
		String[] pgargs = args[0].split("-");
		if (pgargs.length != 2) {
			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property Group Name is not Valid.");
			return;
		}
		String propertyGroup = pgargs[0];
		String propertyNumber = pgargs[1];
		
		// Validate the property group exists
		if (propertyConfig.getConfigurationSection(propertyGroup) == null) {
			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property Group does not exist.");
			return;
		}
		
		// Validate the property group is created
		if (propertyConfig.getBoolean(propertyGroup + ".properties."+propertyNumber+".created") == false) {
			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property not created");
			return;
		}
    	
		// Get locations
		int x = propertyConfig.getInt(propertyGroup+".startlocation.x");
		int z = propertyConfig.getInt(propertyGroup+".startlocation.z");
		String worldname = propertyConfig.getString(propertyGroup+".startlocation.world");
		int width = propertyConfig.getInt(propertyGroup+".width");
		int length = propertyConfig.getInt(propertyGroup+".length");
		int spacing = propertyConfig.getInt(propertyGroup+".propertyspacing");
		int row = propertyConfig.getInt(propertyGroup+".properties."+propertyNumber+".row");
		int col = propertyConfig.getInt(propertyGroup+".properties."+propertyNumber+".col");
		int blocks = length * width * 256;
		
		x = ((width + spacing) * (row - 1)) + x;
		z = ((length + spacing) * (col - 1)) + z;
		
		World world = Bukkit.getWorld(worldname);
		Location l1 = new Location(world, x, 0, z);
    	Location l2 = new Location(world, x+width, 255, z+length);
    	
    	// Reload Region
    	if (SchematicTools.regen(l1, l2, blocks)) {
    		// Delete info from configuration
    		try 
    		{
    			RegionTools.deleteProtectedRegion(regionName, worldname);
    		} catch (Exception e) {
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Region could not be deleted...");
    		}
    		
    		propertyConfig.removeProperty(propertyGroup+".properties."+propertyNumber);
    		propertyConfig.set(propertyGroup+".properties."+propertyNumber+".created", false);
			propertyConfig.set(propertyGroup+".properties."+propertyNumber+".row", row);
			propertyConfig.set(propertyGroup+".properties."+propertyNumber+".col", col);
    		propertyConfig.save();
			
    		sender.sendMessage(plugin.getTag() + "Property '"+propertyGroup+"-"+propertyNumber+"' Deleted");
    	} else {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property not Deleted, internal error...");
    	}
    }
}