package pl.trollcraft.creative.core.help.blockades;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import pl.trollcraft.creative.core.controlling.Controller;

public class BlockadesController extends Controller<Blockade, String> {

    @Nullable
    @Override
    public Blockade find(String id) {
        return instances.stream()
                .filter(blo -> blo.getName().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Blockade newInstance(String name) {
        Blockade blockade = new Blockade(name);
        register(blockade);
        return blockade;
    }

    public void load(Player player) {
    }

    public void save() {
    }

}
