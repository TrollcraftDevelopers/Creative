package pl.trollcraft.creative.essentials.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A teleport model.
 */
public abstract class Teleport {

    /**
     * A player who is going to be teleported.
     */
    private Player player;

    public Teleport(Player player) {
        this.player = player;
    }

    /**
     * Teleport operation.
     */
    public abstract boolean teleport();

    /**
     * Getters
     */
    public Player getPlayer() { return player; }

}
