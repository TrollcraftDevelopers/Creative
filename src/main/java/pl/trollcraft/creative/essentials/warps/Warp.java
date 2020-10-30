package pl.trollcraft.creative.essentials.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Help;

import java.util.ArrayList;

public class Warp {

    private static ArrayList<Warp> warps = new ArrayList<Warp>();

    private String name;
    private Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
        warps.add(this);
    }

    public void teleport(Player player) {
        if (!location.getChunk().isLoaded()) location.getChunk().load();
        player.teleport(location);
        Bukkit.getPluginManager().callEvent(new PlayerWarpEvent(player, this));
    }

    public void save() {
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.set("warps." + name + ".loc", Help.locationToString(location));
        Configs.save(conf, "warps.yml");
    }

    public void delete(){
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.set("warps." + name + ".world", null);
        conf.set("warps." + name + ".loc", null);
        Configs.save(conf, "warps.yml");
        warps.removeIf(warp -> warp.getName().equals(name));
        Configs.save(conf, "warps.yml");
    }

    private String getName() { return name;
    }

    public static Warp get(String name) {
        for (Warp w : warps){
            if (w.name.equalsIgnoreCase(name)) return w;
        }
        return null;
    }

    public static void load() {
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.getConfigurationSection("warps").getKeys(false).forEach( name -> {

            Location loc = Help.stringToLocation(conf.getString("warps." + name + ".loc"));
            new Warp(name, loc);

        } );
    }

}