package pl.trollcraft.creative.safety.limits.chunks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.safety.limits.file.LimitFilesController;

import java.util.HashMap;
import java.util.logging.Level;

public class ChunkLimitsListener implements Listener {

    private final ChunkLimitsController chunkLimitsController
            = Creative.getPlugin().getChunkLimitsController();

    private final LimitFilesController limitFilesController
            = Creative.getPlugin().getLimitFilesController();

    private final String REG = "chunks";

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Chunk chunk = event.getBlockPlaced().getChunk();

        // I. chunk is already loaded
        ChunkLimit limit = chunkLimitsController.find(chunk);
        if (limit != null) {

            Bukkit.getLogger().log(Level.INFO, "Chunk is loaded.");

            Material type = event.getBlockPlaced().getType();

            int am = limit.get(type);
            int lim = limitFilesController.findInRegister(REG, type);


            Bukkit.getLogger().log(Level.INFO, "am=" + am + ", lim=" + lim);

            if (lim != -1 && lim <= am) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(
                        Colors.color("&cNie mozesz polozyc wiecej tego typu blokow na tym chunku."));
            }
            else
                limit.logPlaced(type);

        }

        else {

            limit = chunkLimitsController.load(chunk);

            // II. Chunk has been loaded.
            if (limit != null) {

                Bukkit.getLogger().log(Level.INFO, "Chunk has been loaded.");

                Material type = event.getBlockPlaced().getType();
                int am = limit.get(type);
                int lim = limitFilesController.findInRegister(REG, type);

                Bukkit.getLogger().log(Level.INFO, "am=" + am + ", lim=" + lim);

                if (lim != -1 && lim <= am) {
                    event.setCancelled(true);
                    limit.logPlaced(type);
                    event.getPlayer().sendMessage(
                            Colors.color("&cNie mozesz polozyc wiecej tego typu blokow na tym chunku."));
                }
                else
                    limit.logPlaced(type);

            }

            // III. Chunk has not been registered yet.
            else {

                Material type = event.getBlockPlaced().getType();
                int am = 1;
                int lim = limitFilesController.findInRegister(REG, type);

                if (lim != -1 && lim <= am) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(
                            Colors.color("&cNie mozesz polozyc wiecej tego typu blokow na tym chunku."));
                }
                else {
                    HashMap<Material, Integer> limits = new HashMap<>();
                    limits.put(type, 1);

                    chunkLimitsController.register(new ChunkLimit(chunk, limits));

                    Bukkit.getLogger().log(Level.INFO, "Chunk has been registered.");
                }

            }

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Chunk chunk = event.getBlock().getChunk();

        // I. chunk is already loaded
        ChunkLimit limit = chunkLimitsController.find(chunk);
        if (limit != null) {

            Bukkit.getLogger().log(Level.INFO, "Chunk is loaded.");

            Material type = event.getBlock().getType();
            limit.logBroken(type);

        }

        else {

            limit = chunkLimitsController.load(chunk);

            // II. Chunk has been loaded.
            if (limit != null) {

                Bukkit.getLogger().log(Level.INFO, "Chunk has been loaded.");

                Material type = event.getBlock().getType();
                limit.logBroken(type);

            }

        }

    }

}
