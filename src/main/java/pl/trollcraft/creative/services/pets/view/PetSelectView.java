package pl.trollcraft.creative.services.pets.view;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackBuilder;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.services.pets.PetsComponent;
import pl.trollcraft.creative.services.pets.model.Pet;
import pl.trollcraft.creative.services.pets.model.PetSession;

import java.util.Objects;

public class PetSelectView {

    private Player player;
    private GUI gui;

    public PetSelectView(Player player) {
        this.player = player;
        gui = new GUI(27, Colors.color("&a&lPet'y"));
    }

    private void prepare() {

        String name = player.getName();
        User user = Creative.getPlugin().getUserController().find(name);

        assert user != null;

        PetsComponent petsComponent = user.findComponent(PetsComponent.COMP_NAME);

        int slot = 0;
        for (String petName : petsComponent.getPets()) {

            Pet pet = Creative.getPlugin()
                    .getPetsController()
                    .find(petName);

            ItemStack itemStack = ItemStackBuilder.init(getMaterial(pet.getEntityType()))
                    .name(Colors.color(pet.getName()))
                    .build();

            gui.addItem(slot, itemStack, e -> {

                e.setCancelled(true);

                PetSession petSession = Creative.getPlugin()
                        .getPetSessionsController()
                        .find(player);

                if (petSession != null) {
                    if (petSession.getNpc().getEntity().getType() == pet.getEntityType()) {
                        player.sendMessage(Colors.color("&cTen pet jest juz przyzwany."));
                        return;
                    }
                    else {
                        petSession.getNpc().despawn();
                        petSession.getNpc().destroy();
                        Creative.getPlugin().getPetSessionsController().unregister(petSession);
                    }
                }

                pet.serve(player);
                player.sendMessage(Colors.color("&aPet zostal przywolany!"));

                gui.setTransacting(true);
                display();

            });

        }

    }

    public void display() {
        prepare();
        Creative.getPlugin().getGuiManager().open(player, gui);
    }

    // ----

    private Material getMaterial(EntityType type) {
        return Objects.requireNonNull(Material.getMaterial(type.name() + "_SPAWN_EGG"));
    }

}
