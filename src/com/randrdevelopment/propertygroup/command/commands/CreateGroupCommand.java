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
import com.randrdevelopment.propertygroup.PropertyGroupConfig;
import com.randrdevelopment.propertygroup.command.BaseCommand;

public class CreateGroupCommand extends BaseCommand{
	private PropertyGroupConfig defaultConfig = null;
	private PropertyGroupConfig propertyConfig = null;
	
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
    	defaultConfig = plugin.getDefaultConfig();
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
    	
    	// Get default configuration from config.yml
    	propertyConfig.set(propertyGroup+".userteleport", defaultConfig.get("PropertyGroupDefaults.userteleport"));
    	propertyConfig.set(propertyGroup+".propertyspacing", defaultConfig.get("PropertyGroupDefaults.propertyspacing"));
    	propertyConfig.set(propertyGroup+".createregion", defaultConfig.get("PropertyGroupDefaults.createregion"));
   		propertyConfig.set(propertyGroup+".assignhome", defaultConfig.get("PropertyGroupDefaults.assignhome"));
   		// Get default configuration for region flags from config.yml
   		propertyConfig.set(propertyGroup+".pvp", defaultConfig.get("RegionFlagDefaults.pvp"));
   		propertyConfig.set(propertyGroup+".mob-damage", defaultConfig.get("RegionFlagDefaults.mob-damage"));
   		propertyConfig.set(propertyGroup+".mob-spawning", defaultConfig.get("RegionFlagDefaults.mob-spawning"));
   		propertyConfig.set(propertyGroup+".creeper-explosion", defaultConfig.get("RegionFlagDefaults.creeper-explosion"));
   		propertyConfig.set(propertyGroup+".ghast-fireball", defaultConfig.get("RegionFlagDefaults.ghast-fireball"));
   		propertyConfig.set(propertyGroup+".tnt", defaultConfig.get("RegionFlagDefaults.tnt"));
   		propertyConfig.set(propertyGroup+".lighter", defaultConfig.get("RegionFlagDefaults.lighter"));
   		propertyConfig.set(propertyGroup+".fire-spread", defaultConfig.get("RegionFlagDefaults.fire-spread"));
   		propertyConfig.set(propertyGroup+".lava-fire", defaultConfig.get("RegionFlagDefaults.lava-fire"));
   		propertyConfig.set(propertyGroup+".lightning", defaultConfig.get("RegionFlagDefaults.lightning"));
   		propertyConfig.set(propertyGroup+".chest-access", defaultConfig.get("RegionFlagDefaults.chest-access"));
   		propertyConfig.set(propertyGroup+".water-flow", defaultConfig.get("RegionFlagDefaults.water-flow"));
   		propertyConfig.set(propertyGroup+".lava-flow", defaultConfig.get("RegionFlagDefaults.lava-flow"));
   		propertyConfig.set(propertyGroup+".use", defaultConfig.get("RegionFlagDefaults.use"));
   		propertyConfig.set(propertyGroup+".leaf-decay", defaultConfig.get("RegionFlagDefaults.leaf-decay"));
   		propertyConfig.set(propertyGroup+".greeting", defaultConfig.get("RegionFlagDefaults.greeting"));
   		propertyConfig.set(propertyGroup+".greeting-noowner", defaultConfig.get("RegionFlagDefaults.greeting-noowner"));
   		propertyConfig.set(propertyGroup+".farewell", defaultConfig.get("RegionFlagDefaults.farewell"));
   		propertyConfig.set(propertyGroup+".priority", defaultConfig.get("RegionFlagDefaults.priority"));
   		
    	// Save the configuration
    	propertyConfig.save();
    	
    	// Set the property group so set and save can use it.
    	plugin.setPropertyName(propertyGroup);
    	
    	// Lets show the user what they need to do next
    	sender.sendMessage(plugin.getTag()+"Property group '"+propertyGroup+"' has been created.");
    	sender.sendMessage(ChatColor.AQUA + "Set start point with '/property setstartpoint'");
    	sender.sendMessage(ChatColor.AQUA + "Set rows and cols with '/property setsize <rows> <cols>'");
    	sender.sendMessage(ChatColor.AQUA + "Set advanced options with '/property set'");
    }
    
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
}