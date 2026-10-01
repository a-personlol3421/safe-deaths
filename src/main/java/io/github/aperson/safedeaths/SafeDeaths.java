package io.github.aperson.safedeaths;

import io.github.aperson.safedeaths.listen.DeathEvent;
import io.github.aperson.safedeaths.managers.GraveManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

public class SafeDeaths extends JavaPlugin {
    @Override
    public void onEnable() {
        GraveManager mgr = new GraveManager(this, "gravestones.yml");
        DeathEvent listener = new DeathEvent(this, mgr);

        this.getServer().getPluginManager().registerEvents(listener, this);

        this.getLogger().info("Registered event listeners.");
    }
}
