package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;

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
                register(new Limit(type, amount));
                Bukkit.getLogger().log(Level.INFO, "Loaded limit for: " + type.name());
            }

        } );

    }

}
