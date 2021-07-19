package com.fighter.cutebees.main;

import com.fighter.cutebees.commands.Commands;
import com.fighter.cutebees.custom_file_manager.CustomItemFile;
import com.fighter.cutebees.listeners.CustomInventoryListener;
import com.fighter.cutebees.listeners.EntityBeeDeathDropListener;
import com.fighter.cutebees.listeners.beeSpawnListener;
import com.fighter.cutebees.runnable.CustomTask;
import com.fighter.cutebees.utils.Utils;
import com.fighter.cutebees.custom_file_manager.WorldEnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class Main extends JavaPlugin {

    private final PluginManager pm = Bukkit.getPluginManager();
    private final Server s = Bukkit.getServer();
    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    public static Main instance;
    private CustomTask task;
    private Utils util = new Utils();

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        sender.sendMessage(ChatColor.GREEN + "[ CuteBees ] " + ChatColor.YELLOW + "The plugin has been enabled on this server!");
        sender.sendMessage(util.versionCheckerForHex(Arrays.asList("1.16","1.17")) ? util.pch("&a[ CuteBees ] " + "&7Does the server version support hex colors? &2Yes &6("+Bukkit.getServer().getBukkitVersion()+")") : util.pch("&a[ CuteBees ] " + "&7Does the server version support hex colors? &4No &6("+Bukkit.getServer().getBukkitVersion()+")"));
        saveDefaultConfig();
        WorldEnable.generateWorldFile();
        CustomItemFile.generateCustomItemFile();
        registerListeners();
        registerCommands();
        EntityBeeDeathDropListener.loadCustomItems();

        task = new CustomTask(this, 0, 20) {
            @Override
            public void run() {
                beeSpawnListener.updateBees();

                /*if(Bukkit.getOnlinePlayers().size() < 1) {
                    canncel();
                }*/
            }
        };


    }



    private void registerCommands() {
        Objects.requireNonNull(getCommand("cutebees")).setExecutor(new Commands());
    }

    public CustomTask getTask() {
        return task;
    }

    @Override
    public void onDisable() {
        sender.sendMessage(ChatColor.GREEN + "[ CuteBees ] " + ChatColor.RED + "The plugin has been disabled on this server!");
        if (getTask() != null && getTask().isRunning()) {
            getTask().canncel();
        }
    }

    private void registerListeners() {
        pm.registerEvents(new beeSpawnListener(this), this);
        pm.registerEvents(new EntityBeeDeathDropListener(), this);
        pm.registerEvents(new CustomInventoryListener(), this);
    }
}
