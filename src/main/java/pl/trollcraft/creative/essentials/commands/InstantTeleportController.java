package pl.trollcraft.creative.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.teleport.InstantTeleport;

public class InstantTeleportController extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
    // Changed so it is different permission.
        if (!player.hasPermission("creative.tp")) {
            player.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 1) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()){
                player.sendMessage(Colors.color("&cGracz offline."));
                return;
            }

            Location loc = targetPlayer.getLocation();
            if (!new InstantTeleport(player, loc).teleport())
                player.sendMessage(Colors.color("&cNie mozna zrealizowac teleportacji."));
            else
                player.sendMessage(Colors.color("&aTeleportacja..."));

        }
        else
            player.sendMessage(Colors.color("&eUzycie: &7/tp <gracz>"));

    }

}
