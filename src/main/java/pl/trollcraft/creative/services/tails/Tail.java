package pl.trollcraft.creative.services.tails;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.betterparticles.Particles;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.services.Service;
import pl.trollcraft.creative.services.ServiceManager;

import java.util.*;

public abstract class Tail implements Service {

    private static final String GROUP_NAME = "tails";

    private static ArrayList<Tail> tails = new ArrayList<>();
    private static List<Session> activeTails = new LinkedList<>();

    private static class Session {
        public Player player;
        public Tail tail;
    }

    private String id;
    private String name;
    protected Particles particles;

    public Tail (String id, String name, Particles particles) {
        this.id = id;
        this.name = name;
        this.particles = particles;
        tails.add(this);
        ServiceManager.register(this);
    }

    protected abstract Location prepare(Player player);

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGroup() {
        return GROUP_NAME;
    }

    @Override
    public boolean isAllowedFor(Player player) {

        UsersController usersController = Creative.getPlugin().getUserController();

        if (usersController == null) {
            player.sendMessage("NULL LOL");
            return false;
        }

        User user = usersController.find(player.getName());

        if (user == null) {
            player.sendMessage(Colors.color("&cBlad, przeloguj sie."));
            return false;
        }

        TailsComponent tailsComponent = (TailsComponent) user.getComponent(TailsComponent.COMP_NAME);
        return tailsComponent.hasTail(id);
    }

    @Override
    public void allow(Player player) {

        UsersController usersController = Creative.getPlugin().getUserController();

        String name = player.getName();
        User user = usersController.find(name);

        if (user == null)
            player.sendMessage(Colors.color("&cBlad, przeloguj sie."));

        else {
            TailsComponent tailsComponent = (TailsComponent) user.getComponent(TailsComponent.COMP_NAME);
            tailsComponent.addTail(id);
            player.sendMessage(Colors.color("&aZakupiono ogon &e" + this.name + "!"));

        }

    }

    @Override
    public void serve(Player player) {
        Location loc = prepare(player);
        particles.spawn(loc);
    }

    public static ArrayList<Tail> getTails() {
        return tails;
    }

    public static Tail find(String id) {
        for (Tail tail : tails)
            if (tail.id.equals(id))
                return tail;
        return null;
    }

    public static void runTail(Player player, Tail tail) {
        disableTail(player);

        Session session = new Session();
        session.player = player;
        session.tail = tail;
        activeTails.add(session);
    }

    public static void disableTail(Player player) {
        int id = player.getEntityId();
        Iterator<Session> sessions = activeTails.iterator();

        Session session;
        while (sessions.hasNext()) {

            session = sessions.next();

            if (session.player.getEntityId() == id)
                sessions.remove();
        }
    }

    public static void enable() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<Session> it = activeTails.iterator();

                Session session;
                while (it.hasNext()) {
                    session = it.next();
                    session.tail.serve(session.player);
                }

            }

        }.runTaskTimer(Creative.getPlugin(), 20, 1);

    }

}
