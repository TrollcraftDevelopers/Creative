package pl.trollcraft.creative.safety.limits.chunks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.io.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

public class ChunkLimitsController extends Controller<ChunkLimit, Chunk> {

    @Override
    public ChunkLimit find(Chunk id) {
        return instances.stream()
                .filter( c -> c.getChunk().equals(id) )
                .findAny()
                .orElse(null);
    }

    public void save(ChunkLimit chunkLimit) {

        if (chunkLimit.getItems().isEmpty())
            return;

            try (Config config = new Config(
                    Creative.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "chunks",
                    String.format("%dx%d.yml", chunkLimit.getChunk().getX(), chunkLimit.getChunk().getZ())
            )){

                ArrayList<String> serialized = new ArrayList<>();
                chunkLimit.getItems().forEach( (type, am) ->
                    serialized.add(String.format("%s=%d", type.name(), am))
                );

                config.yaml().set("limits", serialized);

            }
            catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void save() {

        Iterator<ChunkLimit> it = instances.iterator();
        ChunkLimit lim;

        while (it.hasNext()) {

            lim = it.next();
            if (lim.getItems().isEmpty()) {
                File file = new File(Creative.getPlugin().getDataFolder() + File.separator + "chunks",
                        lim.getChunk().getX() + "x" + lim.getChunk().getZ() + ".yml");

                if (file.exists())
                    file.delete();

                it.remove();
                Bukkit.getLogger().log(Level.INFO, "deleted");
            }

        }

        instances.removeIf( chunkLimit -> chunkLimit.getItems().isEmpty() );
        instances.forEach(this::save);

    }

    private void clear(ChunkLimit chunkLimit) {



        unregister(chunkLimit);

    }

    //TODO should return a value?
    public ChunkLimit load(Chunk chunk) {

        ChunkLimit limit = null;

        try (Config config = new Config(
                Creative.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "chunks",
                String.format("%dx%d.yml", chunk.getX(), chunk.getZ())
        )){

            if (config.yaml().contains("limits")){

                HashMap<Material, Integer> limits = new HashMap<>();
                config.yaml().getStringList("limits")
                        .forEach(el -> {

                            String[] data = el.split("=");
                            Material type = Material.getMaterial(data[0]);
                            int am = Integer.parseInt(data[1]);
                            limits.put(type, am);

                        });

                limit = new ChunkLimit(chunk, limits);
                register(limit);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return limit;

    }

}
