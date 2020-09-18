package pl.trollcraft.creative.essentials.poses;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.events.DoubleInteractEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Sitting implements Listener {

    private static HashMap<Player, Entity> sitting = new HashMap<>();

    public static void sit(Player player, Block block) {
        Location loc = block.getLocation();

        Entity entity;
        if (block.getType().name().contains("STAIRS"))
            entity = loc.getWorld().spawnEntity(loc.add(0.5, -0.15, 0.5), EntityType.ARROW);
        else
            entity = loc.getWorld().spawnEntity(loc.add(0.5, 0.15, 0.5), EntityType.ARROW);

        entity.addPassenger(player);
        hideEntity(player, entity);

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

    private static void hideEntity(Player sees, Entity entity) {

        PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);

        container.getIntegerArrays().write(0, new int[] { entity.getEntityId() } );

        int id = sees.getEntityId();
        Bukkit.getOnlinePlayers().forEach( player -> {

            if (player.getEntityId() == id) return;

            try {

                Creative.getPlugin().getProtocolManager().sendServerPacket(player, container);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        });

    }

}
