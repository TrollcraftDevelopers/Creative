package pl.trollcraft.creative.chat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.chat.ChatProfile;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class IgnoreCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/ignore <gracz>"));
            return;
        }

        Player player = (Player) sender;

        ChatProfile chatProfile = ChatProfile.get(player);
        assert chatProfile != null;

        if (chatProfile.getIgnored().size() > 20) {
            sender.sendMessage(Colors.color("&cNie mozesz ignorowac wiecej niz 20 graczy."));
            return;
        }

        String ignore = args[0];

        if (chatProfile.getIgnored().contains(ignore)) {
            sender.sendMessage(Colors.color("&cIgnorujesz juz tego gracza."));
            return;
        }

        chatProfile.getIgnored().add(ignore);
        player.sendMessage(Colors.color("&aIgnorujesz od teraz gracza &e" + ignore));

    }

}
