package pl.trollcraft.creative.chat.commands.socialspy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SocialSpyCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy.");
            return;
        }

        if (!sender.hasPermission("creative.socialspy")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        Player player = (Player) sender;

        SocialSpyManager socialSpyManager = Creative.getPlugin().getSocialSpyManager();

        if (socialSpyManager.isSpy(player)) {
            socialSpyManager.remove(player);
            player.sendMessage(Colors.color("&cWylaczono&7 social spy."));
        }
        else {
            socialSpyManager.add(player);
            player.sendMessage(Colors.color("&aWlaczono&7 social spy."));
        }

    }

}
