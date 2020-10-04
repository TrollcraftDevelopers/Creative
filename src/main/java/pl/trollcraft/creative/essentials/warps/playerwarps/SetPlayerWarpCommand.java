package pl.trollcraft.creative.essentials.warps.playerwarps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.GroupValues;

public class SetPlayerWarpCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy!");
            return;
        }

        Player player = (Player) sender;

        int warps = (int) new GroupValues<Integer>().add("Creative.mvip", 10).add("Creative.svip", 5).add("Creative.vip", 2).add("Creative.player", 1).resolve(player);

        if(args.length == 1){
            String warp_name = args[0];

            if(!PlayerWarp.doesWarpExist(player.getName(), warp_name)){
                if(PlayerWarp.getPlayerWarpsAmount(player.getName()) >= warps){
                    player.sendMessage(Colors.color("&cOsiagnales maksymalna liczbe warpow!"));
                    return;
                }
            }
            PlayerWarp.addWarp(player.getName(), warp_name, player.getLocation());
            player.sendMessage(Colors.color("&aUtworzono warp o nazwie &7" + warp_name));
            return;
        }

        player.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
