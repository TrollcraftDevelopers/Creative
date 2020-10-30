package pl.trollcraft.creative.essentials.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

public class GamemodeCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Colors.color("&7Uzycie: &e/gamemode 0 - tryb przetrwania,"));
            player.sendMessage(Colors.color("&7Uzycie: &e/gamemode 1 - tryb kreatywny,"));
            player.sendMessage(Colors.color("&7Uzycie: &e/gamemode 2 - tryb przygodowy,"));
            return;
        }

        int mode = Help.isInteger(args[0], -1);

        if (mode < 0 || mode > 2) {
            player.sendMessage(Colors.color("&cZly tryb."));
            return;
        }

        GameMode gameMode = GameMode.getByValue(mode);
        player.setGameMode(gameMode);

        player.sendMessage(Colors.color("&aTryb gry zostal zmieniony."));
    }
}
