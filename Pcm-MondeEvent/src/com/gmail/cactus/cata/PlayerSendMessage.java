package com.gmail.cactus.cata;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.vanish.VanishManager;

public class PlayerSendMessage implements Listener {

	private Main main;
	private VanishManager vanishManag;

	public PlayerSendMessage(Main main, VanishManager vanishManag) {
		this.main = main;
		this.vanishManag = vanishManag;
	}

	@EventHandler
	public void SendMessage(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		event.setCancelled(true);
		if (vanishManag.getList().contains(player)) {

			if (!(main.getConfig().getBoolean("Vanish.CanSpeak"))) {

				player.sendMessage(PrefixMessage.PREFIX_VANISH
						+ "La config vous empeche de parler, vous pouvez executer la commande suivante pour parler à nouveau :\n/config change CanSpeak");
				return;

			}

		}

		File file = new File(main.getDataFolder(), "players/" + player.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		String prefix = config.getString("general.prefix");
		String suffix = config.getString("general.suffix");

		if (player.hasPermission("pcm.msg.color")) {
			Bukkit.broadcastMessage(prefix + player.getName() + suffix + ": " + event.getMessage().replace('&', '§'));

		} else {
			Bukkit.broadcastMessage(prefix + player.getName() + suffix + ": " + event.getMessage());
		}

	}

}
