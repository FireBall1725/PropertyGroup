package com.randrdevelopment.propertygroup.regions;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.randrdevelopment.propertygroup.log.PropertyGroupLogger;
import com.randrdevelopment.propertygroup.DirectoryStructure;
import com.randrdevelopment.propertygroup.PropertyGroup;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class SchematicTools {
	private WorldEditPlugin wep;
	private WorldEdit we;
	private File saveFile;
	private LocalPlayer localPlayer;
	
	private SchematicTools () {
		wep = PropertyGroup.getWorldEdit();
		if (wep == null) {
			return;
		}
		we = wep.getWorldEdit();	
	}
	
	private boolean loadSchematicFile(String FileName){
		try {
			saveFile = we.getSafeSaveFile(localPlayer, DirectoryStructure.getSchematicsDirectory(), FileName, "schematic", new String[] { "schematic" });
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean placeSchematic(int blocks, int start_x, int start_y, int start_z, String worldName){
		if (wep == null){
			return false;
		}
		
		try {
			World world = Bukkit.getWorld(worldName);
			
			EditSession es = new EditSession(new BukkitWorld(world), blocks);
			CuboidClipboard cc = CuboidClipboard.loadSchematic(saveFile);
			Vector originaloffset = cc.getOffset();
			Vector newoffset = new Vector(0, originaloffset.getY(), 0);
			cc.setOffset(newoffset);
			Vector pos = new Vector(start_x, start_y, start_z);
			cc.paste(es, pos, false);
			return true;
		} catch (Exception e) {
			PropertyGroupLogger.severe(e.getMessage());
			return false;
		}
	}
	
	public static boolean reload(String FileName, String worldname, int x, int y, int z, int blocks) {
		boolean restored = false;
		try {
			SchematicTools st = new SchematicTools();
			if (st.loadSchematicFile(FileName)) {
				if (restored = st.placeSchematic(blocks, x, y, z, worldname)) {
					restored = true;
				}
			}
		} catch (Exception e) {
			restored = false;
		}
		return restored;
	}
}
