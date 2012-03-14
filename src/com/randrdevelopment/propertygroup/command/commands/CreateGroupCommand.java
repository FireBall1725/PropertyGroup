package com.randrdevelopment.propertygroup.command.commands;

import java.io.File;
import java.io.FileInputStream;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.randrdevelopment.propertygroup.PropertyGroup;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class CreateGroupCommand extends BaseCommand{
	FileConfiguration propertyConfig;
	
	public CreateGroupCommand(PropertyGroup plugin) {
        super(plugin);
        name = "CreateGroup";
        description = "Create Property Group";
        usage = "/property creategroup <groupname>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("property creategroup");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
    	String propertyGroup = args[0].toLowerCase();
    	
    	Player player = null;
    	if (sender instanceof Player) {
    		player = (Player) sender;
    	}
    	
    	// Validate non-console command
    	if (player == null)
    	{
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You must be a player to create property groups");
    		return;
    	}
    	
    	// Validate permissions level
    	if (!sender.hasPermission("propertygroup.create"))
    	{
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "You do not have permission to use this command");
    		return;
    	}

    	// Validate property group exists
    	if (propertyConfig.getConfigurationSection(propertyGroup) != null)
    	{
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group '"+propertyGroup+"' already exists.");
    		return;
    	}
    	
    	// Validate that property group creation is not already in progress
    	if (plugin.getPropertyName() != null)
    	{
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"Property Group being created or modified.  Please finish that first.");
    	}
    	
    	// Validate that schematic file exists
    	try {
    		File f = new File("schematics/"+propertyGroup+".schematic");
    		FileInputStream fis = new FileInputStream(f);
    		fis.close();
    	} catch (Exception e){
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Can not find a schematic file named '"+propertyGroup+"'");
    		return;
    	}
    	
    	// Lets add the property name to the configuration
    	propertyConfig.createSection(propertyGroup);
    	plugin.savePropertyConfig();
    	
    	// Set the property group so set and save can use it.
    	plugin.setPropertyName(propertyGroup);
    	
    	// Lets show the user what they need to do next
    	sender.sendMessage(plugin.getTag()+"Property group '"+propertyGroup+"' has been added.  Configure the property group with the '/property set' command");
    }
}