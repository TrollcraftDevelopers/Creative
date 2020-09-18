package pl.trollcraft.creative.services.pets.model;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.services.Service;
import pl.trollcraft.creative.services.pets.PetsComponent;

/**
 * A pet service.
 * Pet is a mob that follows player
 * and makes him even more cool.
 */
public class Pet implements Service {

    private static final String GROUP_NAME = "pets";

    private String id;
    private String name;
    private EntityType entityType;

    public Pet(String id, String name, EntityType entityType) {
        this.id = id;
        this.name = name;
        this.entityType = entityType;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public String getGroup() {
        return GROUP_NAME;
    }

    @Override
    public boolean isAllowedFor(Player player) {
        UsersController usersController = Creative.getPlugin().getUserController();

        User user = usersController.find(player.getName());
        assert user != null;

        PetsComponent comp = user.findComponent(PetsComponent.COMP_NAME);
        assert comp != null;

        return comp.hasPet(id);
    }

    @Override
    public void allow(Player player) {
        UsersController usersController = Creative.getPlugin().getUserController();

        User user = usersController.find(player.getName());
        assert user != null;

        PetsComponent comp = user.findComponent(PetsComponent.COMP_NAME);
        assert comp != null;

        comp.addPet(id);
    }

    @Override
    public void serve(Player player) {

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, Colors.color(name));
        npc.spawn(player.getLocation());

        npc.getNavigator().getLocalParameters().baseSpeed(2);
        npc.getNavigator().setTarget( (Entity) player, true);

        PetSession petSession = PetSession.newInstance(player, this, npc);
        Creative.getPlugin().getPetSessionsController().register(petSession);

    }

}
