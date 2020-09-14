package pl.trollcraft.creative.safety.leaks.redstone;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.ArrayList;

public class RedstoneListener implements Listener {

    private ArrayList<Block> blocked;

    public RedstoneListener() {
        blocked = new ArrayList<>();
    }

    public void block(Block block) {
        blocked.add(block);
    }

    @EventHandler
    public void onRedstone (BlockRedstoneEvent event) {

        Block block = event.getBlock();
        if (blocked.contains(block))
            event.setNewCurrent(0);

    }

}