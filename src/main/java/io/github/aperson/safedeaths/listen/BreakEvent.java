package io.github.aperson.safedeaths.listen;

import io.github.aperson.safedeaths.managers.Grave;
import io.github.aperson.safedeaths.managers.GraveManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakEvent implements Listener {
    private final GraveManager mgr;
    private final JavaPlugin plugin;

    public BreakEvent(JavaPlugin plugin, GraveManager mgr) {
        this.mgr = mgr;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        Grave g = mgr.fromBlock(event.getBlock());

        if (g == null) {
            plugin.getLogger().info("grave is null");
            return;
        }

        if (g.player.getUniqueId() != event.getPlayer().getUniqueId()) {
            plugin.getLogger().info("player is not required player");
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);

        for (int i = 0; i < g.inv.length; i++) {
            event.getPlayer().getInventory().setItem(i, g.inv[i]);
        }

        plugin.getLogger().info("replaced all");
    }
}
