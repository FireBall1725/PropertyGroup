package com.randrdevelopment;

import java.util.logging.Logger;

import org.bukkit.plugin.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PropertyGroupMain extends JavaPlugin {
	Logger Log = Logger.getLogger("Minecraft");

	public void onEnable(){ 
		Log.info("[PropertyGroup] Starting Property Groups Version 1.0");
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PropertyGroupListener(), this);
		getCommand("property").setExecutor(new PropertyGroupCommands(this));
		
		loadEssentials();
		
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
}
