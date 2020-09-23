package pl.trollcraft.creative.essentials.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.homes.Home;

import java.util.Objects;

public class DelWarpCommand extends CommandController {
    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }
        Player player = (Player) sender;
        String name = args[0].toLowerCase();
        Warp warp = Warp.get(name);
        if (args.length > 2) {
            player.sendMessage(Colors.color("&cZa dużo argumentów."));
            return;
        }
        if (Warp.get(name) == null){
            player.sendMessage(Colors.color("&cNie ma takiego warpa."));
            return;
        }
        warp.delete();

    }
}


