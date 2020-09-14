package pl.trollcraft.creative.core.help.blocksdetector;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class BlockDetector implements Listener {

    private Plugin plugin;

    /**
     * A requests of players to
     * execute certain tasks on block
     * detect.
     */
    private List<DetectionRequest> requests;

    /**
     * Blocks that detector should ignore
     * for certain request.
     */
    private HashMap<DetectionRequest, Block> ignore;

    public BlockDetector(Plugin plugin) {
        this.plugin = plugin;
        requests = new ArrayList<>();
        ignore = new HashMap<>();
    }

    /**
     * A monitoring scheduled task
     * running every 1/2 second.
     */
    public void monitor() {

        new BukkitRunnable() {

            @Override
            public void run() {

                requests.forEach( request -> {

                    Block block =
                            request.getPlayer().getLocation().subtract(0, 1, 0).getBlock();

                    if (ignore.containsKey(request) && ignore.get(request).equals(block))
                        return;

                    if (request.getType().equals(block.getType())) {
                        request.getTask().accept(request);

                        if (request.shouldBeIgnored()) {

                            if (ignore.containsKey(request))
                                ignore.replace(request, block);
                            else
                                ignore.put(request, block);

                        }

                    }

                } );

            }

        }.runTaskTimer(plugin, 10, 10);

        plugin.getLogger().log(Level.INFO, "Started BlockDetector.");

    }

    /**
     * Defines a new detection request.
     *
     * @param request
     */
    public void define(DetectionRequest request) {
        requests.add(request);
    }

    /**
     * Undefines (removes) a request
     * from the list.
     *
     * @param player
     * @param tag
     */
    public boolean undefine(Player player, String tag) {
        return requests.removeIf( request -> request.getPlayer().equals(player) && request.getTag().equals(tag) );
    }

}
