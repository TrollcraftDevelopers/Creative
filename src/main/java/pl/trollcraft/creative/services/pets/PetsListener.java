package pl.trollcraft.creative.services.pets;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
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
    public void onNPCInteract (NPCRightClickEvent event) {

        Player player = event.getClicker();
        PetSessionsController sessionsController = Creative.getPlugin().getPetSessionsController();

        PetSession session = sessionsController.find(player);

        if (session == null) return;

        NPC npc = event.getNPC();

        if (session.getNpc().equals(event.getNPC())) {
            PetManageView view = new PetManageView(player, session);
            view.display();
        }

    }

}
