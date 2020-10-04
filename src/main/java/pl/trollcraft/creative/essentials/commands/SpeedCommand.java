package pl.trollcraft.creative.essentials.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

public class SpeedCommand extends CommandController {

    // Flying = 1-10

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

        // Checking if input is number.
        float speed = Help.isFloat(args[0], -1F);
        if (speed == -1F) {
            sender.sendMessage(Colors.color("&cTo nie jest liczba."));
            return;
        }

        if (speed > 10 || speed <= 0) {
            sender.sendMessage(Colors.color("&cZla wartosc - nie mozesz byc mniejsza rowna 0, ani wieksza od 10."));
            return;
        }

        Player player = (Player) sender;

        if (player.isFlying()) {
            setFlySpeed(speed, player);
            player.sendMessage(Colors.color("&aUstawiono predkosc lotu na &e" + speed));
        }
        else {
            setWalkSpeed(speed, player);
            player.sendMessage(Colors.color("&aUstawiono predkosc biegu na &e" + speed));
        }

    }

    private void setWalkSpeed(float speed, Player player) {

        float a;

        // G(X) = -0.2 * (x - 1) + 0.2
        if (speed < 1)
            a = -.2F;


        // F(x) = 4/405 * (x - 1)^2 + 0.2
        else
            a = 4F/405F;

        float speedVal = a * (float) Math.pow(speed - 1, 2) + .2F;
        player.setWalkSpeed(speedVal);

    }

    private void setFlySpeed(float speed, Player player) {
        player.setFlySpeed(speed / 10);
    }

}
