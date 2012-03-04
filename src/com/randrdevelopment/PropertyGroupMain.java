package com.randrdevelopment;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PropertyGroupMain extends JavaPlugin {
	Logger Log = Logger.getLogger("Minecraft");

	public void onEnable(){ 
		Log.info("[PropertyGroup] Starting Property Groups Version 1.0");
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PropertyGroupListener(), this);
		getCommand("property").setExecutor(new PropertyGroupCommands(this));
		Log.info("[PropertyGroup] Property Groups plugin succesfully enabled!");
	}
	 
	public void onDisable(){ 
	
	}
}
