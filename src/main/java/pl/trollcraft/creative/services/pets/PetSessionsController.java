package pl.trollcraft.creative.services.pets;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.services.pets.model.PetSession;

public class PetSessionsController extends Controller<PetSession, Player> {

    @Override
    public PetSession find(Player id) {
        return instances.stream()
                .filter(ses -> ses.getPlayer().equals(id))
                .findAny()
                .orElse(null);
    }

}
