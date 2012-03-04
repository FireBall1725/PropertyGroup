package com.randrdevelopment;

import java.util.logging.Logger;

import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class PropertyGroup extends JavaPlugin {
	Logger Log = Logger.getLogger("Minecraft");
	private static WorldEditPlugin pluginWorldEdit;
	//private static Essentials  
	private static WorldGuardPlugin pluginWorldGuard;
	private static PropertyGroup instance;
	
	public void onEnable(){ 
		instance = this;
		Log.info("[PropertyGroup] Starting Property Groups Version 1.0");
		DirectoryStructure.setup();
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PropertyGroupListener(), this);
		getCommand("property").setExecutor(new PropertyGroupCommands(this));
		
		loadEssentials();
		loadWorldEdit();
		loadWorldGuard();
		
		Log.info("[PropertyGroup] Property Groups plugin succesfully enabled!");
	}
	 
	public void onDisable(){ 
	
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
}
