package pl.trollcraft.creative.services.pets.view;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackBuilder;
import pl.trollcraft.creative.services.pets.model.PetSession;

public class PetManageView {

    private Player player;
    private PetSession petSession;
    private GUI gui;

    public PetManageView(Player player, PetSession petSession) {
        this.player = player;
        this.petSession = petSession;
        gui = new GUI(9, Colors.color("&a&lPet'y"));
    }

    private void prepare() {

        if (petSession == null) return;
        NPC npc = petSession.getNpc();

        // Movement
        if (petSession.canMove()) {

            ItemStack notMove = ItemStackBuilder.init(Material.STONE)
                    .name(Colors.color("&e&lRozkaz stac w miejscu"))
                    .build();

            gui.addItem(0, notMove, e -> {

                npc.getNavigator().getLocalParameters().baseSpeed(1);
                npc.getNavigator().setTarget(null);
                petSession.setMove(false);
                display();

            });
        }

        else {

            ItemStack move = ItemStackBuilder.init(Material.LEAD)
                    .name(Colors.color("&a&lRozkaz podazac"))
                    .build();

            gui.addItem(0, move, e -> {

                npc.getNavigator().getLocalParameters().baseSpeed(2);
                npc.getNavigator().setTarget((Entity) player, true);
                petSession.setMove(true);

                display();

            });
        }

        // Closing

        ItemStack close = ItemStackBuilder.init(Material.BARRIER)
                .name(Colors.color("&c&lUsun"))
                .build();

        gui.addItem(8, close, e -> {

            petSession.getNpc().despawn();
            petSession.getNpc().destroy();
            Creative.getPlugin().getPetSessionsController().unregister(petSession);

            player.closeInventory();

        });

    }

    public void display() {
        prepare();
        Creative.getPlugin().getGuiManager().open(player, gui);
    }

}
