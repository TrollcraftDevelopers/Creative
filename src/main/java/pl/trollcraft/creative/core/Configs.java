package pl.trollcraft.creative.core;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.Creative;

import java.io.File;
import java.io.IOException;

public class Configs {

    private static final Creative creative = Creative.getPlugin();

    public static YamlConfiguration load(String configName){
        YamlConfiguration config;
        File file = new File(creative.getDataFolder() + File.separator + configName);
        if (!file.exists())
            creative.saveResource(configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(creative.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
