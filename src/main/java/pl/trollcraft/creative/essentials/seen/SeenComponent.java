package pl.trollcraft.creative.essentials.seen;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.user.UserComponent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeenComponent implements UserComponent {

    public static final String COMP_NAME = "seencomponent";

    private long lastSeen;

    public String getLastSeen() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return format.format(new Date(lastSeen));
    }

    public void update() {
        lastSeen = System.currentTimeMillis();
    }

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        conf.set(path + ".lastSeen", lastSeen);
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        lastSeen = conf.getLong(path + ".lastSeen");
    }

    @Override
    public boolean isEmpty() {
        return lastSeen == 0;
    }
}
