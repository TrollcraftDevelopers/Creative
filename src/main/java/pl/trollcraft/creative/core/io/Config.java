package pl.trollcraft.creative.core.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config implements AutoCloseable{

    /**
     * Core object of the config
     * - the file.
     */
    private final File file;

    /**
     * Processed yaml configuration
     * model.
     */
    private final YamlConfiguration yaml;

    /**
     * Creates all the needed directories
     * and the file.
     *
     * @param path location of the file.
     * @throws IOException thrown when file does not exist
     */
    public Config (String path, String fileName) throws IOException, InvalidConfigurationException {

        Path p = Paths.get(path);
        Files.createDirectories(p);

        file = new File(path, fileName);

        if (!file.exists())
            file.createNewFile();

        yaml = new YamlConfiguration();
        yaml.load(file);
    }

    /**
     * Gets the yaml configuration model.
     *
     * @return yaml model.
     */
    public YamlConfiguration yaml(){
        return yaml;
    }

    @Override
    public void close() throws Exception {
        yaml.save(file);
    }

}
