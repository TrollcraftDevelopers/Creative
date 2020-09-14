package pl.trollcraft.creative.prefixes.obj;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.ArrayList;
import java.util.List;

public class PrefixesComponent implements UserComponent {

    public static final String COMP_NAME = "PrefixesComponent";

    private String current;
    private List<String> prefixes;

    public PrefixesComponent() {
        prefixes = new ArrayList<>();
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public boolean hasPrefix(String id) {
        return prefixes.contains(id);
    }

    public void addPrefix(String id) {
        prefixes.add(id);
    }

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path, Player player) {
        conf.set(path + ".current", current);
        conf.set(path + ".prefixes", prefixes);
    }

    @Override
    public void load(YamlConfiguration conf, String path, Player player) {
        current = conf.getString(path + ".current");
        prefixes = conf.getStringList(path + ".prefixes");
    }

    @Override
    public boolean isEmpty() {
        return prefixes.isEmpty();
    }
}
