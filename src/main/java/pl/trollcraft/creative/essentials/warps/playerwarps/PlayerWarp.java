package pl.trollcraft.creative.essentials.warps.playerwarps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Colors;
import sun.security.krb5.Config;

import java.util.HashMap;

public class PlayerWarp {

    private static HashMap<String, HashMap<String, Location>> players_warps = new HashMap<>();

    public static void addWarp(String player_name, String warp_name, Location location){
        if(!players_warps.containsKey(player_name)){
            players_warps.put(player_name, new HashMap<>());
        }
        players_warps.get(player_name).put(warp_name, location);
    }

    public static void removeWarp(String player_name, String warp_name){
        players_warps.get(player_name).remove(warp_name);
    }

    public static Location getWarpByName(String player_name, String warp_name){
        return players_warps.get(player_name).get(warp_name);
    }

    public static void showWarpsOfPlayer(Player target, String player_name){
        String warps = "&aWarpy gracza " + player_name + ":&7";
        for(String key : players_warps.get(player_name).keySet()){
            warps = warps + key + ", ";
        }
        target.sendMessage(Colors.color(warps));
    }

    public static boolean doesWarpExist(String player_name, String warp_name){
        if(players_warps.containsKey(player_name)){
            if(players_warps.get(player_name).containsKey(warp_name)){
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

    public static int getPlayerWarpsAmount(String player_name){
        if(players_warps.containsKey(player_name)){
            return players_warps.get(player_name).size();
        }
        else{
            return 0;
        }
    }

    public static void savePlayerWarps(String player_name){
        if(!players_warps.containsKey(player_name)){
            return;
        }

        YamlConfiguration configuration = Configs.load("players_warps.yml");

        configuration.set("warps." + player_name, null);

        for(String key : players_warps.get(player_name).keySet()){
            Location location = players_warps.get(player_name).get(key) ;

            configuration.set("warps." + player_name + "." + key + ".x", location.getX());
            configuration.set("warps." + player_name + "." + key + ".y", location.getX());
            configuration.set("warps." + player_name + "." + key + ".z", location.getX());
            configuration.set("warps." + player_name + "." + key + ".world", location.getWorld().getName());
        }

        Configs.save(configuration, "players_warps.yml");
    }

    public static void loadPlayerWarps(String player_name){
        YamlConfiguration configuration = Configs.load("players_warps.yml");

        if(!configuration.contains("warps." + player_name)){
            return;
        }

        HashMap<String, Location> temp_homes = new HashMap<>();

        for(String key : configuration.getConfigurationSection("warps." + player_name).getKeys(false)){
            double x = configuration.getDouble("warps." + player_name + "." + key + ".x");
            double y = configuration.getDouble("warps." + player_name + "." + key + ".y");
            double z = configuration.getDouble("warps." + player_name + "." + key + ".z");
            String world = configuration.getString("warps." + player_name + "." + key + ".world");

            temp_homes.put(key, new Location(Bukkit.getWorld(world),x, y, z));
        }

        players_warps.put(player_name, temp_homes);
    }
}
