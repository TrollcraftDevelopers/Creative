package pl.trollcraft.creative.services.vehicles;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.gui.GUIManager;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;

import java.util.List;

public class VehiclesCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        UsersController usersController = Creative.getPlugin().getUserController();
        User user = usersController.find(player.getName());
        VehiclesComponent vehiclesComponent = (VehiclesComponent) user.getComponent(VehiclesComponent.COMP_NAME);

        openMenu(player, vehiclesComponent.getVehiclesAvailable());
    }

    private void openMenu(Player player, List<String> vehicles) {

        GUIManager guiManager = Creative.getPlugin().getGuiManager();
        GUI gui = guiManager.find(player);

        if (gui == null)
            gui = new GUI(54, Colors.color("&2&lPOJAZDY"));
        else
            gui.clear();

        ItemStack itemStack;
        ItemMeta itemMeta;

        VehiclesController vehiclesController = Creative.getPlugin().getVehiclesController();

        for (int i = 0 ; i < vehicles.size() ; i++) {

            Vehicle vehicle = vehiclesController.find(vehicles.get(i));

            itemStack = new ItemStack(Material.CHEST);
            itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(vehicle.getName());
            itemStack.setItemMeta(itemMeta);

            gui.addItem(i, itemStack, e -> {
                e.setCancelled(true);
                vehicle.serve(player);
                player.sendMessage(Colors.color("&aPobrano pojazd: &e" + vehicle.getName()));
            });

        }

        guiManager.open(player, gui);

    }

}
