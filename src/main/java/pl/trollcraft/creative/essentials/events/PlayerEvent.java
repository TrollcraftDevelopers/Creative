package pl.trollcraft.creative.essentials.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerEvent {

    private Player owner;
    private ArrayList<Player> participants;
    private String title;

    private Map<Player, Location> returnLocations;

    public PlayerEvent (Player owner, String title) {
        this.owner = owner;
        participants = new ArrayList<>();
        this.title = title;
        returnLocations = new HashMap<>();
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
        owner.sendMessage(Colors.color("&e" + player.getName() + " &adolacza do Twojego event'u!"));

        returnLocations.put(player, player.getLocation());
        player.teleport(owner);

        return true;
    }

    public boolean leave(Player player) {
        if (!participants.contains(player))
            return false;

        teleportBack(player);

        participants.remove(player);
        message("&e" + player.getName() + " &7opuscil event.");
        owner.sendMessage(Colors.color("&e" + player.getName() + " &7opuscil Twoj event."));

        return true;
    }

    public void teleportBack() {
        participants.forEach(this::teleportBack);
    }
    public void teleportBack(Player player) {
        player.teleport(returnLocations.get(player));
        returnLocations.remove(player);
    }

    public void message(String message) {
        participants.stream()
                .filter( p -> p.getEntityId() != owner.getEntityId() )
                .forEach( p -> p.sendMessage(Colors.color(message)) );
    }

}
