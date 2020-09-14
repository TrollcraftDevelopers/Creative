package pl.trollcraft.creative.services.pets.mobs;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

public class CustomMobs {

    public static EntityTypes<EntityChicken> chickenType = EntityTypes.CHICKEN;

    public static void mobs() {
        chickenType = register(8, "chicken_pet", "chicken", ChickenPet::new, EnumCreatureType.CREATURE);
    }

    private static <T extends Entity> EntityTypes<T> register(int id, String name, String superTypeName, EntityTypes.b producer, EnumCreatureType type) {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a()
                .getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion()))
                .findChoiceType(DataConverterTypes.ENTITY)
                .types();

        String keyName = "minecraft:" + name;
        dataTypes.put(keyName, dataTypes.get("minecraft:" + superTypeName));

        EntityTypes.a<T> a = EntityTypes.a.a(producer, type);
        return IRegistry.a(IRegistry.ENTITY_TYPE, id, keyName, a.a(name));
    }

    public static @Nonnull Chicken spawnNewEntity(@Nonnull Location spawnLocation) {
        org.bukkit.World world = Objects.requireNonNull(spawnLocation.getWorld());
        WorldServer worldServer = ((CraftWorld) world).getHandle();

        ChickenPet pet = new ChickenPet(chickenType, worldServer);
        pet.getWorld().addEntity(pet);

        Chicken as = (Chicken) pet.getBukkitEntity();
        as.teleport(spawnLocation);

        return as;
    }

}
