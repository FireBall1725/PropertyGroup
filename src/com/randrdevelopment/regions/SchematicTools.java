package com.randrdevelopment.regions;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.randrdevelopment.DirectoryStructure;
import com.randrdevelopment.PropertyGroup;
import com.randrdevelopment.log.PropertyGroupLogger;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.FilenameException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class SchematicTools {
	private WorldEditPlugin wep;
	private WorldEdit we;
	private File saveFile;
	private LocalPlayer localPlayer;
	private EditSession editSession;
	private LocalSession localSession;
	
	private SchematicTools () {
		wep = PropertyGroup.getWorldEdit();
		if (wep == null) {
			return;
		}
		we = wep.getWorldEdit();	
	}
	
	private boolean loadSchematic(String FileName) {
		try {
			saveFile = we.getSafeSaveFile(localPlayer, DirectoryStructure.getSchematicsDirectory(), FileName, "schematic", new String[] { "schematic" });
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	private boolean reloadTerrain(int x, int y, int z, String worldname) {
		if (wep == null) {
			return false;
		}

		try {
			World world = Bukkit.getWorld(worldname);
			
	        EditSession es = new EditSession(new BukkitWorld(world), 20000);
	        CuboidClipboard cc = CuboidClipboard.loadSchematic(saveFile);
			Vector pos = new Vector(x, y, z);		
			cc.paste(es, pos, false);
			return true;
		} catch (Exception e) {
			PropertyGroupLogger.severe(e.getMessage());
			return false;
		}
	}
	
	public static boolean reload(String FileName, String worldname, int x, int y, int z) {
		boolean restored = false;
		try {
			SchematicTools st = new SchematicTools();
			st.loadSchematic(FileName);
			restored = st.reloadTerrain(x, y, z, worldname);
			restored = true;
		} catch (Exception e) {
			restored = false;
		}
		return restored;
	}
	
	public void undo() {
		editSession.undo(editSession);
	}
}
