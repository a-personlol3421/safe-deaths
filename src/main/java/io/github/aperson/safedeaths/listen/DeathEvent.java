package io.github.aperson.safedeaths.listen;

import io.github.aperson.safedeaths.managers.Grave;
import io.github.aperson.safedeaths.managers.GraveManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class DeathEvent implements Listener {
    private final GraveManager mgr;
    private final JavaPlugin plugin;

    public DeathEvent(JavaPlugin plugin, GraveManager mgr) {
        this.mgr = mgr;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) throws IOException {
        Grave g = mgr.newGrave(event.getPlayer());

        if (g != null) {
            g.place();
            event.setShouldDropExperience(true);
        } else {
            event.getItemsToKeep().addAll(event.getDrops());
            event.setShouldDropExperience(false);
            event.setKeepLevel(true);
            event.getPlayer().sendMessage(Component.text("There was no safe location, so we bailed you out."));
        }

        event.getDrops().clear();

        plugin.getLogger().info("Player death event recorded and saved.");
    }
}
