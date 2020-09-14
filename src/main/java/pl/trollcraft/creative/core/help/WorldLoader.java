package pl.trollcraft.creative.core.help;

import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;

public class WorldLoader {

    public static void load() {
        YamlConfiguration conf = Configs.load("worlds.yml");
        conf.getStringList("worlds").forEach( worldName -> new WorldCreator(worldName) );
    }

}
