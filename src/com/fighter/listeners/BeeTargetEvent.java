package com.fighter.listeners;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class BeeTargetEvent implements Listener {
	
	@EventHandler
	public void onBeeTarget(EntityTargetEvent e) {
		
		Entity entity = e.getEntity();
		
		if(entity.getType() == EntityType.BEE) {	
			
			if(entity.getType() == null) {
				
				Logger.getLogger("ERROR").info("This is an error in BeeAttackStop, please report it on spigotmc");
				
			} else {
			
				e.setTarget(null);
			}

		} else {

			
		}
		
	}
	
}
