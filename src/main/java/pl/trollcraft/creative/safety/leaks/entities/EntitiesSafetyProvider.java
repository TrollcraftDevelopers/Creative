package pl.trollcraft.creative.safety.leaks.entities;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import pl.trollcraft.creative.safety.leaks.SafetyLeak;
import pl.trollcraft.creative.safety.leaks.SafetyProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntitiesSafetyProvider implements SafetyProvider {

    private static final int ID = 1000;

    private List<World> worlds;
    private List<EntityType> entityTypesToRemove;

    public EntitiesSafetyProvider(List<World> worlds, List<EntityType> entityTypesToRemove) {
        this.worlds = worlds;
        this.entityTypesToRemove = entityTypesToRemove;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public SafetyLeak scan() {

        List<Entity> entities = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {

            for (Entity e : world.getEntities()) {

                if (entityTypesToRemove.contains(e.getType()))
                    entities.add(e);

            }

        }

        if (entities.size() > 0)
            return new EntitiesLeak(entities);

        return null;

    }

}
