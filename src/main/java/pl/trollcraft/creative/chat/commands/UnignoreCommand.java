package pl.trollcraft.creative.chat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.chat.ChatProfile;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class UnignoreCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/unignore <gracz>"));
            return;
        }

        Player player = (Player) sender;

        ChatProfile chatProfile = ChatProfile.get(player);
        assert chatProfile != null;

        String ignore = args[0];

        if (!chatProfile.getIgnored().contains(ignore)) {
            sender.sendMessage(Colors.color("&cNie ignorujesz tego gracza."));
            return;
        }

        chatProfile.getIgnored().remove(ignore);
        player.sendMessage(Colors.color("&aNie ignorujesz od teraz gracza &e" + ignore));


    }

}
