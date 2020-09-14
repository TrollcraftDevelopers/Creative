package pl.trollcraft.creative.safety.leaks.entities;

import org.bukkit.entity.Entity;
import pl.trollcraft.creative.safety.leaks.SafetyLeak;

import java.util.List;

public class EntitiesLeak implements SafetyLeak {

    private List<Entity> entities;

    public EntitiesLeak(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public boolean solve() {
        entities.forEach(e -> e.remove());
        return true;
    }

    @Override
    public String message() {
        return String.format("Wykryto %d smiecio-stworzen.", entities.size());
    }
}
