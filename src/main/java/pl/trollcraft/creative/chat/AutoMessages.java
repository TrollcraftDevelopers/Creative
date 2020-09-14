package pl.trollcraft.creative.chat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;
import java.util.HashMap;

public class AutoMessages {

    private static HashMap<Player, Boolean> autoMessagesEnabled = new HashMap<>();
    private static ArrayList<String> messages = new ArrayList<>();
    private static int index = 0;
    private static String prefix;

    public static void init() {

        YamlConfiguration conf = Configs.load("automessages.yml");
        long interval = conf.getLong("automessages.interval") * 20l;
        conf.getStringList("automessages.messages").forEach( msg -> messages.add(Colors.color(msg)) );
        prefix = Colors.color(conf.getString("automessages.prefix"));

        new BukkitRunnable() {

            @Override
            public void run() {

                if (messages.isEmpty()) return;

                String message = Colors.color(messages.get(index));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage("");
                    p.sendMessage(prefix + " " + message);
                    p.sendMessage("");
                }

                if (index == messages.size() - 1)
                    index = 0;
                else
                    index++;

            }

        }.runTaskTimerAsynchronously(Creative.getPlugin(), interval, interval);


    }

}
