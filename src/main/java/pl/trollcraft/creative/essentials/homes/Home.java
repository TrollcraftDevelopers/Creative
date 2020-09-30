package pl.trollcraft.creative.essentials.homes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Colors;

import java.util.*;

/**
 * Home class, Responsible for creating and loading the homes.
 */
public class Home {

    private static final HashMap<String, HashMap<String, Location>> homes = new HashMap<>();

    public static void addHome(String player_name, String home_name, Location location){
        if(!homes.containsKey(player_name)){
            homes.put(player_name, new HashMap<>());
        }
        homes.get(player_name).put(home_name, location);
    }

    public static void removeHome(String player_name, String home_name){
        homes.get(player_name).remove(home_name);
    }

    public static Location getHomeLocationByName(String player_name, String home_name){
        return  homes.get(player_name).get(home_name);
    }

    public static void showHomes(Player player){
        if(!homes.containsKey(player.getName())){
            player.sendMessage(Colors.color("&cNie posiadasz zadnych domow!"));
            return;
        }

        String message = "";
        HashMap<String, Location> temp_homes = homes.get(player.getName());

        message = message + "&aDostepne home'y to: &7";

        for(String home_name : temp_homes.keySet()){
            message = message + home_name + ",";
        }

        player.sendMessage(Colors.color(message));
    }

    public static boolean doesPlayerHasHomes(String player_name){
        if(homes.containsKey(player_name)){
            return true;
        }
        else{
            return false;
        }
    }

    public static int getPlayerHomesNumber(String player_name){
        return homes.get(player_name).size();
    }

    public static boolean doesHomeExist(String player_name, String home_name){
        if(homes.containsKey(player_name)){
            if(homes.get(player_name).containsKey(home_name)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public static void load(Player player){
        String name = player.getName();

        YamlConfiguration configuration = Configs.load("homes.yml");
        if(!configuration.contains("homes." + name)){
            return;
        }

        HashMap<String, Location> temp_homes = new HashMap<>();
        for(String key : configuration.getConfigurationSection("homes." + name).getKeys(false)){
            double x = configuration.getDouble("homes." + name + "." + key + ".x");
            double y = configuration.getDouble("homes." + name + "." + key + ".y");
            double z = configuration.getDouble("homes." + name + "." + key + ".z");
            String world = configuration.getString("homes." + name + "." + key + ".world");
            temp_homes.put(key, new Location(Bukkit.getWorld(world), x, y, z));
        }

        homes.put(name, temp_homes);
        System.out.println("Zaladowano domy gracza " + name);
    }

    public static void save(Player player){
        String name = player.getName();

        if(!homes.containsKey(name)){
            return;
        }

        YamlConfiguration configuration = Configs.load("homes.yml");
        configuration.set("homes." + name, null);

        HashMap<String, Location> temp_homes = homes.get(name);
        for(String home_name : temp_homes.keySet()){
            Location location = temp_homes.get(home_name);

            configuration.set("homes." + name + "." + home_name + ".x", location.getX());
            configuration.set("homes." + name + "." + home_name + ".y", location.getY());
            configuration.set("homes." + name + "." + home_name + ".z", location.getZ());
            configuration.set("homes." + name + "." + home_name + ".world", location.getWorld().getName());
        }

        Configs.save(configuration, "homes.yml");
        homes.remove(name);
        System.out.println("Zapisano domy gracza " + name);
    }




}


