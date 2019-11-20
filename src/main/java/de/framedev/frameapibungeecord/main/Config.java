package de.framedev.frameapibungeecord.main;
/*
 * This Plugin was Created by FrameDev
 * Copyrighted by FrameDev
 * 18.11.2019, 15:44
 */

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    public static Configuration getConfig() {
        Configuration configuration = null;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(FrameAPIBungee.getPlugin().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }
    public static void saveFile() {
        try {
            if (!FrameAPIBungee.getPlugin().getDataFolder().exists())
                FrameAPIBungee.getPlugin().getDataFolder().mkdir();

            File file = new File(FrameAPIBungee.getPlugin().getDataFolder(), "config.yml");

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void saveCFG() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), new File(FrameAPIBungee.getPlugin().getDataFolder(), "config.yml"));
            Configuration configuration = null;
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(FrameAPIBungee.getPlugin().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
