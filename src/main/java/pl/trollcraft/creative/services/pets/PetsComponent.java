package pl.trollcraft.creative.services.pets;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.HashMap;

public class PetsComponent implements UserComponent {

    public static final String COMP_NAME = "PetsComponent";

    /**
     * Contains pet ID and name.
     */
    private HashMap<String, String> pets;

    public PetsComponent() {
        pets = new HashMap<>();
    }

    public boolean hasPet(String id) {
        return pets.containsKey(id);
    }

    public void addPet(String id) {
        pets.put(id, "");
    }

    public HashMap<String, String> getPets() {
        return pets;
    }

    public String getPetName(String petId) {
        return pets.getOrDefault(petId, null);
    }

    public void setPetName(String petId, String petName) {
        if (pets.containsKey(petId))
            pets.replace(petId, petName);
        else throw new IllegalStateException("Player hasn't pet " + petName);
    }

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        pets.forEach( (petId, petName) ->
            conf.set(path + ".pets." + petId + ".name", petName)
        );
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        conf.getConfigurationSection(path + ".pets")
                .getKeys(false)
                .forEach( petId -> {
                    String name = conf.getString(path + ".pets." + petId + ".name");
                    pets.put(petId, name);
                } );
    }

    @Override
    public boolean isEmpty() {
        return pets.isEmpty();
    }
}
