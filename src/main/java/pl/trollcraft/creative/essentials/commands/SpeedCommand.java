package pl.trollcraft.creative.essentials.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

public class SpeedCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.vip")) {
            sender.sendMessage(Colors.color("&cKomenda jedynie dla graczy vip."));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/speed <predkosc>"));
            return;
        }

        float speed = Help.isFloat(args[0], -1F);
        if (speed == -1F) {
            sender.sendMessage(Colors.color("&cTo nie jest liczba."));
            return;
        }

        if (speed > 1) {
            speed++;
            speed /= 10;
        }
        else if (speed == 1)
            speed = 0.2F;

        else if (speed > 10) {
            sender.sendMessage(Colors.color("&cZbyt wysoka predkosc - musi byc mniejsza lub rowna 10."));
            return;
        }
        else {
            sender.sendMessage(Colors.color("&cZbyt niska predkosc - musi byc wieksza lub rowna 1."));
            return;
        }

        Player player = (Player) sender;

        if (player.isFlying()) {
            player.setFlySpeed(speed);
            player.sendMessage(Colors.color("&aPredkosc lotu zostala zmieniona."));
        }
        else {
            player.setWalkSpeed(speed);
            player.sendMessage(Colors.color("&aPredkosc chodu zostala zmieniona."));
        }
    }
}
