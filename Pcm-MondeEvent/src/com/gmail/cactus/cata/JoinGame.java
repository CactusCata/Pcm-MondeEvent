package com.gmail.cactus.cata;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import com.gmail.cactus.cata.enums.PrefixMessage;
import com.gmail.cactus.cata.vanish.VanishManager;

public class JoinGame implements Listener {

	private Main main;
	private VanishManager vanishManager;

	public JoinGame(Main main, VanishManager vanishManager) {
		this.main = main;
		this.vanishManager = vanishManager;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		event.setJoinMessage(null);

		File file = new File(main.getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		Calendar calendrier = Calendar.getInstance();

		if (!file.exists()) {
			config.set("general.firstlogin",
					calendrier.get(Calendar.HOUR_OF_DAY) + ":" + calendrier.get(Calendar.MINUTE) + ":"
							+ calendrier.get(Calendar.SECOND) + " " + calendrier.get(Calendar.DAY_OF_MONTH) + "/"
							+ calendrier.get(Calendar.MONTH) + 1 + "/" + calendrier.get(Calendar.YEAR));
			config.set("general.staff", "Aucun");
			config.set("general.prefix", "§e");
			config.set("general.suffix", "§f");
			config.set("stats.uuid", player.getUniqueId().toString());
			config.set("general.pseudo", player.getName());
			config.set("general.lastip", player.getAddress().getHostString());
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Bukkit.broadcastMessage(
					PrefixMessage.PREFIX + "Veillez souhaiter la bienvenue à §3" + player.getName() + "§e !");
		}

		String staff = config.getString("general.staff");

		if (config.getBoolean("Vanish.connect")) {

			vanishManager.getList().add(player);
			vanishManager.actionMessageVanish(player, main);

			player.sendMessage(
					PrefixMessage.PREFIX_VANISH + "Vous êtes par défaut en Vanish quand vous vous connectez !");

			for (Player onlineplayer : Bukkit.getOnlinePlayers()) {

				if (staff.equals("Aucun"))
					onlineplayer.hidePlayer(player);

			}

		}

		if (staff.equals("Aucun"))
			for (Player playerVanished : vanishManager.getList())
				player.hidePlayer(playerVanished);

		player.sendMessage(PrefixMessage.PREFIX + "Bienvenue à toi " + player.getName() + " sur le monde event !");
		if (!(player.isOp())) {
			player.teleport(new Location(player.getWorld(), -0.5d, 65.5d, -0.5d, 0.0f, 10.0f));
			player.setGameMode(GameMode.ADVENTURE);
			player.setTotalExperience(0);
			player.setExp(0.0f);
			player.setFoodLevel(20);
			player.setHealth(20.0d);
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			player.getInventory().clear();
			player.updateInventory();
			if (player.getName().contains("Erisium")) {
				player.setDisplayName("Liberez_Saydan" + player.getName());
				player.setPlayerListName("Liberez_Saydan" + player.getName());
			}
		} else {

			Location loc = player.getLocation();
			player.teleport(new Location(player.getWorld(), loc.getX(),
					player.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ()), loc.getZ(), loc.getYaw(),
					loc.getPitch()));

		}

		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			@Override
			public void run() {

				for (Player player : Bukkit.getOnlinePlayers()) {
					
					File file = new File(main.getDataFolder(), "players/" + player.getUniqueId() + ".yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					String prefix = config.getString("general.prefix");
					String suffix = config.getString("general.suffix");

					if (!vanishManager.getList().contains(player)) {
						player.setDisplayName(prefix + player.getName() + suffix);

					} else {
						player.setDisplayName(prefix + player.getName() + suffix + "§f[Vanish]");
					}
					player.setPlayerListName(player.getDisplayName());

				}

			}
		}, 10L);

	}

}
