package com.randrdevelopment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import com.randrdevelopment.log.PropertyGroupLogger;

public class DirectoryStructure {
	private static File pluginDir = new File("plugins", "PropertyGroup"); //$NON-NLS-1$ //$NON-NLS-2$
	private static File schematicsDir;
	private static final String schematicsFoldername = "schematics"; //$NON-NLS-1$
	private static File persistFile, cfgProperties;
	private static final String persistFilename = "persist.yml"; //$NON-NLS-1$
	private static final String configProperties = "propertygroups.yml";
	
	public static void setup() {
		pluginDir = PropertyGroup.getInstance().getDataFolder();
		
		setupDirectoryStructure();
		extractResources();
	}

	public static File getPluginDirectory() {
		return pluginDir;
	}

	public static File getSchematicsDirectory() {
		return schematicsDir;
	}

	public static File getPersistFile() {
		return persistFile;
	}
	
	public static File getCfgProperties() {
		return cfgProperties;
	}

	private static void setupDirectoryStructure() {
		schematicsDir = new File(schematicsFoldername);

		persistFile = new File(pluginDir, persistFilename);
		cfgProperties = new File(pluginDir, configProperties);

		createDir(pluginDir);
	}
	
	public static File getJarFile() {
		return new File("plugins", "PropertyGroup.jar");
	}
	
	private static void extractResources() {
		//extractResource("/AI_settings.yml", pluginDir); //$NON-NLS-1$

		//extractResource("/datafiles/board_styles/standard.yml", boardStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/board_styles/open.yml", boardStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/board_styles/sandwood.yml", boardStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/board_styles/large.yml", boardStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/board_styles/small.yml", boardStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/board_styles/huge.yml", boardStyleDir); //$NON-NLS-1$

		//extractResource("/datafiles/piece_styles/standard.yml", pieceStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/piece_styles/twist.yml", pieceStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/piece_styles/sandwood.yml", pieceStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/piece_styles/large.yml", pieceStyleDir); //$NON-NLS-1$
		//extractResource("/datafiles/piece_styles/small.yml", pieceStyleDir); //$NON-NLS-1$

		// message resources no longer extracted here - this is now done by Messages.loadMessages()
	}

	private static void createDir(File dir) {
		if (dir.isDirectory()) {
			return;
		}
		if (!dir.mkdir()) {
			PropertyGroupLogger.warning("Can't make directory " + dir.getName()); //$NON-NLS-1$
		}
	}

	private static void extractResource(String from, File toDir) {
		extractResource(from, toDir, false);
	}

	static void extractResource(String from, File to, boolean force) {
		File of = to;
		if (to.isDirectory()) {
			String fname = new File(from).getName();
			of = new File(to, fname);
		} else if (!of.isFile()) {
			return;
		}
		
		// if the file exists and is newer than the JAR, then we'll leave it alone
		if (of.exists() && of.lastModified() > getJarFile().lastModified() && !force) {
			return;
		}

		OutputStream out = null;
		try {
			// Got to jump through hoops to ensure we can still pull messages from a JAR
			// file after it's been reloaded...
			URL res = PropertyGroup.class.getResource(from);
			if (res == null) {
				PropertyGroupLogger.warning("can't find " + from + " in plugin JAR file"); //$NON-NLS-1$
				return;
			}
			URLConnection resConn = res.openConnection();
			resConn.setUseCaches(false);
			InputStream in = resConn.getInputStream();

			if (in == null) {
				PropertyGroupLogger.warning("can't get input stream from " + res); //$NON-NLS-1$
			} else {
				out = new FileOutputStream(of);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		} catch (Exception ex) {
			PropertyGroupLogger.log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) { //IOException

			}
		}
	}

	/**
	 * Find a YAML resource file in the given directory.  Look first in the custom/ subdirectory
	 * and then in the directory itself.
	 * 
	 * @param dir
	 * @param filename
	 * @param saving
	 * @return
	 * @throws ChessException
	 */
	public static File getResourceFile(File dir, String filename, boolean saving) {
		File f = new File(dir, "custom" + File.separator + filename.toLowerCase() + ".yml");
		if (!f.canRead() && !saving) {
			f = new File(dir, filename.toLowerCase() + ".yml");
			if (!f.canRead()) {
				
			}
		}
		return f;
	}
	
	public static File getResourceFile(File dir, String filename) {
		return getResourceFile(dir, filename, false);
	}
	
	
}
