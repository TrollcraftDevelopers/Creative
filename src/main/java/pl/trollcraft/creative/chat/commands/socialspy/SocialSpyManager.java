package pl.trollcraft.creative.chat.commands.socialspy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashSet;
import java.util.Set;

public class SocialSpyManager {

    private Set<Player> spies;

    private SocialSpyManager() {
        spies = new HashSet<>();
    }

    public static SocialSpyManager newInstance() {
        return new SocialSpyManager();
    }

    public void add(Player player) {
        spies.add(player);
    }

    public void remove(Player player) {
        spies.remove(player);
    }

    public boolean isSpy(Player player) {
        return spies.contains(player);
    }

    public void forward(String message, CommandSender from, CommandSender to) {
        String formatted = Colors.color("&e(" + from.getName() + " > " + to.getName() + ") &f" + message);
        spies.forEach( spy -> spy.sendMessage(formatted) );
    }

}
