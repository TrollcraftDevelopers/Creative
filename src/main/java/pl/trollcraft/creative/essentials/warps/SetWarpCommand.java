package pl.trollcraft.creative.essentials.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SetWarpCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda dostepna jedynie dla gracza online.");
            return;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("Creative.setwarp")){
            player.sendMessage(Colors.color(" &cBrak uprawnien."));
            return;
        }

        if (args.length > 1) {
            player.sendMessage(Colors.color(" &cUzycie: /setwarp <nazwa>"));
            return;
        }

        String name = args[0].toLowerCase();

        if (Warp.get(name) != null){
            player.sendMessage(Colors.color(" &cWarp o takiej nazwie juz istnieje."));
            return;
        }

        new Warp(name, player.getLocation()).save();
        player.sendMessage(Colors.color(" &aWarp utworzony (" + name + ")"));
    }
}

