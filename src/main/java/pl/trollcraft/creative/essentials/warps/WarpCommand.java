package pl.trollcraft.creative.essentials.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class WarpCommand extends CommandController {
    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda dostepna jedynie dla gracza online.");
            return;
        }

        Player player = (Player) sender;
        String world = player.getWorld().getName();


        if (args.length != 1) {
            player.sendMessage(Colors.color("&cUzycie: /warp <nazwa>"));
            return;
        }

        String name = args[0].toLowerCase();
        Warp warp = Warp.get(name);

        if (Warp.get(name) == null){
            player.sendMessage(Colors.color("&cNie ma takiego warpa."));
            return;
        }

        warp.teleport(player);

    }

}

