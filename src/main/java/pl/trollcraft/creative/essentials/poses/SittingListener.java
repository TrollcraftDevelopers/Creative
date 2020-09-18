package pl.trollcraft.creative.essentials.poses;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class SittingListener implements Listener {

    @EventHandler
    public void onInteract (EntityDismountEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Entity dismounted = event.getDismounted();

        Sitting.standUp(player, dismounted);
    }

}
