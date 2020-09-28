package pl.trollcraft.creative.services.tails;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TailsComponent implements UserComponent {

    public static final String COMP_NAME = "TailsComponent";

    private String selectedTail;
    private List<String> tailsAvailable;

    public TailsComponent() {
        tailsAvailable = new ArrayList<>();
    }

    public List<String> getTailsAvailable() {
        return tailsAvailable;
    }

    public boolean hasTail(String tailName) {
        return tailsAvailable.contains(tailName);
    }

    public void addTail(String tailName) {
        tailsAvailable.add(tailName);
    }

    public String getSelectedTail() {
        return selectedTail;
    }

    public void setSelectedTail(String selectedTail) {
        this.selectedTail = selectedTail;
    }

    // -----------------------------------------------------

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        if (tailsAvailable.isEmpty()) return;

        conf.set(String.format("%s.selectedTail", path), selectedTail);
        conf.set(String.format("%s.tailsAvailable", path), tailsAvailable);
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        Bukkit.getLogger().log(Level.INFO, "Loading tails component...");

        if (conf.contains(String.format("%s.tailsAvailable", path)))
            tailsAvailable = conf.getStringList(String.format("%s.tailsAvailable", path));

        if (conf.contains(String.format("%s.selectedTail", path)))
            selectedTail = conf.getString(String.format("%s.selectedTail", path));

    }

    @Override
    public boolean isEmpty() {
        return tailsAvailable.isEmpty();
    }
}
