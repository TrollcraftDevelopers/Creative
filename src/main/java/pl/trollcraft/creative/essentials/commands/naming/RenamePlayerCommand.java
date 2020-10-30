package pl.trollcraft.creative.essentials.commands.naming;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class RenamePlayerCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.nick")) {
            sender.sendMessage(Colors.color("&cKomenda dostepna dla graczy VIP."));
            return;
        }

        if (args.length == 0) {

            sender.sendMessage(Colors.color("&e/nick <nowy_nick> -&7 zmienia nazwe na chat'cie."));
            sender.sendMessage(Colors.color("&e/nick reset -&7 resetuje nazwe na chat'cie."));

            if (sender.hasPermission("creative.nick.others")) {
                sender.sendMessage(Colors.color("&e/nick <gracz> <nowy_nick> -&7 zmienia nazwe gracza na chat'cie."));
                sender.sendMessage(Colors.color("&e/nick <gracz> reset -&7 resetuje nazwe gracza na chat'cie."));
            }

        }
        else {

            if (args.length == 1) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage("Komenda jedynie dla graczy online.");
                    return;
                }

                Player player = (Player) sender;

                if (args[0].equalsIgnoreCase("reset")) {
                    player.setDisplayName(player.getName());
                    player.sendMessage(Colors.color("&7Nick zostal zresetowany."));
                }
                else {
                    player.setDisplayName(Colors.color(args[0].replace('_', ' ')));
                    player.sendMessage(Colors.color("&aNazwa zostala zmieniona!"));
                }

            }
            else if (args.length == 2) {

                if (!sender.hasPermission("creative.admin")){
                    sender.sendMessage(Colors.color("&cBrak uprawnien."));
                    return;
                }

                Player player = Bukkit.getPlayer(args[0]);

                if (player == null || !player.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza."));
                    return;
                }

                if (args[1].equalsIgnoreCase("reset")) {
                    player.setDisplayName(player.getName());
                    sender.sendMessage(Colors.color("&7Nick gracza zostal zresetowany."));
                }
                else {
                    player.setDisplayName(Colors.color(args[1].replace('_', ' ')));
                    sender.sendMessage(Colors.color("&aNazwa gracza zostala zmieniona!"));
                }

            }

        }

    }

}
