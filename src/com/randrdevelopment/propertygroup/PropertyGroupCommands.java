package com.randrdevelopment.propertygroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.randrdevelopment.propertygroup.regions.SchematicTools;
import com.sk89q.worldedit.Vector;

public class PropertyGroupCommands implements CommandExecutor {
	
private PropertyGroup plugin;
    final FileConfiguration config = YamlConfiguration.loadConfiguration(DirectoryStructure.getCfgProperties());

	public PropertyGroupCommands(PropertyGroup plugin) {
		this.plugin = plugin;
	}
	
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
    
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if (commandLabel.equalsIgnoreCase("property")) {
			if (sender instanceof Player){
				if(args.length == 0){
					sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"you must type a command...");
					sender.sendMessage(ChatColor.RED+"Type /Property Help for more info...");
				}
				else
				{
					if(args[0].equalsIgnoreCase("help")){
						//"[PropertyGroup] you must type a command, type /Property Hel"
						//"----------========= Property Groups 1.0 =========----------"
						sender.sendMessage(ChatColor.GREEN+"--------========= Property Groups 1.0 =========--------");
						sender.sendMessage(ChatColor.AQUA+"Create "+ChatColor.DARK_AQUA+"<Group> <Rows> <Cols>"+ChatColor.AQUA+": Create Property Group");
						sender.sendMessage(ChatColor.AQUA+"Set: Setup Commands");
						sender.sendMessage(ChatColor.AQUA+"ListGroups: List all Property Groups");
						sender.sendMessage(ChatColor.AQUA+"List "+ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+": List all properties in group");
						sender.sendMessage(ChatColor.AQUA+"DeleteGroup: " +ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+"Delete a Property Group");
						sender.sendMessage(ChatColor.AQUA+"Delete "+ChatColor.DARK_AQUA+"<Group> <Property>"+ChatColor.AQUA+": Delete a Property");
						sender.sendMessage(ChatColor.AQUA+"AssignBlank"+ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+": Create a blank property assigned to noone");
						sender.sendMessage(ChatColor.AQUA+"Assign "+ChatColor.DARK_AQUA+"<Group> <User>"+ChatColor.AQUA+": Assign user to property");
						sender.sendMessage(ChatColor.AQUA+"TP "+ChatColor.DARK_AQUA+"<Property> "+ChatColor.AQUA+"(optional "+ChatColor.DARK_AQUA+"<user>"+ChatColor.AQUA+"): Teleport to property");
					}else if(args[0].equalsIgnoreCase("list")){
						if(args.length != 2)
						{
							sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"List command requires Property Group to lookup.");
						}
						else
						{
							// TODO: List all the properties
						}
					}else if(args[0].equalsIgnoreCase("listgroups")){
						// TODO: List all the groups
						sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA+"Property Groups:");
						int i = 0;
		                for (String s : config.getKeys(false)) {
		                	sender.sendMessage(ChatColor.AQUA+s);
		                	i++;
		                }
		                if (i == 0){
		                	sender.sendMessage(ChatColor.RED+"No Property Groups");
		                }
					}else if(args[0].equalsIgnoreCase("deletegroup")){
						// TODO: Delete Group
					}else if(args[0].equalsIgnoreCase("delete")){
						// TODO: Delete Property
					}else if(args[0].equalsIgnoreCase("assign")){
						// TODO: Assign Property
					}else if(args[0].equalsIgnoreCase("tp")){
						// TODO: Handle Teleport
					}else if(args[0].equalsIgnoreCase("create")){
						// TODO: Create Property Group...
						if (config.getConfigurationSection(args[1]) != null)
						{
							sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+args[1]+" Already Exists");
							return true;
						}
						Integer Rows = Integer.parseInt(args[2]);
						Integer Cols = Integer.parseInt(args[3]);
						String filename;
						try {
				            File f = new File("schematics/"+args[1]+".schematic");
				            FileInputStream fis = new FileInputStream(f);
				            fis.close();
				            filename = "schematics/"+args[1]+".schematic";
				        } catch (Exception e){
				        	try {
				        		File f = new File("schematics/"+args[1]+"-1.schematic");
				        		FileInputStream fis = new FileInputStream(f);
				        		fis.close();
				        		filename = "schematics/"+args[1]+"-1.schematic";
				        	} catch (Exception e1){
				        		sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"Cannot load schematic file named:");
								sender.sendMessage(ChatColor.RED+args[1]+".schematic or "+args[1]+"-1.schematic");
								return true;
				        	}
				        } 
				        try {
				        	File f = new File(filename);
				            FileInputStream fis = new FileInputStream(f);
				            NBTInputStream nbt = new NBTInputStream(fis);
				            CompoundTag backuptag = (CompoundTag) nbt.readTag();
				            Map<String, Tag> tagCollection = backuptag.getValue();

				            short width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
				            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

				            nbt.close();
				            fis.close();
     				            
				            // Save to configuration file...
				            config.createSection(args[1]);
				            config.set(args[1]+".width", width);
				            config.set(args[1]+".length", length);
				            config.set(args[1]+".rows", Rows);
				            config.set(args[1]+".cols", Cols);
				            config.set(args[1]+".qty", Rows * Cols);
				            
				            // Save default options to configuration file...
				            config.set(args[1]+".userteleport", false);
				            config.set(args[1]+".propertyspacing", 6);
				            config.set(args[1]+".createregion", false);
				            config.set(args[1]+".assignhome", false);
				            
				            // Create Properties
				            int i = 1;
				            for(int r=1; r<=Rows; r++){
				                for(int c=1; c<=Cols; c++){
				                	config.set(args[1]+".properties."+i+".created", false);
				                	config.set(args[1]+".properties."+i+".row", r);
				                	config.set(args[1]+".properties."+i+".col", c);
				                	i++;
				                }
				            }
				            
				            // Save Configuration
				            config.save(DirectoryStructure.getCfgProperties());
				            
				            sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA+"New Property Group Created: "+args[1]);
							sender.sendMessage(ChatColor.AQUA+"Width="+width+" Length="+length+" Rows="+Rows+" Cols="+Cols);

				        } catch (Exception e) {
				            e.printStackTrace();
				        }
					}else if(args[0].equalsIgnoreCase("set")){
						if (args[1] != "")
						{
							// Lets make sure the property group exists
							if (config.getConfigurationSection(args[1]) != null)
							{
								// TODO: Dont allow set start point if properties exist...
								int qty = config.getInt(args[1]+".qty");
								boolean noproperties = true;
							
								for(int i=1; i<=qty; i++){
									if (config.getBoolean(args[1]+".properties."+i+".created")){
										noproperties = false;
									}
								}
								
								if (noproperties){
									if (args[2].equalsIgnoreCase("startpoint")){
										// Set Start Point...
										Location pos = player.getLocation();
										int PosX = (int)pos.getX();
										int PosY = (int)pos.getY();
										int PosZ = (int)pos.getZ();
								
										World world = player.getWorld();
										String worldName = world.getName();
									
										config.set(args[1]+".startlocation.x", PosX);
										config.set(args[1]+".startlocation.y", PosY);
										config.set(args[1]+".startlocation.z", PosZ);
										config.set(args[1]+".startlocation.world", worldName);
									
										try {
											config.save(DirectoryStructure.getCfgProperties());
											sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA+"Start Position Saved");
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								} else {
									sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"Property Group Has Properties Created Already, Cannot modify...");
								}
							} else {
								sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"Property Group Does Not Exist...");
							}
						}
					}else if(args[0].equalsIgnoreCase("assignblank")){
						// Verify the property group is valid
						if (config.getConfigurationSection(args[1]) != null)
						{
							// Lets get the row and column of the next free property..
							int qty = config.getInt(args[1]+".qty");
							boolean error = false;
							boolean noproperties = true;
						
							for(int i=1; i<=qty; i++){
								if (config.getBoolean(args[1]+".properties."+i+".created") == false){
									noproperties = false;
								
									// Found an empty spot..
									int x = config.getInt(args[1]+".startlocation.x");
									int y = config.getInt(args[1]+".startlocation.y");
									int z = config.getInt(args[1]+".startlocation.z");
									String worldname = config.getString(args[1]+".startlocation.world");
									int width = config.getInt(args[1]+".width");
									int length = config.getInt(args[1]+".length");
									int spacing = config.getInt(args[1]+".propertyspacing");
									int row = config.getInt(args[1]+".properties."+i+".row");
									int col = config.getInt(args[1]+".properties."+i+".col");
								
									x = ((width + spacing) * (row - 1)) + x;
									z = ((length + spacing) * (col - 1)) + z;
								
									SchematicTools.reload(args[1], worldname, x, y, z);
								
									config.set(args[1]+".properties."+i+".created", true);
									try {
										config.save(DirectoryStructure.getCfgProperties());
									} catch (IOException e) {
										error = true;
										e.printStackTrace();
									}
									break;
								}
							}
						
							if (error){
								sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"Error creating property...");
							} else {
								if (noproperties){
									sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"No free properties left in this group");
								} else {
									if (error == false)
										sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA+"Property Created");
								}
							}
						} else {
							sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"Property Group Does Not Exist...");
						}
					}else{
						sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.RED+"you must type a command...");
						sender.sendMessage(ChatColor.RED+"Type /Property Help for more info...");
					}
				}
			}
		}
		return true;
	}
}