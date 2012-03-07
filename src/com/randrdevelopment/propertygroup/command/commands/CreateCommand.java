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

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class CreateCommand extends BaseCommand {
	FileConfiguration propertyConfig;
	
	public CreateCommand(PropertyGroup plugin) {
        super(plugin);
        name = "Create";
        description = "Creates a property group.";
        usage = "/property create <groupname> <rows> <cols>";
        minArgs = 3;
        maxArgs = 3;
        identifiers.add("property create");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	propertyConfig = plugin.getPropertyConfig();
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
    	
    	// Run Create Command
    	String propertyGroup = args[0].toLowerCase();
    	Integer Rows = Integer.parseInt(args[1]);
    	Integer Cols = Integer.parseInt(args[2]);

    	if (propertyConfig.getConfigurationSection(propertyGroup) != null){
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Property Group '"+propertyGroup+"' already exists");
    		return;
    	}
				
    	// Look for schematic file
    	String filename;
    	try {
    		File f = new File("schematics/"+propertyGroup+".schematic");
    		FileInputStream fis = new FileInputStream(f);
    		fis.close();
    		filename = "schematics/"+propertyGroup+".schematic";
    	} catch (Exception e){
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Can not find a schematic file named '"+propertyGroup+"'");
    		return;
    	}
				
    	// Parse Schematic file and Save Info
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
				            
    		// Save to configuration file...
    		propertyConfig.createSection(propertyGroup);
    		propertyConfig.set(propertyGroup+".width", width);
    		propertyConfig.set(propertyGroup+".length", length);
    		propertyConfig.set(propertyGroup+".height", height);
    		propertyConfig.set(propertyGroup+".rows", Rows);
    		propertyConfig.set(propertyGroup+".cols", Cols);
    		propertyConfig.set(propertyGroup+".qty", Rows * Cols);
		            
    		// Save default options to configuration file...
    		propertyConfig.set(propertyGroup+".userteleport", false);
    		propertyConfig.set(propertyGroup+".propertyspacing", 6);
    		propertyConfig.set(propertyGroup+".createregion", false);
    		propertyConfig.set(propertyGroup+".assignhome", false);
		            
    		// Create Properties
    		int i = 1;
    		for(int r=1; r<=Rows; r++){
    			for(int c=1; c<=Cols; c++){
    				propertyConfig.set(propertyGroup+".properties."+i+".created", false);
    				propertyConfig.set(propertyGroup+".properties."+i+".row", r);
    				propertyConfig.set(propertyGroup+".properties."+i+".col", c);
    				i++;
    			}
    		}
		            
    		// Save Configuration
    		plugin.savePropertyConfig();
		            
    		sender.sendMessage(plugin.getTag() + propertyGroup+" with "+Rows*Cols+" properties created.");
    		sender.sendMessage(ChatColor.AQUA+"use '/Property setstartpoint "+propertyGroup+"' to set the");
    		sender.sendMessage(ChatColor.AQUA+"starting position (uses your current location)");
    	} catch (Exception e) {
    		sender.sendMessage(plugin.getTag() + ChatColor.RED + "Error while creating property group, see log file...");
    		e.printStackTrace();
    	}
    }
    
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
}
