package pl.trollcraft.creative.essentials.antyafk;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.events.MovementDetectEvent;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashMap;

public class AntyAfkManager implements Listener {

    private static final long AFK = 5 * 60 * 1000;

    private final HashMap<Player, Long> lastMovements;

    private AntyAfkManager() {
        lastMovements = new HashMap<>();
        listen();
    }

    public static AntyAfkManager newInstance() {
        return new AntyAfkManager();
    }

    private void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                long now = System.currentTimeMillis();
                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter( player -> !player.hasPermission("creative.afk") )
                        .forEach( player -> {

                    if (!lastMovements.containsKey(player))
                        return;

                    long lastMove = lastMovements.get(player);

                    if (now - lastMove >= AFK) {
                        player.kickPlayer(Colors.color("&7Wyrzucono z powodu &e5 minut bezczynnosci."));
                        lastMovements.remove(player);
                    }

                } );

            }

        }.runTaskTimer(Creative.getPlugin(), 20, 20);

    }

    @EventHandler
    public void onMovementDetect (MovementDetectEvent event) {
        Player player = event.getPlayer();

        if (lastMovements.containsKey(player))
            lastMovements.replace(player, System.currentTimeMillis());
        else
            lastMovements.put(player, System.currentTimeMillis());

    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (lastMovements.containsKey(player))
            lastMovements.replace(player, System.currentTimeMillis());
        else
            lastMovements.put(player, System.currentTimeMillis());

    }

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (lastMovements.containsKey(player))
            lastMovements.replace(player, System.currentTimeMillis());
        else
            lastMovements.put(player, System.currentTimeMillis());

    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        lastMovements.remove(player);
    }

}
