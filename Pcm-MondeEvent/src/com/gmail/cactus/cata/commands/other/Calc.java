package com.gmail.cactus.cata.commands.other;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.cactus.cata.enums.PrefixMessage;

public class Calc implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("/calc")) {

			sender.sendMessage(PrefixMessage.PREFIX + "La commande //calc a été bloqué !");
			return true;

		}

		return false;
	}

}
