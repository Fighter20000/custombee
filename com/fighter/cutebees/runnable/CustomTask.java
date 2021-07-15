package com.fighter.cutebees.runnable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CustomTask implements Runnable {

    private int taskId;

    public CustomTask(JavaPlugin plugin, long arg, long arg2) {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, arg, arg2);
    }

    public void canncel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public Boolean isRunning() {
     return Bukkit.getScheduler().isCurrentlyRunning(taskId);
    }

}
