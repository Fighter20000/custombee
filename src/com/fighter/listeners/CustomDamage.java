package com.fighter.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.fighter.BeeAttack.main.main;

public class CustomDamage implements Listener {

	private main main;

	public CustomDamage(main main) {
		this.main = main;
	}

	@EventHandler
	public void onCustomDamage(EntityDamageByEntityEvent e) {

		World world = Bukkit.getServer().getWorld("world");

		List<String> worldEnable = main.getConfig().getStringList("WorldEnable");

		if (!worldEnable.contains(world.getName())) {
			return;
		} else {

			Entity entity = e.getDamager();
			Entity player = e.getEntity();

			if (!(entity.getType() == EntityType.BEE) || !(player.getType() == EntityType.PLAYER)) {

				entity = null;
				player = null;

			} else {

				double damage = main.getConfig().getDouble("CustomBee.Damage");
				double customdamage = damage;
				e.setDamage(customdamage);

			}

		}
	}

}
