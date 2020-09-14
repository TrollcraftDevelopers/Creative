package pl.trollcraft.creative.safety.leaks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SafetyListener implements Listener {

    @EventHandler
    public void onLeakDetect (LeakDetectEvent event) {

        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("creative.safety"))
                .forEach( player -> player.sendMessage(event.getLeak().message()) );

        event.getLeak().solve();

    }

}
