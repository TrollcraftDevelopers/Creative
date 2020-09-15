package pl.trollcraft.creative.games.parkour;

import com.google.common.collect.Lists;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.blocksdetector.BlockDetector;
import pl.trollcraft.creative.core.help.blocksdetector.DetectionRequest;
import pl.trollcraft.creative.games.Attraction;
import pl.trollcraft.creative.games.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parkour extends Attraction {

    public static final String TYPENAME = "PARKOUR";

    /**
     * A start location for parkour
     * map. There the players
     * will be moved after game start.
     */
    private Location startLocation;

    /**
     * A list of blocks which,
     * when interacted with,
     * define a new player checkpoint.
     */
    private Material checkpointBlock;

    /**
     * Checkpoints of players.
     * Where should they be
     * moved after the fall.
     */
    private Map<Player, Location> playersCheckpoints;

    /**
     * A block when touched moves
     * to the nearest checkpoint.
     */
    private Material fallBlock;

    /**
     * A block when touched moves
     * ends the parkour.
     */
    private Material endBlock;

    public Parkour(String name, String creator) {
        super(TYPENAME, name, creator);
        playersCheckpoints = new HashMap<>();
    }

    /**
     * A list of game modes allowed
     * during the game.
     *
     * @return List of GameModes
     */
    @Override
    public List<GameMode> getGameModesAllowed() {
        return Arrays.asList(new GameMode[] { GameMode.ADVENTURE });
    }

    /**
     * GameMode the players are set to.
     *
     * @return GameMode
     */
    @Override
    public GameMode getGameMode() {
        return GameMode.ADVENTURE;
    }

    /**
     * Tests the parkour if it has
     * all necessary elements set up.
     *
     * @return result
     */
    @Override
    public Result test() {
        if (startLocation == null)
            return Result.NO_START_POINT;
        else if (fallBlock == null || fallBlock == Material.AIR)
            return Result.NO_FALLBLOCK;
        else if (endBlock == null || endBlock == Material.AIR)
            return Result.NO_ENDBLOCK;
        return Result.OK;
    }

    /**
     * Sets the parkour up for the player.
     *
     * @param player
     */
    @Override
    public void setup(Player player) {

        // defining rules for parkour.

        BlockDetector detector = Creative.getPlugin().getBlockDetector();

        // Setting fall behaviour - what should happen on fall?
        DetectionRequest fallRequest = new DetectionRequest(TYPENAME, player, fallBlock, req -> {
            Location moveTo = getCheckpoint(player);
            player.teleport(moveTo);
            player.sendMessage(Colors.color("&cO nie! Spadles!"));
        }, false);

        // Setting checkpoint enter behaviour.
        DetectionRequest checkpointRequest = new DetectionRequest(TYPENAME, player, checkpointBlock, req -> {
            Location location = player.getLocation();

            if (playersCheckpoints.containsKey(player))
                playersCheckpoints.replace(player, location);
            else
                playersCheckpoints.put(player, location);

            player.sendMessage(Colors.color("&aUstawiono nowy &echeckpoint."));
        }, true);

        // Setting end block, which ends the game for player.
        DetectionRequest endRequest = new DetectionRequest(TYPENAME, player, endBlock, req -> {
            leave(player);

            player.sendMessage(Colors.color("&a&lGRATULACJE!\n&aUkonczyles parkour &e" + getName() + "!"));
        }, false);

        // Registering all monitoring requests.

        detector.define(fallRequest);
        detector.define(checkpointRequest);
        detector.define(endRequest);

        player.teleport(startLocation);
        player.sendMessage(Colors.color("&aWitaj na parkourze &e" + getName() + "!\n&a&lPowodzenia!"));

    }

    /**
     * Ends the game for the player.
     * Removes player monitors.
     *
     * @param player
     */
    @Override
    public void end(Player player) {
        BlockDetector detector = Creative.getPlugin().getBlockDetector();
        detector.undefine(player, TYPENAME);
        playersCheckpoints.remove(player);
    }

    /**
     * Sets a new checkpoint block.
     *
     * @param checkpointBlock
     */
    public void setCheckpointBlock(Material checkpointBlock) {
        this.checkpointBlock = checkpointBlock;
    }

    /**
     * Gets a latest checkpoint of
     * the player.
     *
     * @param player
     * @return checkpoint
     */
    public Location getCheckpoint(Player player) {
        if (!playersCheckpoints.containsKey(player))
            return startLocation;
        return playersCheckpoints.get(player);
    }

    /**
     * Sets the start location.
     *
     * @param startLocation
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Sets a block which interacted
     * moves the player to the last checkpoint.
     *
     * @param fallBlock
     */
    public void setFallBlock(Material fallBlock) {
        this.fallBlock = fallBlock;
    }

    /**
     * Sets a block which interacted
     * with ends the game for the player.
     *
     * @param endBlock
     */
    public void setEndBlock(Material endBlock) {
        this.endBlock = endBlock;
    }

    /**
     * Gets the start location.
     *
     * @return startLocation - start location of the parkour.
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * Gets the checkpoint block.
     *
     * @return checkpointBlock - checkpoint block.
     */
    public Material getCheckpointBlock() {
        return checkpointBlock;
    }

    /**
     * Gets the fall block.
     *
     * @return fallBlock - fall block.
     */
    public Material getFallBlock() {
        return fallBlock;
    }

    /**
     * Gets the end block.
     *
     * @return endBlock - end block.
     */
    public Material getEndBlock() {
        return endBlock;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof String) {
            String name = (String) obj;
            return getName().equals(name);
        }
        return super.equals(obj);

    }
}
