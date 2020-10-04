package pl.trollcraft.creative.services.pets;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Controllable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.services.pets.model.PetSession;
import pl.trollcraft.creative.services.pets.view.PetManageView;

public class PetsListener implements Listener {

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        PetSession petSession = Creative.getPlugin().getPetSessionsController().find(player);

        if (petSession != null) {
            petSession.getNpc().despawn();
            petSession.getNpc().destroy();
            Creative.getPlugin().getPetSessionsController().unregister(petSession);
        }

    }

    @EventHandler
    public void onTeleport (PlayerTeleportEvent event) {

        Player player = event.getPlayer();
        PetSession petSession = Creative.getPlugin()
                .getPetSessionsController()
                .find(player);

        if (petSession == null)
            return;

        NPC npc = petSession.getNpc();
        npc.teleport(player.getLocation(), event.getCause());

    }

    @EventHandler
    public void onNPCLeftClick (NPCLeftClickEvent event) {
        event.setCancelled(true);

        Player player = event.getClicker();
        PetSessionsController sessionsController = Creative.getPlugin().getPetSessionsController();

        PetSession session = sessionsController.find(player);

        if (session == null) return;
        if (session.getNpc().equals(event.getNPC())) {
            PetManageView view = new PetManageView(player, session);
            view.display();
        }

    }

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onNPCDismount (EntityDismountEvent event) {

        Entity entity = event.getEntity();
        if (entity.getType() != EntityType.PLAYER)
            return;

        Player player = (Player) entity;
        PetSession petSession = Creative.getPlugin()
                .getPetSessionsController()
                .find(player);

        if (petSession == null) return;

        int id = event.getDismounted().getEntityId();
        NPC npc = petSession.getNpc();

        if (npc.getEntity().getEntityId() == id) {
            npc.getTrait(Controllable.class).setEnabled(false);

            if (petSession.canMove()) {
                npc.getNavigator().getLocalParameters().baseSpeed(2);
                npc.getNavigator().setTarget((Entity) player, true);
            }

        }


    }

}
