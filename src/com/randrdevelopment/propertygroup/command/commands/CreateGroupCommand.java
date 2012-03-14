package com.randrdevelopment.propertygroup.command.commands;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

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
    		return;
    	}
    	
    	// Validate that schematic file exists
    	String filename = null;
    	try {
    		File f = new File("schematics/"+propertyGroup+".schematic");
    		FileInputStream fis = new FileInputStream(f);
    		fis.close();
    		filename = "schematics/"+propertyGroup+".schematic";
    	} catch (Exception e){
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Can not find a schematic file named '"+propertyGroup+"'");
    		return;
    	}
    	
    	// Lets add the property name to the configuration
    	propertyConfig.createSection(propertyGroup);
    	
    	// Store the size of the schematic into the configuration
    	try {
    		File f = new File(filename);
    		FileInputStream fis = new FileInputStream(f);
    		NBTInputStream nbt = new NBTInputStream(fis);
    		CompoundTag backuptag = (CompoundTag) nbt.readTag();
    		Map<String, Tag> tagCollection = backuptag.getValue();

    		short width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
    		short length = (Short)getChildTag(tagCollection, "Length", ShortTag.class).getValue();
    		short height = (Short)getChildTag(tagCollection, "Height", ShortTag.class).getValue();

    		nbt.close();
    		fis.close();
				            
    		propertyConfig.set(propertyGroup+".width", width);
    		propertyConfig.set(propertyGroup+".length", length);
    		propertyConfig.set(propertyGroup+".height", height);
    	} catch (Exception e) {
    		sender.sendMessage(plugin.getTag()+ChatColor.RED+"There was an error reading the schematic file.");
    		return;
    	}
    	
    	// Store the defaults into the configuration
		propertyConfig.set(propertyGroup+".userteleport", false);
		propertyConfig.set(propertyGroup+".propertyspacing", 10);
		propertyConfig.set(propertyGroup+".createregion", false);
		propertyConfig.set(propertyGroup+".assignhome", false);
    	
    	// Save the configuration
    	plugin.savePropertyConfig();
    	
    	// Set the property group so set and save can use it.
    	plugin.setPropertyName(propertyGroup);
    	
    	// Lets show the user what they need to do next
    	sender.sendMessage(plugin.getTag()+"Property group '"+propertyGroup+"' has been added.  Configure the property group with the '/property set' command");
    }
    
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
}