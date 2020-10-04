package pl.trollcraft.creative.essentials.warps.playerwarps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class TeleportToWarpCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy!");
            return;
        }

        Player player = (Player) sender;

        if(args.length == 2){
            String player_name = args[0];
            String warp_name = args[1];

            if(!PlayerWarp.doesWarpExist(player_name, warp_name)){
                player.sendMessage(Colors.color("&cTaki warp nie istnieje!"));
                return;
            }

            player.teleport(PlayerWarp.getWarpByName(player_name, warp_name));
            player.sendMessage(Colors.color("&aTeleportowano do warpa o nazwie &7" + warp_name + " &agracza &7" + player_name));
            return;
        }

        player.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }

}
