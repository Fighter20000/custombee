package com.fighter.listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.fighter.BeeAttack.main.main;

public class BeeCustom implements Listener {

	private static main main;

	public BeeCustom(main main) {
		BeeCustom.main = main;
	}

	private BukkitTask updater;
	@SuppressWarnings("unused")
	private BukkitTask errorNotify;

	@EventHandler(priority = EventPriority.HIGH)
	public void onCustomBee(CreatureSpawnEvent event) {

		World world = Bukkit.getServer().getWorld("world");
		
		List<String> worldEnable = main.getConfig().getStringList("WorldEnable");
		
		if(!worldEnable.contains(world.getName())) {
			
		} else {

		LivingEntity bee = event.getEntity();

		if (!(event.getEntityType() == EntityType.BEE)) {

			bee = null;

		} else {

			BukkitScheduler scheduler = Bukkit.getScheduler();

			Bee b = (Bee) bee;

			updater = scheduler.runTaskTimerAsynchronously(main, new Runnable() {

				@Override
				public void run() {

					String name = String.join("\n", main.getConfig().getStringList("CustomBee.CustomName.Name"));
					int maxhp = main.getConfig().getInt("CustomBee.Health");
					String hpmax = Integer.toString(maxhp);
					int health = (int) b.getHealth();
					String hp = Integer.toString(health);
					
						b.setCustomName(pch(name).replace("%health%", hp).replace("%max-health%", hpmax));
						b.setGlowing(main.getConfig().getBoolean("CustomBee.Glowing"));
						b.setCustomNameVisible(main.getConfig().getBoolean("CustomBee.CustomName.NameVisible"));
					

				}

			}, 20, 10);

			int health = main.getConfig().getInt("CustomBee.Health");

			if (health > 10 || health < 0) {

				errorNotify = scheduler.runTaskTimer(main, new Runnable() {

					@Override
					public void run() {
					
						Logger.getLogger("CustomBee").log(Level.WARNING,
								"Hey! Your configuration is bad in bees healths. Please, do not use higher or smaller number than 10 & 0");
								
					}

				}, 60, 60);

			} else {

				b.setHealth(main.getConfig().getDouble("CustomBee.Health"));
			}
			if (main.getConfig().getBoolean("CustomBee.EveryBeeBaby") == true) {

				b.setBaby();
			}

		}

		if (Bukkit.getOnlinePlayers().size() == 0) {

			if (!updater.isCancelled()) {

				updater.cancel();

			} else {

			}

		}

	}

}
		
	public static String pch(String pch) {

		pch = ChatColor.translateAlternateColorCodes('&', pch);

		return pch;

	}
	

}
