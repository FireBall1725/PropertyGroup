package com.randrdevelopment.propertygroup.command.commands;

import org.bukkit.command.CommandSender;

import com.randrdevelopment.propertygroup.command.BaseCommand;
import com.randrdevelopment.propertygroup.PropertyGroup;

public class SetConfigCommand extends BaseCommand{	
	public SetConfigCommand(PropertyGroup plugin) {
        super(plugin);
        name = "Set";
        description = "Sets Configuration Options.";
        usage = "/property set <groupname> <option> <value>";
        minArgs = 3;
        maxArgs = 3;
        identifiers.add("property set");
    }
	
    @Override
    public void execute(CommandSender sender, String[] args) {
    	//TODO: SET STUFF...
    }
}
