package pl.trollcraft.creative.safety.worldedit.event;

import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditCommandEvent extends Event implements Cancellable {

    private WorldEditCommand command;
    private String[] args;

    private Region region;
    private Player player;

    public WorldEditCommandEvent(WorldEditCommand command, Player player, String[] args, Region region) {
        this.command = command;
        this.args = args;
        this.region = region;
        this.player = player;
    }

    public WorldEditCommand getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public Region getRegion() {
        return region;
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

    // -------- -------- -------- --------

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
