package pl.trollcraft.creative.safety.leaks.redstone;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import pl.trollcraft.creative.safety.leaks.SafetyLeak;
import pl.trollcraft.creative.safety.leaks.SafetyProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Deprecated
public class RedstoneSafetyProvider implements SafetyProvider, Listener {

    private static final int ID = 2000;

    private Multimap<Chunk, Block> redstone;
    private ArrayList<Chunk> blocked;

    private int redstoneLimit;

    public RedstoneSafetyProvider(int redstoneLimit) {
        redstone = ArrayListMultimap.create();
        blocked = new ArrayList<>();

        this.redstoneLimit = redstoneLimit;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public SafetyLeak scan() {

        Set<Chunk> chunks = redstone.keySet();
        Collection<Block> blocks;

        for (Chunk c : chunks) {

            blocks = redstone.get(c);
            if (blocks.size() >= redstoneLimit)
                return new RedstoneLeak(c, blocks);

        }

        return null;

    }

    @EventHandler
    public void onRedstone (BlockRedstoneEvent event) {

        Block block = event.getBlock();
        Chunk chunk = block.getChunk();
        Collection<Block> blocks = redstone.get(chunk);
        if (!blocks.contains(block))
            blocks.add(block);

    }

}
