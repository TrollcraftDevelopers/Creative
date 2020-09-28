package pl.trollcraft.creative.essentials.poses;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SitCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        Block target = player.getTargetBlock(null, 5);

        if (target.getType() == Material.AIR)
            player.sendMessage(Colors.color("&cNie mozesz usiasc na tym bloku."));
        else
            Sitting.sit(player, target);

    }

}
