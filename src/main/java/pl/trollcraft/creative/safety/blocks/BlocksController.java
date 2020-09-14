package pl.trollcraft.creative.safety.blocks;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Chunk;
import org.bukkit.Material;

import java.util.Collection;

public class BlocksController {

    private Multimap<Chunk, Blocks> chunks;

    public BlocksController() {
        this.chunks = ArrayListMultimap.create();
    }

    public int get(Chunk chunk, Material material) {
        Collection<Blocks> blocks = chunks.get(chunk);
        for (Blocks b : blocks) {
            if (b.getType() == material) {
                return b.getAmount();
            }
        }
        return 0;
    }

    public int add(Chunk chunk, Material material) {
        Collection<Blocks> blocks = chunks.get(chunk);
        for (Blocks b : blocks) {
            if (b.getType() == material) {
                b.add();
                return b.getAmount();
            }
        }
        Blocks b = new Blocks(material);
        blocks.add(b);
        return b.getAmount();
    }

    public int remove(Chunk chunk, Material material) {
        Collection<Blocks> blocks = chunks.get(chunk);
        for (Blocks b : blocks) {
            if (b.getType() == material) {
                b.remove();
                return b.getAmount();
            }
        }
        return 0;
    }

}
