package pl.trollcraft.creative.essentials.homes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import pl.trollcraft.creative.core.Configs;

import java.util.*;

/**
 * Home class, Responsible for creating and loading the homes.
 */
public class Home {
    /**
     * List of all homes
     */
    public static Map<String, List<Home>> homes = new HashMap<>();
    /**
     *  Function to get a single home from target player and target name.
     * @return Home h if home exists or null if home doesn't not exist
     */
    public static Home gethome(Player p, String home) {
        for(Map.Entry<String, List<Home>> entry : homes.entrySet()){
            if (entry.getKey().equals(p.getName())){
                for (Home h : entry.getValue()){
                    if(h.getName().equals(home)){
                        return h;
                    }
                }
            }
        }
        return null;
    }
    /**
     *  Function to get a list  of homes from target player and target name.
     * @return Homes if homes exists or null if homes doesn't not exist
     */
    public static List<Home> gethomes(Player p) {
        for (Map.Entry<String, List<Home>> entry : homes.entrySet()) {
            if (entry.getKey().equals(p.getName())) {
                return entry.getValue();
            }
        }
        return null;
    }


    private String name;
    private Location location;

    /**
     * Constructor of the home object
     * @param name name of the home in String
     * @param section section of the configuration
     */
    public Home(String name, ConfigurationSection section){
        this.name = name;
        World w = Bukkit.getWorld(section.getString("World"));
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        this.location = new Location(w,x,y,z);
    }

    public static void save(Player player, String name) {
        YamlConfiguration conf = Configs.load("homes.yml");
        String key = "homes" + player.getName() + "." + name;
        conf.set( key + ".World", player.getWorld().getName());
        conf.set( key + ".x", player.getLocation().getX());
        conf.set( key + ".y", player.getLocation().getY());
        conf.set( key + ".z", player.getLocation().getZ());
        Configs.save(conf,"homes.yml");
    }
    public static void load(Player player){
        YamlConfiguration conf = Configs.load("homes.yml");
        List<Home> list = new ArrayList<>();
        if(conf.contains(player.getName())){
            for(String s : conf.getConfigurationSection("homes."+ player.getName()).getKeys(false)){
                Home home = new Home(s, Objects.requireNonNull(conf.getConfigurationSection("homes." + player.getName() + "." + s)));
                list.add(home);
            }
            homes.put(player.getName(),list);
        }
    }
    public void delete(Player player){
        YamlConfiguration conf = Configs.load("homes.yml");
        String key = "homes" + player.getName() + "." + name;
        conf.set( key + ".World", null);
        conf.set( key + ".x", null);
        conf.set( key + ".y", null);
        conf.set( key + ".z", null);
        homes.remove(name);
        Configs.save(conf,"homes.yml");

    }

    public String getName(){
        return name;
    }
    public Location getLocation(){
        return location;
    }

}


