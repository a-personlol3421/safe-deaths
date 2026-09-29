package io.github.aperson.safedeaths.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Data {
    private YamlConfiguration config;

    public Data(JavaPlugin plugin, String path) {
        config = YamlConfiguration.loadConfiguration(
                new File(
                        plugin.getDataFolder() + path
                )
        );
    }

    public void getFromLocation(Location location) {

    }
}
