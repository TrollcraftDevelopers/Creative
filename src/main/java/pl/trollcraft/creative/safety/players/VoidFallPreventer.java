package pl.trollcraft.creative.safety.players;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;

public class VoidFallPreventer {

    public static void observePlayers() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> p.getLocation().getBlockY() <= 0)
                        .forEach(p -> {
                            p.teleport(p.getWorld().getSpawnLocation());
                            p.sendMessage(Colors.color("&7Wypadles za mape, milosierny Creative uratowal Ci zycie <3"));
                        });

            }

        }.runTaskTimer(Creative.getPlugin(), 20, 20);

    }

}
