package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.PropertyGroupConfig;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class SetSizeCommand extends BaseCommand{
	private PropertyGroupConfig propertyConfig = null;
	
	public SetSizeCommand(PropertyGroup plugin) {
        super(plugin);
        name = "SetSize";
        description = "Sets the rows and cols in the property group.";
        usage = "/property setsize <rows> <cols>";
        minArgs = 2;
        maxArgs = 2;
        identifiers.add("property setsize");
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
    	
    	
    	// Verify the region is not being shown, if it is, hide it first
    	
    	
    	// Verify that rows and cols are integers
    	int rows = 0;
    	int cols = 0;
    	
    	try {
			rows = Integer.parseInt(args[0]);
			if (rows < 0){
				sender.sendMessage(plugin.getTag() + ChatColor.RED + "Rows cannot be 0 or a negative number.");
				return;
			}
		} catch (Exception e) {
			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Rows must be a number");
			return;
		}
    	
    	try {
    		cols = Integer.parseInt(args[1]);
    		if (cols < 0){
    			sender.sendMessage(plugin.getTag() + ChatColor.RED + "Cols cannot be 0 or a negative number.");
    			return;
    		}
    	} catch (Exception e) {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Cols must be a number");
    	}
		
		propertyConfig.set(propertyGroup+".rows", rows);
		propertyConfig.set(propertyGroup+".cols", cols);
		sender.sendMessage(plugin.getTag() + "Size set, Rows = " + rows + " and Cols = " + cols);
		sender.sendMessage(ChatColor.AQUA + "use '/property showregion' to show the group size");
		propertyConfig.save();
    }
}
