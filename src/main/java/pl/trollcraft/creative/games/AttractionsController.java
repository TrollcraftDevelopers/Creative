package pl.trollcraft.creative.games;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.controller.Controller;

import java.util.List;
import java.util.stream.Collectors;

public class AttractionsController extends Controller<Attraction, String> {

    /**
     * Finds an attraction by name.
     *
     * @param name
     * @return attraction
     */
    @Override
    public Attraction find(String name) {
        return instances.stream()
                .filter( attraction -> attraction.equals(name) )
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
    public Attraction find(String name, String type) {
        return instances.stream()
                .filter( attraction -> attraction.equals(name) )
                .filter( attraction -> attraction.getType().equals(type))
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
    public Attraction find(Player player, String type) {
        return instances.stream()
                .filter( attraction -> attraction.getParticipants().contains(player) )
                .filter( attraction -> attraction.getType().equals(type) )
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
    public Attraction find(Player player) {
        return instances.stream()
                .filter( attraction -> attraction.getParticipants().contains(player) )
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
    public List<Attraction> findAll(Player player, String type) {
        return instances.stream()
                .filter( attraction -> attraction.equals(player) )
                .filter( attraction -> attraction.getType().equals(type) )
                .collect(Collectors.toList());
    }

    /**
     * Finds all attractions created
     * by the player.
     *
     * @param player
     * @return
     */
    public List<Attraction> findAll(Player player) {
        return instances.stream()
                .filter( attraction -> attraction.equals(player) )
                .collect(Collectors.toList());
    }

    /**
     * Finds an attraction of given type
     * of player being currently conserved.
     *
     * @param player
     * @return attraction
     */
    public Attraction findConserved(Player player, String type) {
        return instances.stream()
                .filter( attraction -> attraction.equals(player) )
                .filter( attraction -> attraction.isConserved())
                .filter( attraction -> attraction.getType().equals(type) )
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
    public Attraction findConserved(Player player) {
        return instances.stream()
                .filter( attraction -> attraction.equals(player) )
                .filter( attraction -> attraction.isConserved())
                .findFirst()
                .orElse(null);
    }

}
