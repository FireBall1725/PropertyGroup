package com.randrdevelopment.propertygroup.command.commands;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class HideRegionCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public HideRegionCommand(PropertyGroup plugin) {
        super(plugin);
        name = "HideRegion";
        description = "Hides the region";
        usage = "/property hideregion";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("property hideregion");
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
    	
			
		// Verify region is already being displayed
			
			
		// Set starting points
        World world = Bukkit.getWorld(propertyConfig.getString(propertyGroup+".startlocation.world"));    	

        Set<int[]> blocks = plugin.getBlockData();
        	
        Block newBlock;
        	
        // Hide the frame.
        for (int[] buffer : blocks) {
        	newBlock = world.getBlockAt(buffer[0], buffer[1], buffer[2]);
            newBlock.setTypeId(buffer[3]);
        }        

    }
}
