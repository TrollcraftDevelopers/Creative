package pl.trollcraft.creative.services.pets.model;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

/**
 * Represents a player
 * using his pet.
 */
public final class PetSession {

    private Player player;
    private Pet pet;
    private NPC npc;

    private boolean move;

    private PetSession(Player player, Pet pet, NPC npc) {
        this.player = player;
        this.pet = pet;
        this.npc = npc;
        move = true;
    }

    public static PetSession newInstance(Player player, Pet pet, NPC npc) {
        return new PetSession(player, pet, npc);
    }

    public Player getPlayer() {
        return player;
    }

    public Pet getPet() {
        return pet;
    }

    public NPC getNpc() {
        return npc;
    }

    public boolean canMove() {
        return move;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
}
