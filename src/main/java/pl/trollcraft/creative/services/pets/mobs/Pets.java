package pl.trollcraft.creative.services.pets.mobs;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import pl.trollcraft.creative.core.help.ReflectionHelp;

import java.util.Map;

public enum Pets {

    CHICKEN ("Chicken", 54, ChickenPet.class);

    Pets (String name, int id, Class<? extends Entity> clazz) {
        addToMap(clazz, name, id);
    }

    public static void spawn(Entity entity, Location loc)
    {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
    }

    private static void addToMap(Class clazz, String name, int id) {
        ((Map) ReflectionHelp.getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
        ((Map)ReflectionHelp.getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
        ((Map) ReflectionHelp.getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
    }

}
