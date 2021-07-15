package com.fighter.cutebees.listeners;

import com.fighter.cutebees.main.Main;
import com.fighter.cutebees.runnable.CustomTask;
import com.fighter.cutebees.utils.Utils;
import com.fighter.cutebees.custom_file_manager.WorldEnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Objects;

public class beeSpawnListener implements Listener {


    private static Main main;
    private static Utils util = new Utils();
    private static CustomTask task;

    public beeSpawnListener(Main main) {
        this.main = main;
    }

    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnBee(CreatureSpawnEvent event) {
        updateBees();
    }*/

    public static void updateBees() {
        for (World world : Bukkit.getServer().getWorlds()) {
            List<String> worldEnable = WorldEnable.getWorldConfig().getStringList("world-enable");
            if (worldEnable.contains(world.getName())) {
                for (Entity livingEntity : world.getEntities()) {
                    if (livingEntity instanceof Bee) {
                        Bee bee = (Bee) livingEntity;

                        String name = main.getConfig().getString("CuteBees.bee-name");

                        if (name != null) {
                            if(bee.getHealth() == 10.0) {
                                name = name.replace("%bee-health%", String.valueOf((float) bee.getHealth()).substring(0,4));
                            } else if (String.valueOf(bee.getHealth()).length() > 5 && bee.getHealth() < 0.1) {
                                name = name.replace("%bee-health%", String.valueOf((float) bee.getHealth()).substring(0,4));
                            } else {
                                name = name.replace("%bee-health%", String.valueOf((float) bee.getHealth()).substring(0,3));
                            }
                            name = name.replace("%bee-name%", Objects.requireNonNull(Main.instance.getConfig().getString("CuteBees.bee-name")));
                            name = name.replace("%bee-max-health%", String.valueOf((float) Math.round(bee.getMaxHealth())));
                            bee.setCustomName(util.pch(name));
                        } else {
                            bee.setCustomName(ChatColor.RED + "cName of the bee is NULL, check config.yml");
                        }

                        bee.setGlowing(main.getConfig().getBoolean("CuteBees.bee-glowing"));
                        bee.setCustomNameVisible(main.getConfig().getBoolean("CuteBees.custom-name-visible"));
                        if(Main.getInstance().getConfig().getBoolean("CuteBees.every-bee-baby")) {
                            bee.setBaby();
                        } else {
                            bee.setAdult();
                        }

                    }
                }
            }
        }
    }
}
