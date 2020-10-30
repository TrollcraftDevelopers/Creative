package pl.trollcraft.creative.safety.worldedit.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.safety.worldedit.event.WorldEditCommandEvent;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditCopy implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onWorldEditCommand (WorldEditCommandEvent event) {

        WorldEditCommand command = event.getCommand();

        if (command.getName().equalsIgnoreCase("copy")) {

            Region region = event.getRegion();
            if (!isRegionOK(region)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Colors.color("&c&lUWAGA! &cNie mozesz kopiowac poniewaz region zawiera zablokowane bloki."));
            }
        }

    }

    /**
     * Check if region contains any
     * limited blocks, if so - it stops.
     *
     * @param region
     * @return
     */
    private static boolean isRegionOK(Region region) {

        int xMin = region.getMinimumPoint().getBlockX();
        int yMin = region.getMinimumPoint().getBlockY();
        int zMin = region.getMinimumPoint().getBlockZ();

        int xMax = region.getMaximumPoint().getBlockX();
        int yMax = region.getMaximumPoint().getBlockY();
        int zMax = region.getMaximumPoint().getBlockZ();

        World world = BukkitAdapter.adapt(region.getWorld());
        Block b;
        /*for (int x = xMin ; x <= xMax ; x++) {
            for (int y = yMin ; y <= yMax ; y++) {
                for (int z = zMin ; z <= zMax ; z++) {

                    b = world.getBlockAt(x, y ,z);
                    if (limitsController.find(b.getType()) != null)
                        return false;

                }

            }

        }*/

        return true;

    }

}
