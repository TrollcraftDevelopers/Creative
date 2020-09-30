package pl.trollcraft.creative.essentials.warps.playerwarps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.GroupValues;

public class DelPlayerWarpCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy!");
            return;
        }

        Player player = (Player) sender;

        if(args.length == 1){
            String warp_name = args[0];

            if(!PlayerWarp.doesWarpExist(player.getName(), warp_name)){
                player.sendMessage(Colors.color("&cTaki warp nie istnieje!"));
                return;
            }

            PlayerWarp.removeWarp(player.getName(), warp_name);
            player.sendMessage(Colors.color("&cUsunieto warp o nazwie &7" + warp_name));
            return;
        }

        player.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
