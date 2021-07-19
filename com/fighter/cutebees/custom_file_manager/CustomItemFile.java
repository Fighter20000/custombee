package com.fighter.cutebees.custom_file_manager;

import com.fighter.cutebees.listeners.EntityBeeDeathDropListener;
import com.fighter.cutebees.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomItemFile {

    private static FileConfiguration itemConfig;
    private static File itemFile;

    public static FileConfiguration getItemConfig() {
        return itemConfig;
    }

    public static void generateCustomItemFile() {
        itemFile = new File(Main.getInstance().getDataFolder(), "custom_items.yml");

        if(!itemFile.exists()) {
            itemFile.getParentFile().mkdir();
            Main.getInstance().saveResource("custom_items.yml", false);
        }

        itemConfig = new YamlConfiguration();

        try {
            itemConfig.load(itemFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
    public static void saveConfig() {
        try {
            itemConfig.save(itemFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        try {
            itemConfig.load(itemFile);
            EntityBeeDeathDropListener.loadCustomItems();
            saveConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
