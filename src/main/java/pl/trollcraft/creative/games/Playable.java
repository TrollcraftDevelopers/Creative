package pl.trollcraft.creative.games;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface Playable {

    String getName();
    String getType();

    String getCreator();

    Set<Player> getParticipants();

    Result join(Player player);
    Result leave(Player player);

    long getCreationTime();
    long getLastPlayed();

    int getPlayedBy();
    int getFinishedBy();

    boolean isConserved();
    void setConserved(boolean conserved);

    void delete();

    List<GameMode> getGameModesAllowed();

}
