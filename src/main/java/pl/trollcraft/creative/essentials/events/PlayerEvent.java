package pl.trollcraft.creative.essentials.events;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;

public class PlayerEvent {

    private Player owner;
    private ArrayList<Player> participants;
    private String title;

    public PlayerEvent (Player owner, String title) {
        this.owner = owner;
        participants = new ArrayList<>();
        this.title = title;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean participates(Player player) {
        return participants.contains(player);
    }

    public boolean join(Player player) {
        if (participants.contains(player))
            return false;

        participants.forEach(p ->
                p.sendMessage(Colors.color("&e" + player.getName() + " &adolacza do event'u!")));
        participants.add(player);

        return true;
    }

    public boolean leave(Player player) {
        if (!participants.contains(player))
            return false;

        participants.remove(player);
        participants.forEach(p ->
                p.sendMessage(Colors.color("&e" + player.getName() + " &7opuscil event!")));

        return true;
    }

    public String toAnnouncement() {
        StringBuilder builder = new StringBuilder();
        builder.append(Colors.color("&2&lNOWY EVENT!"));
        builder.append(String.format("&aZorganizowany przez &e%s", owner.getName()));
        builder.append("\n");
        builder.append(title);
        builder.append("\n");
        builder.append("&e&lKLIKNIJ, BY DOLACZYC.");
        return builder.toString();
    }
}
