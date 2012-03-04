package com.randrdevelopment.log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

import com.randrdevelopment.PropertyGroup;

public class PropertyGroupLogger {

	protected static Logger logger;

	public static void init() {
		logger = PropertyGroup.getInstance().getLogger();
	}
	
	public static void setLogLevel(Level level) {
		logger.setLevel(level);
		logger.getParent().setLevel(level);
		for (Handler h : logger.getParent().getHandlers()) {
			h.setLevel(level);
		}
	}
	
	public static void setLogLevel(String val) {
		try {
			Level newLevel = Level.parse(val.toUpperCase());
			setLogLevel(newLevel);
		} catch (IllegalArgumentException e) {
			PropertyGroupLogger.warning("Bad log level: " + val);
		}
	}

	/*----------------- generic logging functions ---------------------*/
	public static void log(String message) {
		if (message != null) {
			logger.log(Level.INFO, ChatColor.stripColor(message));
		}
	}

	public static void log(Level level, String message) {
		if (level == null) {
			level = Level.INFO;
		}
		if (message != null) {
			logger.log(level, ChatColor.stripColor(message));
		}
	}

	public static void log(Level level, String message, Exception err) {
		if (err == null) {
			log(level, message);
		} else {
			logger.log(level, message == null ? (err == null ? "?" : err.getMessage()) : ChatColor.stripColor(message), err);
		}
	}

	/*------------------- logging levels ------------------------------*/
	public static void fine(String message) {
		if (message != null) {
			logger.fine(ChatColor.stripColor(message));
		}
	}
	
	public static void finer(String message) {
		if (message != null) {
			logger.finer(ChatColor.stripColor(message));
		}
	}
	
	public static void finest(String message) {
		if (message != null) {
			logger.finest(ChatColor.stripColor(message));
		}
	}

	public static void info(String message) {
		if (message != null) {
			logger.info(ChatColor.stripColor(message));
		}
	}

	public static void warning(String message) {
		if (message != null) {
			logger.warning(ChatColor.stripColor(message));
		}
	}

	public static void severe(String message) {
		if (message != null) {
			logger.severe(ChatColor.stripColor(message));
		}
	}

	public static void info(String message, Exception err) {
		if (err == null) {
			info(message);
		} else {
			logger.log(Level.INFO, getMsg(message, err));
		}
	}

	public static void warning(String message, Exception err) {
		if (err == null) {
			warning(message);
		} else {
			logger.log(Level.WARNING, getMsg(message, err));
		}
	}

	public static void severe(String message, Exception err) {
		if (err == null) {
			severe(message);
		} else {
			logger.log(Level.SEVERE, getMsg(message, err));
		}
	}
	
	private static String getMsg(String message, Exception e) {
		return message == null ? e.getMessage() : ChatColor.stripColor(message);
	}
	
} // end class ChessCraftLogger

