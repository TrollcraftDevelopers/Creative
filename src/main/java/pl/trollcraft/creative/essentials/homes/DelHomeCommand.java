package pl.trollcraft.creative.essentials.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Objects;

public class DelHomeCommand extends CommandController {
    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }
        Player player = (Player) sender;
        if (args.length > 2) {
            player.sendMessage(Colors.color("&cZa dużo argumentów."));
            return;
        }
        for (Home home : Objects.requireNonNull(Home.gethomes(player))) {
            if (args[0].equalsIgnoreCase(home.getName())) {
                home.delete(player);
                player.sendMessage(Colors.color("&cUsunieto home."));
                return;
            }
        }
        player.sendMessage(Colors.color("&c Nie ma takiego home."));
    }
}
