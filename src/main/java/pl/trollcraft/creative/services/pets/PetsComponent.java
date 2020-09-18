package pl.trollcraft.creative.services.pets;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.ArrayList;
import java.util.List;

public class PetsComponent implements UserComponent {

    public static final String COMP_NAME = "PetsComponent";

    private List<String> pets;

    public PetsComponent() {
        pets = new ArrayList<>();
    }

    public boolean hasPet(String name) {
        return pets.contains(name);
    }

    public void addPet(String name) {
        pets.add(name);
    }

    public List<String> getPets() {
        return pets;
    }

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path, Player player) {
        conf.set(path + ".pets", pets);
    }

    @Override
    public void load(YamlConfiguration conf, String path, Player player) {
        pets = conf.getStringList(path + ".pets");
    }

    @Override
    public boolean isEmpty() {
        return pets.isEmpty();
    }
}
