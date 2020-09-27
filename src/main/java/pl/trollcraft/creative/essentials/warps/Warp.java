package pl.trollcraft.creative.essentials.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.Configs;

import java.util.ArrayList;

public class Warp {

    private static ArrayList<Warp> warps = new ArrayList<Warp>();

    private String name;
    private Location location;

    public static Location locFromString(String str) {
        String[] str2loc = str.split(":");
        Location loc = new Location((World) Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        loc.setYaw(Float.parseFloat(str2loc[3]));
        loc.setPitch(Float.parseFloat(str2loc[4]));
        return loc;
    }
    public static String locToString(Location loc) { return loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch(); }

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
        warps.add(this);
    }


    public void teleport(Player player) {
        if (location.getWorld() == null) player.sendMessage("sie zjebao");
        if (!location.getChunk().isLoaded()) location.getChunk().load();
        player.teleport(location);
        Bukkit.getPluginManager().callEvent(new PlayerWarpEvent(player, this));
    }

    public void save() {
        YamlConfiguration conf = Configs.load("warps.yml");
        conf.set("warps." + name + ".world", location.getWorld().getName());
        conf.set("warps." + name + ".loc", locToString(location));
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

            Location loc = locFromString(conf.getString("warps." + name + ".loc"));

            if (conf.contains("warps." + name + ".world")){
                World world = Bukkit.getWorld(conf.getString("warps." + name + ".world"));
                loc = new Location(world, loc.getX(), loc.getY(), loc.getZ());
            }
            new Warp(name, loc);
        } );
    }

}