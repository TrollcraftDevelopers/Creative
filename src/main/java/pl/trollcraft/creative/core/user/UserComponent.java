package pl.trollcraft.creative.core.user;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public interface UserComponent {

    String getName();

    void save(YamlConfiguration conf, String path, Player player);
    void load(YamlConfiguration conf, String path, Player player);

    boolean isEmpty();

}
