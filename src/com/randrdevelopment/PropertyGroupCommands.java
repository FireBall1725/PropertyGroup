package com.randrdevelopment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.randrdevelopment.regions.SchematicTools;

public class PropertyGroupCommands implements CommandExecutor {
	
private PropertyGroup plugin;

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
						sender.sendMessage(ChatColor.AQUA+"Create "+ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+": Create Property Group");
						sender.sendMessage(ChatColor.AQUA+"Set: Setup Commands");
						sender.sendMessage(ChatColor.AQUA+"ListGroups: List all Property Groups");
						sender.sendMessage(ChatColor.AQUA+"List "+ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+": List all properties in group");
						sender.sendMessage(ChatColor.AQUA+"DeleteGroup: " +ChatColor.DARK_AQUA+"<Group>"+ChatColor.AQUA+"Delete a Property Group");
						sender.sendMessage(ChatColor.AQUA+"Delete "+ChatColor.DARK_AQUA+"<Group> <Property>"+ChatColor.AQUA+": Delete a Property");
						sender.sendMessage(ChatColor.AQUA+"Assign "+ChatColor.DARK_AQUA+"<Group> <User>"+ChatColor.AQUA+": Assign user to property");
						sender.sendMessage(ChatColor.AQUA+"TP "+ChatColor.DARK_AQUA+"<Property> "+ChatColor.AQUA+"(optional "+ChatColor.DARK_AQUA+"<user>"+ChatColor.AQUA+"): Teleport to property");
						sender.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
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

				            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
				            byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

				            List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
				            List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();

				            nbt.close();
				            fis.close();
				            
				            
				            
				            sender.sendMessage(ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA+"New Property Group Created "+args[1]);
							sender.sendMessage(ChatColor.AQUA+"Width="+width+" Length="+length);

				        } catch (Exception e) {
				            e.printStackTrace();
				        }
					}else if(args[0].equalsIgnoreCase("test")){
						SchematicTools.reload(player, args[1]);
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