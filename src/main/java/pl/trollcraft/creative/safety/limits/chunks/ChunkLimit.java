package pl.trollcraft.creative.safety.limits.chunks;

import org.bukkit.Chunk;
import org.bukkit.Material;

import java.util.HashMap;

public class ChunkLimit {

    private Chunk chunk;
    private HashMap<Material, Integer> items;

    public ChunkLimit(Chunk chunk, HashMap<Material, Integer> items) {
        this.chunk = chunk;
        this.items = items;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public HashMap<Material, Integer> getItems() {
        return items;
    }

    public int get(Material material) {

        if (items.containsKey(material))
            return items.get(material);
        return 0;

    }

    public void logPlaced(Material material) {
        if (items.containsKey(material))
            items.replace(material, items.get(material) + 1);
        else
            items.put(material, 1);
    }

    public void logBroken(Material material) {

        if (items.containsKey(material)) {

            int am = items.get(material) - 1;
            if (am == 0)
                items.remove(material);
            else
                items.replace(material, am);

        }

    }

}
