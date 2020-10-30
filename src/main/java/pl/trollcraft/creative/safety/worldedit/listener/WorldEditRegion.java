package pl.trollcraft.creative.safety.worldedit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.safety.worldedit.event.WorldEditCommandEvent;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditRegion implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWorldEditCommand (WorldEditCommandEvent event) {

        WorldEditCommand command = event.getCommand();
        Player player = event.getPlayer();

    }

}
