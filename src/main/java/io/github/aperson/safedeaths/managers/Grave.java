package io.github.aperson.safedeaths.managers;

import com.destroystokyo.paper.MaterialSetTag;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public class Grave {
    public final UUID uuid;
    public final OfflinePlayer player;
    public final Location location;
    public final long timestamp;
    public final ItemStack[] inv;

    public Grave(@NotNull Player player) {
        this.uuid = UUID.randomUUID();
        this.player = player;
        this.location = player.getLocation();
        this.timestamp = Instant.now().getEpochSecond();
        this.inv = player.getInventory().getContents().clone();
    }

    public Grave(
            @NotNull UUID uuid,
            @NotNull OfflinePlayer player,
            @NotNull Location location,
            long timestamp,
            @NotNull ItemStack[] inv
    ) {
        this.uuid = uuid;
        this.player = player;
        this.location = location;
        this.timestamp = timestamp;
        this.inv = inv;
    }
}
