package pl.trollcraft.creative.core.help.movement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.events.MovementDetectEvent;

import java.util.HashMap;

public class MovementDetector {

    private HashMap<Player, Location> locations;

    private MovementDetector() {
        locations = new HashMap<>();
        listen();
    }

    public static MovementDetector newInstance() {
        return new MovementDetector();
    }

    private void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach( player -> {

                    if (!locations.containsKey(player))
                        locations.put(player, player.getLocation());

                    else {

                        Location current = player.getLocation();
                        Location last = locations.get(player);

                        if (!current.equals(last)) {
                            locations.replace(player, current);
                            Bukkit.getPluginManager().callEvent(new MovementDetectEvent(player));
                        }
                    }

                });

            }

        }.runTaskTimer(Creative.getPlugin(), 10, 10);

    }

}
