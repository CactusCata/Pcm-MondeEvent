package com.gmail.cactus.cata;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.cactus.cata.commands.other.Broadcast;
import com.gmail.cactus.cata.commands.other.Calc;
import com.gmail.cactus.cata.commands.other.ConfigOption;
import com.gmail.cactus.cata.commands.other.Fly;
import com.gmail.cactus.cata.commands.other.Gamemodeb;
import com.gmail.cactus.cata.commands.other.Heal;
import com.gmail.cactus.cata.commands.other.InfoPlayer;
import com.gmail.cactus.cata.commands.other.Killb;
import com.gmail.cactus.cata.commands.other.Spawn;
import com.gmail.cactus.cata.commands.other.Speed;
import com.gmail.cactus.cata.commands.other.Tps;
import com.gmail.cactus.cata.commands.spy.MessageObj;
import com.gmail.cactus.cata.commands.spy.Msg;
import com.gmail.cactus.cata.commands.spy.R;
import com.gmail.cactus.cata.commands.spy.SocialSpy;
import com.gmail.cactus.cata.commands.spy.SpyList;
import com.gmail.cactus.cata.commands.staff.SetStaff;
import com.gmail.cactus.cata.commands.tp.Tpb;
import com.gmail.cactus.cata.commands.tp.Tppos;
import com.gmail.cactus.cata.commands.warp.DelWarp;
import com.gmail.cactus.cata.commands.warp.SetWarp;
import com.gmail.cactus.cata.commands.warp.Warp;
import com.gmail.cactus.cata.commands.warp.WarpInfo;
import com.gmail.cactus.cata.commands.warp.Warps;
import com.gmail.cactus.cata.other.Sign;
import com.gmail.cactus.cata.other.Weather;
import com.gmail.cactus.cata.vanish.EventsListener;
import com.gmail.cactus.cata.vanish.Vanish;
import com.gmail.cactus.cata.vanish.VanishManager;

public class Main extends JavaPlugin {

	public void onEnable() {

		PluginManager pm = getServer().getPluginManager();
		getConfig().options().copyDefaults(true);
		saveConfig();

		GameObj gameObj = new GameObj();
		MessageObj messageObj = new MessageObj();
		VanishManager vanishManager = new VanishManager();

		pm.registerEvents(new Sign(), (this));
		pm.registerEvents(new Weather(), (this));
		pm.registerEvents(new PlayerSendMessage(this, vanishManager), (this));
		pm.registerEvents(new LeaveGame(this, messageObj, vanishManager), (this));
		pm.registerEvents(new JoinGame(this, vanishManager), (this));
		pm.registerEvents(new EventsListener(this, vanishManager, gameObj), (this));

		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("gamemodeb").setExecutor(new Gamemodeb(gameObj));
		getCommand("setstaff").setExecutor(new SetStaff(this, vanishManager));
		getCommand("setwarp").setExecutor(new SetWarp(this));
		getCommand("delwarp").setExecutor(new DelWarp(this));
		getCommand("warp").setExecutor(new Warp(this));
		getCommand("warps").setExecutor(new Warps(this));
		getCommand("warpinfo").setExecutor(new WarpInfo(this));
		getCommand("socialspy").setExecutor(new SocialSpy(messageObj));
		getCommand("spylist").setExecutor(new SpyList(messageObj));
		getCommand("msg").setExecutor(new Msg(messageObj, vanishManager));
		getCommand("r").setExecutor(new R(messageObj));
		getCommand("speed").setExecutor(new Speed());
		getCommand("killb").setExecutor(new Killb());
		getCommand("tppos").setExecutor(new Tppos());
		getCommand("tpb").setExecutor(new Tpb(vanishManager));
		getCommand("fly").setExecutor(new Fly());
		getCommand("infoplayer").setExecutor(new InfoPlayer(this));
		getCommand("vanish").setExecutor(new Vanish(this, vanishManager));
		getCommand("config").setExecutor(new ConfigOption(this));
		getCommand("lag").setExecutor(new Tps(this));
		getCommand("heal").setExecutor(new Heal());
		getCommand("/calc").setExecutor(new Calc());

	}

}
