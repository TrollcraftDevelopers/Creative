package pl.trollcraft.creative.essentials.poses;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    @EventHandler
    public void onInteract (DoubleInteractEvent event) {

        //FIXME add sitting on blocks (right now it is a bit bugged, when sitting on stairs - ok)

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Location loc = event.getBlock().getLocation();

            Entity entity = loc.getWorld().spawnEntity(loc.add(0.5, -0.15, 0.5), EntityType.ARROW);
            entity.addPassenger(player);
            hideEntity(player, entity);

            sitting.put(player, entity);
        }

    }

    @EventHandler
    public void onDismount (final EntityDismountEvent event) {

        if (event.getEntityType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();

        if (sitting.containsKey(player)) {
            sitting.get(player).remove();
            sitting.remove(player);

            new BukkitRunnable() {

                @Override
                public void run() {
                    event.getDismounted().remove();
                }

            }.runTaskLater(Creative.getPlugin(), 20);
        }

    }

    private void hideEntity(Player sees, Entity entity) {

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
