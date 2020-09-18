package pl.trollcraft.creative.core.help.demands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Iterator;

public class DemandsController extends Controller<Demand, Integer> {

    /**
     * Millis the demand should be alive.
     */
    private static final long DEMAND_TTL = 3*60*1000;

    /**
     * Created instance.
     * Also launches monitor.
     */
    public DemandsController() {
        monitor();
    }

    /**
     *
     * @param id numerical id of the demand
     * @return Demand
     */
    @Override
    public Demand find(Integer id) {
        return instances.stream()
                .filter( dem -> dem.getId() == id.intValue() )
                .findAny()
                .orElse(null);
    }

    /**
     * Gets the demand by the demander.
     *
     * @param tag grouping tag of demand.
     * @param who player who sent the demand.
     * @return list of demands.
     */
    public Demand find(String tag, String who) {
        return instances.stream()
                .filter(dem -> dem.getTag().equals(tag))
                .filter(dem -> dem.getWho().equals(who))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the demand by the target of
     * the demand.
     *
     * @param tag grouping tag of demand.
     * @param target player who is target of the demand.
     * @return list of demands.
     */
    public Demand findFirst(String tag, String target) {
        return instances.stream()
                .filter(dem -> dem.getTag().equals(tag))
                .filter(dem -> dem.getTo().equals(target))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the demand based on all the data.
     *
     * @param tag grouping tag of demand,
     * @param who who is demanding,
     * @param to to whom the demand is targeted.
     * @return Demand
     */
    public Demand find(String tag, String who, String to) {
        return instances.stream()
                .filter( dem -> dem.getTag().equals(tag) )
                .filter( dem -> dem.getWho().equals(who) )
                .filter( dem -> dem.getTo().equals(to) )
                .findAny()
                .orElse(null);
    }

    /**
     * Creates a new demand.
     *
     * @param tag grouping tag of the demand.
     * @param who player who is demanding.
     * @param to player to whom the demand is directed.
     * @param handler action handler of responses.
     */
    public void create(String tag, String who, String to, DemandHandler handler) {
        Demand demand = new Demand(tag, who, to, handler);
        register(demand);
    }

    /**
     * The demand has been accepted.
     *
     * @param demand accepted demand
     */
    public void accept(Demand demand) {
        unregister(demand);
        demand.getAction().accepted();
    }

    /**
     * The demand has been refused.
     *
     * @param demand refused demand
     */
    public void refuse(Demand demand) {
        unregister(demand);
        demand.getAction().refused();
    }

    /**
     * Monitors all demands. If one is too old
     * it is removed.
     */
    private void monitor() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<Demand> it = instances.iterator();
                Demand dem;
                Player player;

                while (it.hasNext()) {

                    dem = it.next();
                    if (System.currentTimeMillis() >= dem.getCreationTime() + DEMAND_TTL) {

                        player = Bukkit.getPlayer(dem.getWho());
                        if (player != null && player.isOnline())
                            player.sendMessage(Colors.color("&7Prosba teleportacji do &e" + dem.getTo() + " &7przedawnila sie."));

                        it.remove();

                    }


                }

            }

        }.runTaskTimer(Creative.getPlugin(), 20, 20);

    }

}
