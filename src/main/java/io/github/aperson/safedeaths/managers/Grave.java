package io.github.aperson.safedeaths.managers;

import com.destroystokyo.paper.MaterialSetTag;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Grave implements ConfigurationSerializable {
    public final String uuid;
    public final OfflinePlayer player;
    public final Location location;
    public final long timestamp;
    public final ItemStack[] inv;
    public final JavaPlugin plugin;

    public static Grave assembleFrom(@NotNull Player player, @NotNull JavaPlugin plugin) {
        return new Grave(
                UUID.randomUUID().toString(),
                player,
                player.getLocation(),
                Instant.now().getEpochSecond(),
                player.getInventory().getContents().clone(),
                plugin
        );
    }

    private Grave(
            @NotNull String uuid,
            @NotNull OfflinePlayer player,
            @NotNull Location location,
            long timestamp,
            @Nullable ItemStack[] inv,
            @NotNull JavaPlugin plugin
    ) {
        this.uuid = uuid;
        this.player = player;
        this.location = location;
        this.timestamp = timestamp;
        this.inv = inv;
        this.plugin = plugin;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "uuid", uuid,
                "inv", inv,
                "player", player,
                "location", location,
                "timestamp", timestamp
        );
    }

    @NotNull
    public static Grave deserialize(Map<String, Object> map) {

    }

    public Location findSafe() {
        for (int r = 1; r <= 5; r++) {
            for (int z = -r; z <= r; z++) {
                for (int y = -r; y <= r; y++) {
                    for (int x = -r; x <= r; x++) {
                        // I'm so sorry for this for loop.

                        if (Math.abs(z) != r && Math.abs(y) != r && Math.abs(x) != r) {
                            continue;
                        }

                        double cx = location.x() + x;
                        double cy = location.y() + y;

                        if (cy > location.getWorld().getMaxHeight()) {
                            cy = location.getWorld().getMaxHeight();
                        } else if (cy < location.getWorld().getMinHeight()) {
                            cy = location.getWorld().getMinHeight();
                        }

                        double cz = location.z() + z;

                        Location tLoc = new Location(location.getWorld(), cx, cy, cz);

                        if (tLoc.getBlock().isEmpty()) {
                            return tLoc;
                        }
                    }
                }
            }
        }

        return null;
    }

    public void place(Location l) {
        Block b = l.getBlock();

        b.setType(Material.PLAYER_HEAD);
        Skull s = (Skull) b.getState();

        ResolvableProfile rp = ResolvableProfile.resolvableProfile(this.player.getPlayerProfile());

        s.setProfile(rp);
        NamespacedKey key = new NamespacedKey(plugin)

        s.update(true);
    }
}
