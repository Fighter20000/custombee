package com.fighter.cutebees.custom_file_manager;

import com.fighter.cutebees.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WorldEnable {


    private static FileConfiguration worldConfig;
    private static File worldFile;

    public static FileConfiguration getWorldConfig() {
        return worldConfig;
    }

    public static void generateWorldFile() {
        worldFile = new File(Main.getInstance().getDataFolder(), "enabled_worlds.yml");

        if(!worldFile.exists()) {
            worldFile.getParentFile().mkdir();
            Main.getInstance().saveResource("enabled_worlds.yml", false);
        }

        worldConfig = new YamlConfiguration();

        try {
            worldConfig.load(worldFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
    public static void saveConfig() {
        try {
            worldConfig.save(worldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
