package pl.trollcraft.creative.games;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.List;
import java.util.stream.Collectors;

public class PlayablesController extends Controller<Playable, String> {

    /**
     * Finds an attraction by name.
     *
     * @param name
     * @return attraction
     */
    @Override
    public Playable find(String name) {
        return instances.stream()
                .filter( p -> p.getName().equals(name) )
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an attraction of given type
     * by name.
     *
     * @param name
     * @return attraction
     */
    public Playable find(String name, String type) {
        return instances.stream()
                .filter( p -> p.getName().equals(name) )
                .filter( p -> p.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an attraction played by
     * the player at the moment.
     *
     * @param player
     * @return attraction
     */
    public Playable find(Player player, String type) {
        return instances.stream()
                .filter( p -> p.getParticipants().contains(player) )
                .filter( p -> p.getType().equals(type) )
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an attraction of any type played by
     * the player at the moment.
     *
     * @param player
     * @return attraction
     */
    public Playable find(Player player) {
        return instances.stream()
                .filter( p -> p.getParticipants().contains(player) )
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds all attractions of given
     * type created by the player.
     *
     * @param player
     * @return
     */
    public List<Playable> findAll(Player player, String type) {
        return instances.stream()
                .filter( p -> p.getCreator().equals(player.getName()) )
                .filter( p -> p.getType().equals(type) )
                .collect(Collectors.toList());
    }

    /**
     * Finds all attractions created
     * by the player.
     *
     * @param player a name of creator.
     * @return list of games
     */
    public List<Playable> findAll(String player) {
        return instances.stream()
                .filter( p -> p.getCreator().equals(player) )
                .collect(Collectors.toList());
    }

    /**
     * Finds an attraction of given type
     * of player being currently conserved.
     *
     * @param player
     * @return attraction
     */
    public Playable findConserved(Player player, String type) {
        return instances.stream()
                .filter( p -> p.getCreator().equals(player.getName()) )
                .filter(Playable::isConserved)
                .filter( p -> p.getType().equals(type) )
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an attraction of any type
     * of player being currently conserved.
     *
     * @param player
     * @return attraction
     */
    public Playable findConserved(Player player) {
        return instances.stream()
                .filter( p -> p.getCreator().equals(player.getName()) )
                .filter(Playable::isConserved)
                .findFirst()
                .orElse(null);
    }

}
