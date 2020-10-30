package pl.trollcraft.creative.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MovementDetectEvent extends Event {

    private Player player;

    /**
     * The default constructor is defined
     * for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public MovementDetectEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    // -------- -------- -------- --------

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
