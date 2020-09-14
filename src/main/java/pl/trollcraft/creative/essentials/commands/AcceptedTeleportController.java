package pl.trollcraft.creative.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.teleport.AcceptedTeleport;

public class AcceptedTeleportController extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 1) {

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()){
                player.sendMessage(Colors.color("&cGracz offline."));
                return;
            }

            if (AcceptedTeleport.demand(player, targetPlayer)) {
                player.sendMessage(Colors.color("&7Wyslano prosbe teleportacji do gracza &e" + targetPlayer.getName()));

                targetPlayer.sendMessage(Colors.color("&7Gracz &e" + player.getName() + "&7 prosi o zgodne na teleportacje do Ciebie."));
                targetPlayer.sendMessage(Colors.color("&e/tpaccept - &7wyraz zgode"));
                targetPlayer.sendMessage(Colors.color("&e/tpdeny - &7nie wyraz zgody."));
            }
            else
                player.sendMessage(Colors.color("&cNie mozna zrealizowac prosby."));

        }
        else
            player.sendMessage(Colors.color("&eUzycie: &7/tpa <gracz>"));

    }

}
