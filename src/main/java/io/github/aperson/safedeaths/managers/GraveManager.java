package io.github.aperson.safedeaths.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GraveManager {
    private final YamlConfiguration config;
    private final File file;
    private final JavaPlugin plugin;

    public GraveManager(JavaPlugin plugin, String path) {
        file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) {
            boolean _ = file.getParentFile().mkdirs(); // Assignment because IntelliJ gets REALLY mad
        }

        ConfigurationSerialization.registerClass(Grave.class);
        config = YamlConfiguration.loadConfiguration(file);

        this.plugin = plugin;
    }

    public Grave newGrave(@NotNull Player player) throws IOException {
        Grave grave = Grave.assembleFrom(player);

        config.set("gravestones." + grave.uuid, grave);
        config.save(file);

        return grave;
    }

    @Nullable
    public Grave fromBlock(@NotNull Block block) {
        ConfigurationSection section = config.getConfigurationSection("gravestones");

        if (section == null) {
            return null;
        }

        for (String key : section.getKeys(false)) {
            Grave grave = (Grave) section.get(key);

            // IntelliJ also gets mad for dereferencing something that MAY be null.
            // So this is here now.
            if (grave == null) {
                return null;
            }

            if (grave.location == block.getLocation()) {
                return grave;
            }
        }

        return null;
    }

    public void deleteGrave(@NotNull String uuid) {
        Grave g = (Grave) config.get("gravestones." + uuid);

        if (g != null) {
            config.set("gravestones." + uuid, null);
        }
    }
}
