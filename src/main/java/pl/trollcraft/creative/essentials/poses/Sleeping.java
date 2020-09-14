package pl.trollcraft.creative.essentials.poses;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.particles.ParticleType;
import pl.trollcraft.creative.core.particles.Particles;
import pl.trollcraft.creative.core.particles.StandardParticle;
import pl.trollcraft.creative.services.tails.ClassicTail;
import pl.trollcraft.creative.core.wrappers.*;

import java.util.ArrayList;

public class Sleeping implements Listener {

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {

            Vector off = new Vector(.5, .5, .5);

            //Particles particles = new StandardParticle(ParticleType.Standard.HEART, off, 5);
            //new ClassicTail(particles, event.getPlayer());

            //layDown(event.getPlayer());

        }

    }

    private static ArrayList<Player> laying = new ArrayList<>();

    private void layDown(Player player) {

        laying.add(player);

        /*WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[] {player.getEntityId()});*/

        WrapperPlayServerNamedEntitySpawn spawn = new WrapperPlayServerNamedEntitySpawn();
        spawn.setEntityID(123);
        spawn.setX(player.getLocation().getX());
        spawn.setY(player.getLocation().getY());
        spawn.setZ(player.getLocation().getZ());
        spawn.setPlayerUUID(player.getUniqueId());

        WrapperPlayServerEntityLook look = new WrapperPlayServerEntityLook();
        look.setEntityID(123);
        look.setYaw(-player.getLocation().getYaw());
        look.setPitch(-player.getLocation().getPitch());

        PacketContainer metadata = Creative.getPlugin().getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metadata.getIntegers().write(0, 123);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.WrappedDataWatcherObject poseWatcher
                = new WrappedDataWatcher.WrappedDataWatcherObject(6, WrappedDataWatcher.Registry.get(EnumWrappers.getEntityPoseClass()));
        EnumWrappers.EntityPose pose = EnumWrappers.EntityPose.SLEEPING;
        watcher.setObject(poseWatcher, pose.toNms());
        metadata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        Creative.getPlugin().getProtocolManager().broadcastServerPacket(spawn.getHandle());
        Creative.getPlugin().getProtocolManager().broadcastServerPacket(look.getHandle());
        Creative.getPlugin().getProtocolManager().broadcastServerPacket(metadata);


    }

}
