package pl.trollcraft.creative.games;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A game representing model.
 * A base for any game type.
 */
public abstract class Attraction implements Playable {

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
    private String creator;

    /**
     * Players who take part in creator's
     * game.
     */
    private Set<Player> participants;

    /**
     * When the attraction
     * was created.
     */
    private long created;

    /**
     * When a player last played this
     * attraction.
     */
    private long lastPlayed;

    /**
     * Amount of players who tried to
     * play this attraction.
     * Not original players.
     */
    private int playedBy;

    /**
     * Amount of players who managed to
     * finish this attraction.
     */
    private int finishedBy;

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
    public Attraction(String type, String name, String creator) {
        this.type = type;
        this.name = name;
        this.creator = creator;
        participants = new HashSet<>();
        lastPlayed = 0;
        conserved = false;
    }

    /**
     * A list of game modes allowed
     * during the game.
     *
     * @return List of GameModes
     */
    public abstract List<GameMode> getGameModesAllowed();

    /**
     * GameMode the players are set to.
     *
     * @return GameMode
     */
    public abstract GameMode getGameMode();

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
        player.setGameMode(getGameMode());

        playedBy++;
        lastPlayed = System.currentTimeMillis();

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
        player.setGameMode(GameMode.CREATIVE);

        return Result.LEFT;
    }

    /**
     * @returns creator of the game.
     */
    public String getCreator() {
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
    @Override
    public String getName() {
        return name;
    }

    /**
     * @returns type of the game.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @returns boolean if attraction is conserved.
     */
    @Override
    public boolean isConserved() {
        return conserved;
    }

    @Override
    public long getCreationTime() {
        return created;
    }

    @Override
    public long getLastPlayed() {
        return lastPlayed;
    }

    @Override
    public int getPlayedBy() {
        return playedBy;
    }

    @Override
    public int getFinishedBy() {
        return finishedBy;
    }

    public void finished() {
        finishedBy++;
    }

    /**
     * Makes the attraction conserved. It
     * means any player cannot join.
     */
    public void setConserved(boolean conserved) {
        this.conserved = conserved;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public void setPlayedBy(int playedBy) {
        this.playedBy = playedBy;
    }

    public void setFinishedBy(int finishedBy) {
        this.finishedBy = finishedBy;
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
