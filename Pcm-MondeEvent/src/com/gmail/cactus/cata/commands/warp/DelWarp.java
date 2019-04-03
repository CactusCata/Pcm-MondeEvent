package com.gmail.cactus.cata.commands.warp;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class DelWarp implements CommandExecutor {

	private Main main;

	public DelWarp(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("delwarp")) {

			if (args.length < 0) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le nom du warp !");
				return true;
			}

			String warp = args[0];
			File file = new File(main.getDataFolder(), "warps.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);

			if (config.get("warps." + warp + ".x") == null) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le warp " + warp + " n'existe pas !");
				return true;
			}

			config.set("warps." + warp, null); // null --> reset de la partie
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			sender.sendMessage(PrefixMessage.PREFIX + "Le warp " + warp + " a bien été supprimé !");

			return true;

		}
		return false;
	}

}
