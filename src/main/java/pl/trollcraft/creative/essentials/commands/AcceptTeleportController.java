package pl.trollcraft.creative.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.teleport.AcceptedTeleport;

import java.util.ArrayList;

public class AcceptTeleportController extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {

            ArrayList<Player> demands = AcceptedTeleport.getDemands(player);

            if (demands.size() == 1) {
                AcceptedTeleport.accept(demands.get(0), player);
                player.sendMessage(Colors.color("&aTeleportacja..."));
                demands.get(0).sendMessage(Colors.color("&aZaakceptowano."));
            }
            else if (demands.size() == 0)
                player.sendMessage(Colors.color("&7Brak prosb o teleportacje."));
            else {
                player.sendMessage(Colors.color("&7Masz wiecej niz 1 prosbe, uzyj komendy &e/tpaccept <gracz>"));
                player.sendMessage(Colors.color("&7Prosby:"));
                for (Player p : demands)
                    player.sendMessage(Colors.color(" &e- " + p.getName()));
            }

        }
        else if (args.length == 1) {

            Player asking = Bukkit.getPlayer(args[0]);

            if (AcceptedTeleport.accept(asking, player)){
                player.sendMessage(Colors.color("&aTeleportacja..."));
                asking.sendMessage(Colors.color("&aZaakceptowano."));
            }
            else
                player.sendMessage(Colors.color("&cBrak prosby od tego gracza."));
        }

    }
}
