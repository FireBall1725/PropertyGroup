package com.randrdevelopment.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.randrdevelopment.PropertyGroup;
import com.randrdevelopment.command.BaseCommand;
import com.randrdevelopment.regions.SchematicTools;

public class CreatePropertyCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public CreatePropertyCommand(PropertyGroup plugin) {
        super(plugin);
        name = "CreateProperty";
        description = "Creates a Property Manually.";
        usage = "/property createproperty <groupname> [user]";
        minArgs = 1;
        maxArgs = 2;
        identifiers.add("property createproperty");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	String propertyGroup = args[0].toLowerCase();
    	
    	if (propertyConfig.getConfigurationSection(propertyGroup) != null)
		{
    		// Verify start point exists
    		
    		
			// Lets get the row and column of the next free property..
			int qty = propertyConfig.getInt(propertyGroup+".qty");
			boolean noproperties = true;
		
			for(int i=1; i<=qty; i++){
				if (propertyConfig.getBoolean(propertyGroup+".properties."+i+".created") == false){
					noproperties = false;
				
					// Found an empty spot..
					int x = propertyConfig.getInt(propertyGroup+".startlocation.x");
					int y = propertyConfig.getInt(propertyGroup+".startlocation.y");
					int z = propertyConfig.getInt(propertyGroup+".startlocation.z");
					String worldname = propertyConfig.getString(propertyGroup+".startlocation.world");
					int width = propertyConfig.getInt(propertyGroup+".width");
					int length = propertyConfig.getInt(propertyGroup+".length");
					int spacing = propertyConfig.getInt(propertyGroup+".propertyspacing");
					int row = propertyConfig.getInt(propertyGroup+".properties."+i+".row");
					int col = propertyConfig.getInt(propertyGroup+".properties."+i+".col");
				
					x = ((width + spacing) * (row - 1)) + x;
					z = ((length + spacing) * (col - 1)) + z;
				
					SchematicTools.reload(propertyGroup, worldname, x, y, z);
				
					propertyConfig.set(propertyGroup+".properties."+i+".created", true);
					plugin.savePropertyConfig();
					
					break;
				}
			}
		
			if (noproperties){
				sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group '"+propertyGroup+"' is full.");
			} else {
				sender.sendMessage(plugin.getTag()+"Property Created");
			}
		} else {
			sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group '"+propertyGroup+"' does not exist.");
		}
    }
}
