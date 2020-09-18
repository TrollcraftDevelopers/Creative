package pl.trollcraft.creative.services.pets;

import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.services.pets.model.Pet;

public class PetsController extends Controller <Pet, String>{

    @Override
    public Pet find(String id) {
        return instances.stream()
                .filter(pet -> pet.getId().equals(id))
                .findAny()
                .orElse(null);
    }

}
