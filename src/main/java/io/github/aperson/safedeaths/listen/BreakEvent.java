package io.github.aperson.safedeaths.listen;

import io.github.aperson.safedeaths.managers.Grave;
import io.github.aperson.safedeaths.managers.GraveManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
    private final GraveManager mgr;

    public BreakEvent(GraveManager mgr) {
        this.mgr = mgr;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        Grave g = mgr.fromBlock(event.getBlock());

        if (g == null) {
            return;
        }

        if (g.player.getUniqueId() != event.getPlayer().getUniqueId()) {
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);

        for (int i = 0; i < g.inv.length; i++) {
            event.getPlayer().getInventory().setItem(i, g.inv[i]);
        }
    }
}
