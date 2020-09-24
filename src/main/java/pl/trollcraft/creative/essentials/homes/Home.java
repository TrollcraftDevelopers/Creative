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

    private static HashMap<String, List<Home>> homes = new HashMap<>();

    private String name;
    private Location location;

    public Home(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public static void addHome(Player player, String name){
        if(!homes.containsKey(player.getName())){
            homes.put(player.getName(), new ArrayList<>());
        }
        Home home = new Home(name, player.getLocation());
        if(doesHomeExist(player, name)){
            Home home1 = getHomeByName(player, name);
            homes.get(player.getName()).remove(home1);
        }
        homes.get(player.getName()).add(home);
        save(player, name);
    }

    public void delHome(Player player, String name){
        Home to_delete = null;
        for(Home home : homes.get(player.getName())){
            if(home.getName().equalsIgnoreCase(name)){
                to_delete = home;
                break;
            }
        }
        homes.get(player.getName()).remove(to_delete);
        delete(player);
    }

    public static Home getHomeByName(Player player, String name){
        for(Home home : homes.get(player.getName())){
            if(home.getName().equalsIgnoreCase(name)){
                return home;
            }
        }
        return null;
    }

    public static List getHomeListByPlayer(Player player){
        return homes.get(player.getName());
    }

    public static boolean doesPlayerHasHomes(Player player){
        if(!homes.containsKey(player.getName())){
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean doesHomeExist(Player player, String name){
        for(Home home : homes.get(player.getName())){
            if(home.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public static int getHomesAmount(Player player){
        if(homes.containsKey(player.getName())){
            return homes.get(player.getName()).size();
        }
        else{
            return 0;
        }
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
                double x = conf.getDouble("homes." + player.getName() + "." + s + ".x");
                double y = conf.getDouble("homes." + player.getName() + "." + s + ".x");
                double z = conf.getDouble("homes." + player.getName() + "." + s + ".x");
                String world = conf.getString("homes." + player.getName() + "." + s + ".World");
                Home home = new Home(s, new Location(Bukkit.getWorld(world),x, y, z));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}


