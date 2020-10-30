package pl.trollcraft.creative.chat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.blockades.Blockade;
import pl.trollcraft.creative.core.help.blockades.BlockadesController;

public class ToggleMessagesCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (!sender.hasPermission("creative.togglepm")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        Player player = (Player) sender;
        BlockadesController blockadesController = Creative.getPlugin().getBlockadesController();
        Blockade blockade = blockadesController.find("messages");

        assert blockade != null;
        if (blockade.isBlocked(player)) {
            blockade.unblock(player);
            player.sendMessage(Colors.color("&aOdblokowano&7 wiadomosci prywatne."));
        }
        else {
            blockade.block(player);
            player.sendMessage(Colors.color("&cZablokowano&7 wiadomosci prywatne."));
        }

    }
}
