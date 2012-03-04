package com.randrdevelopment.regions;

import java.io.File;
import java.util.logging.Level;

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
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class SchematicTools {
	private WorldEditPlugin wep;
	private WorldEdit we;
	private File saveFile;
	private LocalPlayer localPlayer;
	private EditSession editSession;
	private LocalSession localSession;
	private Player player;
	
	private SchematicTools (Player player) {
		this.player = player;
		
		wep = PropertyGroup.getWorldEdit();
		if (wep == null) {
			return;
		}
		we = wep.getWorldEdit();
		
		localPlayer = wep.wrapPlayer(player);
		localSession = we.getSession(localPlayer);
		editSession = localSession.createEditSession(localPlayer);
	}
	
	private boolean loadSchematic(String FileName) {
		try {
			saveFile = we.getSafeSaveFile(localPlayer, DirectoryStructure.getSchematicsDirectory(), FileName, "schematic", new String[] { "schematic" });
			return true;
		} catch (Exception ex) {
			//PropertyGroupLogger.warning("Error when loading schematic", ex);
			return false;
		}
	}
	
	private boolean reloadTerrain() {
		if (wep == null) {
			return false;
		}

		try {
			editSession.enableQueue();
			localSession.setClipboard(CuboidClipboard.loadSchematic(saveFile));
			//Vector pos = localSession.getClipboard().getOrigin();
			Vector pos = localPlayer.getPosition();
			
			localSession.getClipboard().place(editSession, pos, false);
			editSession.flushQueue();
			we.flushBlockBag(localPlayer, editSession);
			//if (!saveFile.delete()) {
				//PropertyGroupLogger.warning("Cannot get schematic file");
			//}
			return true;
		} catch (Exception e) {
			// DataException, IOException, EmptyClipboardException,
			// MaxChangedBlocksException
			// PropertyGroupLogger.errorMessage(player, "Cannot restore schematic file"); //$NON-NLS-1$
			return false;
		}
	}
	
	public static boolean reload(Player player, String FileName) {
		boolean restored = false;
		try {
			SchematicTools st = new SchematicTools(player);
			st.loadSchematic(FileName);
			restored = st.reloadTerrain();
		} catch (Exception e) {
			//PropertyGroupLogger.log(Level.WARNING, e.getMessage());
		}
		return restored;
	}
	
	public void undo() {
		editSession.undo(editSession);
	}
}
