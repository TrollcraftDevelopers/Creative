package pl.trollcraft.creative.core.help.blockades;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * An util to create a simple list
 * of blocked users to certain
 * actions.
 */
public class Blockade {

    /**
     * Name of the blockade
     */
    private String name;

    /**
     * List of players with
     * this blockade.
     */
    private List<String> players;

    /**
     * Creates a blockade.
     *
     * @param name name of the blockade
     */
    public Blockade(String name) {
        this.name = name;
        players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Activates the blockade for
     * the player.
     *
     * @param player player to activate blockade to.
     */
    public void block(Player player) {
        String name = player.getName();
        if (!players.contains(name))
            players.add(name);
    }

    /**
     * Deactivates the blockade for
     * the player.
     *
     * @param player player to deactivate blockade to.
     */
    public void unblock(Player player) {
        String name = player.getName();
        if (players.contains(name))
            players.remove(name);
    }

    /**
     * Determines if the players is blocked.
     *
     * @param player player to determine the blockade for.
     * @return blockade status.
     */
    public boolean isBlocked(Player player) {
        String name = player.getName();
        return players.contains(name);
    }

}
