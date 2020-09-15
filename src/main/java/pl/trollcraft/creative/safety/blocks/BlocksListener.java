package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.help.Colors;

public class BlocksListener implements Listener {

    private Controller<Limit, Material> limitsController = Creative.getPlugin().getLimitsController();
    private BlocksController blocksController = Creative.getPlugin().getBlocksController();

    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Material type = event.getBlock().getType();

        int b = blocksController.get(chunk, type);
        Limit limit = limitsController.find(type);
        if (limit == null)
            return;

        if (b > limit.getAmount()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Colors.color("&c&lLIMIT! &cNie mozesz postawic wiecej blokow tego typu na tym chunk'u."));
        }
        else blocksController.add(chunk, type);

    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        Chunk chunk = event.getBlock().getChunk();
        Material type = event.getBlock().getType();
        blocksController.remove(chunk, type);
    }

}
