package com.fighter.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.fighter.BeeAttack.main.main;

public class BeeRideEvent implements Listener {

	private main main;
	@SuppressWarnings("unused")
	private static boolean isRideable = false;

	public BeeRideEvent(main main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityRide(PlayerInteractEntityEvent event) {

		World world = Bukkit.getServer().getWorld("world");

		List<String> worldEnable = main.getConfig().getStringList("WorldEnable");

		if (!worldEnable.contains(world.getName())) {
			return;
		} else {

			Player player = event.getPlayer();

			if (main.getConfig().getBoolean("CustomBee.Rideable")) {

				if (event.getPlayer() instanceof HumanEntity) {

					if (event.getRightClicked().getType() == EntityType.BEE) {

						event.getRightClicked().setPassenger(player);

					} else {
						return;
					}
				}

			}
		}
	}

}
