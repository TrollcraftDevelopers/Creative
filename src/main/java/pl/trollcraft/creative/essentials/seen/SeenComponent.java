package pl.trollcraft.creative.essentials.seen;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.Calendar;
import java.util.Date;

public class SeenComponent implements UserComponent {

    public static final String COMP_NAME = "seencomponent";

    private long lastSeen;

    public String getLastSeen() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(lastSeen));
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        return dd + "/" + mm + "/" + yyyy + " " + h + ":" + m;
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
