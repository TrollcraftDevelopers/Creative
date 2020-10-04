package pl.trollcraft.creative.essentials.teleport.demanded;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.demands.Demand;
import pl.trollcraft.creative.core.help.demands.DemandsController;

public class RefuseTeleportCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        DemandsController demandsController = Creative.getPlugin().getDemandsController();
        Demand demand;

        if (args.length != 1) {

            demand = demandsController.findFirst("TPA", sender.getName());

            if (demand == null) {
                sender.sendMessage(Colors.color("&cBrak prosb teleportacji."));
                return;
            }

        }
        else {
            String fromWhomName = args[0];
            demand = demandsController.find("TPA", fromWhomName);

            if (demand == null) {
                sender.sendMessage(Colors.color("&cBrak prosby od gracza &e" + fromWhomName));
                return;
            }

        }

        demandsController.refuse(demand);
        sender.sendMessage(Colors.color("&7Odrzucono prosbe teleportacji od gracza &e" + demand.getWho()));

    }

}
