package io.github.aperson.safedeaths.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public class GraveManager {
    private final YamlConfiguration config;

    public GraveManager(JavaPlugin plugin, String path) throws RuntimeException {
        plugin.getLogger().info("Saving to: " + plugin.getDataFolder() + path);
        File file = new File(plugin.getDataFolder() + "/" + path);

        if (!file.exists()) {
            boolean success = file.getParentFile().mkdirs();

            if (!success) {
                plugin.getLogger().info("Exists, likely. Ignoring.");
            }

            plugin.saveResource(plugin.getDataFolder() + path, false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void newGrave(@NotNull Player player) {
        Grave grave = new Grave(player);

        config.set("gravestones." + grave.uuid.toString(), grave);
    }

    @Nullable
    public Grave fromBlock(@NotNull Block block) {
        ConfigurationSection section = config.getConfigurationSection("gravestones");

        if (section == null) {
            return null;
        }

        for (String key : section.getKeys(false)) {
            break;
        }

        return null;
    }

    @Nullable
    public Grave fromUUID(@NotNull UUID uuid) {
        ConfigurationSection gravestone = config.getConfigurationSection("gravestones." + uuid.toString());

        if (gravestone == null) {
            return null;
        }

        return null;
    }
}
