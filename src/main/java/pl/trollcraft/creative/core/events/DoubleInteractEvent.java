package pl.trollcraft.creative.core.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class DoubleInteractEvent extends Event {

    private Player player;
    private Action action;
    private Block block;

    public DoubleInteractEvent (Player player, Action action, Block block) {
        this.player = player;
        this.action = action;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public Block getBlock() {
        return block;
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
