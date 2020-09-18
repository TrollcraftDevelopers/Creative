package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.List;
import java.util.logging.Level;

public class LimitsController extends Controller<Limit, Material> {

    public void load() {

        YamlConfiguration conf = Configs.load("limits.yml");
        conf.getConfigurationSection("limits").getKeys(false).forEach( typeName -> {

            Material type = Material.getMaterial(typeName);
            if (type == null)
                Bukkit.getLogger().log(Level.SEVERE, "Unrecognized material: " + typeName);

            else {
                int amount = conf.getInt("limits." + typeName + ".amount");

                List<String> aliases = null;
                if (conf.contains("limits." + typeName + ".aliases"))
                    aliases = conf.getStringList("limits." + typeName + ".aliases");

                register(new Limit(type, aliases, amount));
                Bukkit.getLogger().log(Level.INFO, "Loaded limit for: " + type.name());
            }

        } );

    }

    public boolean isLimited(String exp) {
        String low = exp.toLowerCase();
        for (Limit l : instances)
            if (l.contains(low)) return true;
        return false;
    }

}
