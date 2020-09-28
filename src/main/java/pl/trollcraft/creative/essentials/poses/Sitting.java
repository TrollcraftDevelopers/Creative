package pl.trollcraft.creative.essentials.poses;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;

import java.util.HashMap;
import java.util.Objects;

public class Sitting implements Listener {

    private static HashMap<Player, Entity> sitting = new HashMap<>();

    public static void sit(Player player, Block block) {
        Location loc = block.getLocation();

        Entity entity;
        if (block.getType().name().contains("STAIRS") || block.getType().name().contains("SLAB"))
            entity = Objects.requireNonNull(loc.getWorld()).spawnEntity(loc.add(.5, -0.15, .5), EntityType.ARROW);
        else
            entity = Objects.requireNonNull(loc.getWorld()).spawnEntity(loc.add(.5, .5, .5), EntityType.ARROW);

        entity.addPassenger(player);
        sitting.put(player, entity);
    }

    public static void standUp(Player player, Entity entity) {

        if (sitting.containsKey(player)) {
            sitting.get(player).remove();
            sitting.remove(player);

            new BukkitRunnable() {

                @Override
                public void run() {
                    entity.remove();
                }

            }.runTaskLater(Creative.getPlugin(), 20);
        }

    }

}
