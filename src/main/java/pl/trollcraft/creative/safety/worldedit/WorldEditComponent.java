package pl.trollcraft.creative.safety.worldedit;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.ArrayList;

public class WorldEditComponent implements UserComponent {

    private class Cooldown {

        private WorldEditCommand command;
        private long until;

        public Cooldown(WorldEditCommand command, long until) {
            this.command = command;
            this.until = until;
        }

        public WorldEditCommand getCommand() {
            return command;
        }

        public boolean hasExpired() {
            return System.currentTimeMillis() >= until;
        }

    }

    public static final String COMP_NAME = "worldeditcomponent";

    private ArrayList<Cooldown> cooldowns;
    private long joinTime;

    public WorldEditComponent() {
        cooldowns = new ArrayList<>();
        joinTime = System.currentTimeMillis();
    }

    // --------

    private Cooldown getCooldown(WorldEditCommand command) {
        for (Cooldown cooldown : cooldowns)
            if (cooldown.command.getName().equals(command.getName()))
                return cooldown;
        return null;
    }

    public void cooldown(WorldEditCommand command, Player player) {
        Cooldown cooldown = getCooldown(command);
        if (cooldown != null)
            cooldowns.remove(cooldown);

        int c = command.getCooldown().resolve(player);
        long until = System.currentTimeMillis() + c * 1000;

        cooldown = new Cooldown(command, until);
        cooldowns.add(cooldown);
    }

    public long isOnCooldown(WorldEditCommand command) {
        Cooldown cooldown = getCooldown(command);
        if (cooldown == null) return 0;
        return cooldown.until - System.currentTimeMillis();
    }

    public boolean hasPlayedFor(long seconds) {
        long millis = seconds * 1000;
        long playTime = System.currentTimeMillis() - joinTime;
        return playTime >= millis;
    }

    public String getTimeToPlayFor(long seconds) {
        long timeToBe = seconds * 1000;
        long onlineTime = System.currentTimeMillis() - joinTime;
        return Help.parseTime(timeToBe - onlineTime);
    }

    // --------

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        conf.set(path, null);
        for (Cooldown cooldown : cooldowns)
            conf.set(String.format("%s.%s.until", path, cooldown.command.getName()), cooldown.until);
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        conf.getConfigurationSection(path).getKeys(false).forEach( command -> {

            long until = conf.getLong(String.format("%s.%s.until", path, command));
            if (System.currentTimeMillis() >= until)
                return;

            Cooldown cooldown = new Cooldown(Creative.getPlugin().getWorldEditCommandsController().find(command), until);
            cooldowns.add(cooldown);

        } );

    }

    @Override
    public boolean isEmpty() {
        return cooldowns.isEmpty();
    }
}
