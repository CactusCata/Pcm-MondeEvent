package com.gmail.cactus.cata.commands.warp;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.maths.Maths;

public class SetWarp implements CommandExecutor {

	private Main main;

	public SetWarp(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			if (args.length < 1) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le nom du warp !");
				return true;
			}

			Player p = (Player) sender;
			String world = p.getWorld().getName();
			String warp = args[0];
			double x = Maths.arrondidouble(p.getLocation().getX(), 2);
			double y = Maths.arrondidouble(p.getLocation().getY(), 2);
			double z = Maths.arrondidouble(p.getLocation().getZ(), 2);
			float pitch = Maths.arrondifloat(p.getLocation().getPitch(), 2);
			float yaw = Maths.arrondifloat(p.getLocation().getYaw(), 2);

			p.sendMessage(PrefixMessage.PREFIX + "Le warp " + args[0] + " a été créer en x: " + x + ", y: " + y
					+ ", z: " + z + ", pitch: " + pitch + ", yaw: " + yaw + ", monde: " + world.toString() + " !");

			File file = new File(main.getDataFolder(), "warps.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);

			if (!file.exists()) {
				try {
					config.save(file);
				} catch (IOException e) {

				}
			}

			config.set("warps." + warp + ".x", x);
			config.set("warps." + warp + ".y", y);
			config.set("warps." + warp + ".z", z);
			config.set("warps." + warp + ".pitch", pitch);
			config.set("warps." + warp + ".yaw", yaw);
			config.set("warps." + warp + ".monde", world);
			try {
				config.save(file);
			} catch (IOException e) {

			}

			return true;
		}
		return false;
	}

}
