package com.fighter.BeeAttack.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.fighter.BeeAttack.commands.Commands;
import com.fighter.listeners.BeeCustom;
import com.fighter.listeners.BeeRideEvent;
import com.fighter.listeners.ClickEvent;
import com.fighter.listeners.CustomDamage;
import com.fighter.listeners.CustomDrop;

public class main extends JavaPlugin {

	public void onEnable() {

		Bukkit.getServer().getPluginManager().registerEvents(new BeeCustom(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomDrop(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CustomDamage(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BeeRideEvent(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);

		this.getCommand("custombee").setExecutor(new Commands(this));

		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(false);
	}
}
