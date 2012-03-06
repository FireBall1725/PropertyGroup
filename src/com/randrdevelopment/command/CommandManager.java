package com.randrdevelopment.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.PropertyGroup;
import com.randrdevelopment.command.BaseCommand;

public class CommandManager {
	protected List<BaseCommand> commands;
	
    public CommandManager() {
        commands = new ArrayList<BaseCommand>();
    }
    
    public boolean dispatch(CommandSender sender, Command command, String label, String[] args, PropertyGroup plugin) {
        String input = label + " ";
        for (String s : args) {
            input += s + " ";
        }

        BaseCommand match = null;
        String[] trimmedArgs = null;
        StringBuilder identifier = new StringBuilder();

        for (BaseCommand cmd : commands) {
            StringBuilder tmpIdentifier = new StringBuilder();
            String[] tmpArgs = cmd.validate(input, tmpIdentifier);
            if (tmpIdentifier.length() > identifier.length()) {
                identifier = tmpIdentifier;
                match = cmd;
                if (tmpArgs != null) {
                    trimmedArgs = tmpArgs;
                }
            }
            if (tmpArgs != null && tmpIdentifier.length() != 0) {
                if (tmpIdentifier.length() > identifier.length()) {
                    identifier = tmpIdentifier;
                    trimmedArgs = tmpArgs;
                    match = cmd;
                }
            }
        }

        if (match != null) {
            if (trimmedArgs != null) {
                match.execute(sender, trimmedArgs);
                return true;
            } else {
            	sender.sendMessage(plugin.getTag() + ChatColor.RED + "Error with command: " + match.getName());
                sender.sendMessage(ChatColor.AQUA + "Usage: " + match.getUsage());
            }
        }

        return true;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    public void removeCommand(BaseCommand command) {
        commands.remove(command);
    }
}
