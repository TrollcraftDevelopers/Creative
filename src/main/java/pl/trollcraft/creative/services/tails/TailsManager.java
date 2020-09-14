package pl.trollcraft.creative.services.tails;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.betterparticles.ParticlesParser;
import pl.trollcraft.creative.core.betterparticles.Particles;

import java.util.logging.Level;

public class TailsManager {

    public static void load() {

        YamlConfiguration conf = Configs.load("tails.yml");
        conf.getConfigurationSection("tails").getKeys(false).forEach( id -> {

            String title = conf.getString("tails." + id + ".title");
            Particles particles = ParticlesParser.parse(conf.getString("tails." + id + ".particles"));
            String engine = conf.getString("tails." + id + ".engine");

            if (engine.equalsIgnoreCase("ClassicTail"))
                new ClassicTail(id, title, particles);
            else if (engine.equalsIgnoreCase("RandomOffsetTail"))
                new RandomOffsetTail(id, title, particles);

            else
                Bukkit.getLogger().log(Level.WARNING, "Unknown Tail Engine.");

        } );

    }

}
