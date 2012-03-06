package com.randrdevelopment.propertygroup;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.randrdevelopment.propertygroup.command.CommandManager;
import com.randrdevelopment.propertygroup.command.commands.CreateCommand;
import com.randrdevelopment.propertygroup.command.commands.CreatePropertyCommand;
import com.randrdevelopment.propertygroup.command.commands.ListGroupsCommand;
import com.randrdevelopment.propertygroup.command.commands.ReloadConfigCommand;
import com.randrdevelopment.propertygroup.command.commands.SetConfigCommand;
import com.randrdevelopment.propertygroup.command.commands.SetStartPointCommand;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PropertyGroup extends JavaPlugin {
	Logger Log = Logger.getLogger("Minecraft");
	private static WorldEditPlugin pluginWorldEdit;
	//private static Essentials  
	private static WorldGuardPlugin pluginWorldGuard;
	private static PropertyGroup instance;
	private CommandManager commandManager;
	private FileConfiguration propertyConfig = null;
	
	public void onEnable(){ 
		instance = this;
		Log.info("[PropertyGroup] Starting Property Groups Version 1.0");
		
		DirectoryStructure.setup();
		registerCommands();
		loadEssentials();
		loadWorldEdit();
		loadWorldGuard();
		
		Log.info("[PropertyGroup] Property Groups plugin succesfully enabled!");
	}
	 
	public void onDisable(){ 
	
	}
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandManager.dispatch(sender, command, label, args, this);
    }
    
	private void loadEssentials(){
		Plugin p = this.getServer().getPluginManager().getPlugin("Essentials");
		if (p != null) {
			Log.info("[PropertyGroup] Essentials Found");
		}
		else
		{
			Log.info("[PropertyGroup] Essentials not found, Essentials Support Disabled");
		}
	}
	
	private void registerCommands() {
        commandManager = new CommandManager();
        commandManager.addCommand(new CreateCommand(this));
        commandManager.addCommand(new ListGroupsCommand(this));
        commandManager.addCommand(new ReloadConfigCommand(this));
        commandManager.addCommand(new SetConfigCommand(this));
        commandManager.addCommand(new SetStartPointCommand(this));
        commandManager.addCommand(new CreatePropertyCommand(this));
    }
	
	public static PropertyGroup getInstance() {
		return instance;
	}
	
	private void loadWorldEdit() {
		Plugin p = this.getServer().getPluginManager().getPlugin("WorldEdit");
		if (p != null && p instanceof WorldEditPlugin) {
			pluginWorldEdit = (WorldEditPlugin) p;
			//Cuboid.setWorldEdit(worldEditPlugin);
			Log.info("[PropertyGroup] WorldEdit plugin detected");
		} else {
			Log.info("[PropertyGroup] WorldEdit plugin not detected - This will cause problems...");
		}
	}
	
	private void loadWorldGuard() {
		Plugin p = this.getServer().getPluginManager().getPlugin("WorldGuard");
		if (p != null && p instanceof WorldGuardPlugin) {
			pluginWorldGuard = (WorldGuardPlugin) p;
			Log.info("[PropertyGroup] WorldGuard plugin detected");
		} else {
			Log.info("[PropertyGroup] WorldGuard plugin not detected, Region Support Disabled");
		}
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return pluginWorldEdit;
	}
	
	public static WorldGuardPlugin getWorldGuard() {
		return pluginWorldGuard;
	}

	public String getTag() {
		String tag = ChatColor.GREEN+"[PropertyGroup] "+ChatColor.AQUA;
		return tag;
	}
	
	public FileConfiguration getPropertyConfig() {
	    if (propertyConfig == null) {
	        reloadPropertyConfig();
	    }
	    return propertyConfig;
	}
	
	public void reloadPropertyConfig() {
	    propertyConfig = YamlConfiguration.loadConfiguration(DirectoryStructure.getCfgProperties());
	}
	
	public void savePropertyConfig() {
	    if (propertyConfig == null) {
	    	return;
	    }
	    try {
	        propertyConfig.save(DirectoryStructure.getCfgProperties());
	    } catch (IOException ex) {
	        //Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
}
