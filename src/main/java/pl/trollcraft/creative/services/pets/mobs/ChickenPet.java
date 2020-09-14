package pl.trollcraft.creative.services.pets.mobs;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;

public class ChickenPet extends EntityChicken {

    public ChickenPet (EntityTypes<? extends EntityChicken> entitytypes, World world) {
        super (entitytypes, world);

        EntityPlayer entityPlayer = ((CraftPlayer)Bukkit.getPlayer("SkepsonSk")).getHandle();
        goalSelector.a(new PathfinderGoalFollowPlayer(this, entityPlayer, 2, 2));
    }

}
