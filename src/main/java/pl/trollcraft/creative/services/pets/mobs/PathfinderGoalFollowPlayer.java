package pl.trollcraft.creative.services.pets.mobs;

import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PathfinderGoal;

public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private EntityInsentient entity;
    private EntityPlayer owner;
    private double speed;
    private float distanceSquared;

    public PathfinderGoalFollowPlayer(EntityInsentient entity, EntityPlayer owner, double speed, float distance) {
        this.entity = entity;
        this.owner = owner;
        this.speed = speed;
        this.distanceSquared = distance * distance;
        //this.a(3);
    }

    @Override
    public boolean a() {
        return (owner != null && owner.isAlive() && this.entity.h(owner) > (double)distanceSquared);
    }

    @Override
    public void d() {
        this.entity.getNavigation().n();
    }

    @Override
    public void e() {
        this.entity.getNavigation().a(owner, this.speed);
    }

}
