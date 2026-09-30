package io.github.aperson.safedeaths.listen;

import io.github.aperson.safedeaths.managers.GraveManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    private final GraveManager mgr;

    public DeathEvent(GraveManager mgr) {
        this.mgr = mgr;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        mgr.newGrave(event.getPlayer());
    }
}
