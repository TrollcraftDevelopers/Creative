package pl.trollcraft.creative.essentials.teleport;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AcceptedTeleport extends Teleport {

    private static ArrayList<AcceptedTeleport> demands = new ArrayList<>();

    private Player to;
    private long living;

    private AcceptedTeleport(Player player, Player to) {
        super(player);
        this.to = to;
        living = System.currentTimeMillis()
                + TimeUnit.MINUTES.toMillis(5);
        demands.add(this);
    }

    @Override
    public boolean teleport() {
        return getPlayer().teleport(to);
    }

    // -------- --------- -------- -------

    private static AcceptedTeleport find(Player player, Player to) {
        int playerId = player.getEntityId();
        int toId = to.getEntityId();

        for (AcceptedTeleport teleport : demands)
            if (teleport.to.getEntityId() == toId &&
                    teleport.getPlayer().getEntityId() == playerId)
                return teleport;

        return null;
    }

    public static boolean demand(Player player, Player to) {
        AcceptedTeleport teleport = find(player, to);

        if (teleport != null)
            return false;

        teleport = find(player, to);

        if (teleport != null)
            return false;

        teleport = new AcceptedTeleport(player, to);
        demands.add(teleport);

        return true;
    }

    public static boolean accept(Player player, Player to) {
        AcceptedTeleport teleport = find(player, to);
        if (teleport == null) return false;
        return teleport.teleport();
    }

    public static ArrayList<Player> getDemands(Player player) {
        ArrayList<Player> demands = new ArrayList<>();

        int toId = player.getEntityId();

        for (AcceptedTeleport teleport : AcceptedTeleport.demands)
            if (teleport.to.getEntityId() == toId)
                demands.add(teleport.to);

        return demands;
    }

}
