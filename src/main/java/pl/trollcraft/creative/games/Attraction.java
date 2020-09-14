package pl.trollcraft.creative.games;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * A game representing model.
 * A base for any game type.
 */
public abstract class Attraction {

    /**
     * A type of the game
     */
    private String type;

    /**
     * A name of the game
     */
    private String name;

    /**
     * Creator of the game.
     * Also owner.
     */
    private Player creator;

    /**
     * Players who take part in creator's
     * game.
     */
    private Set<Player> participants;

    /**
     * When a player last used this
     * attraction.
     */
    private long lastUsed;

    /**
     * Is the attraction currently
     * unavailable due to conservation?
     */
    private boolean conserved;

    /**
     * Creates a game instance of
     * define type.
     *
     * @param type
     */
    public Attraction(String type, String name, Player creator) {
        this.type = type;
        this.name = name;
        this.creator = creator;
        participants = new HashSet<>();
        conserved = false;
    }

    /**
     * Tests if attraction
     * can be attended.
     *
     * @return result
     */
    public abstract Result test();

    /**
     * Sets the parkour for
     * player. Moves him, registers all
     * necessary components.
     */
    public abstract void setup(Player player);

    /**
     * Joins a player to the game.
     *
     * @param player
     * @return result of join request.
     */
    public Result join(Player player) {

        if (!participants.contains(player))
            participants.add(player);
        else return Result.ALREADY_JOINED;

        setup(player);
        return Result.JOINED;
    }

    /**
     * Ends the game for the player.
     * Restores data, removes listeners.
     *
     * @param player
     */
    public abstract void end(Player player);

    /**
     * Leaves a player from the game.
     *
     * @param player
     * @return result of leave request.
     */
    public Result leave(Player player) {
        if (!participants.contains(player))
            return Result.NOT_IN_GAME;

        participants.remove(player);
        end(player);
        return Result.LEFT;
    }

    /**
     * @returns creator of the game.
     */
    public Player getCreator() {
        return creator;
    }

    /**
     * @returns participants set.
     */
    public Set<Player> getParticipants() {
        return participants;
    }

    /**
     * @return name of the parkour.
     */
    public String getName() {
        return name;
    }

    /**
     * @returns type of the game.
     */
    public String getType() {
        return type;
    }

    /**
     * @returns boolean if attraction is conserved.
     */
    public boolean isConserved() {
        return conserved;
    }

    /**
     * Makes the attraction conserved. It
     * means any player cannot join.
     */
    public void setConserved(boolean conserved) {
        this.conserved = conserved;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Player) {
            Player player = (Player) obj;
            return creator.equals(player);
        }

        else if (obj instanceof String) {
            String name = (String) obj;
            return this.name.equals(name);
        }

        return super.equals(obj);

    }
}
