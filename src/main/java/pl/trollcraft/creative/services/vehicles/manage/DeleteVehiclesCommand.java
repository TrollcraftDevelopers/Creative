package pl.trollcraft.creative.services.vehicles.manage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.safety.vehicles.AbstractVehicle;
import pl.trollcraft.creative.safety.vehicles.AbstractVehiclesController;

import java.util.List;

public class DeleteVehiclesCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.vehicles.delete")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie: &7/deletevehicles <gracz>"));
            return;
        }

        String id = "";
        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);

        if (player != null)
            id = player.getUniqueId().toString();

        else {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            if (offlinePlayer != null)
                id = offlinePlayer.getUniqueId().toString();

            else {

                sender.sendMessage(Colors.color("Brak danych gracza."));
                return;

            }

        }

        AbstractVehiclesController controller = Creative.getPlugin()
                .getAbstractVehiclesController();

        List<AbstractVehicle> vehs = controller.findAll(id);
        vehs.forEach(veh -> sender.sendMessage(Colors.color("&aUsuwanie pojazdu...")));
        vehs.forEach(controller::unregister);

        sender.sendMessage(Colors.color("&aUsunieto pojazdy tego gracza."));

    }

}
