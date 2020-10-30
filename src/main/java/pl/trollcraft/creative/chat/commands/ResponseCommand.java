package pl.trollcraft.creative.chat.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Stack;

public class ResponseCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!MessageCommand.getLastReceivedFrom().containsKey(sender)) {
            sender.sendMessage(Colors.color("&cBrak rozmowcow."));
            return;
        }

        String rec = MessageCommand.getLastReceivedFrom().get(sender);

        if (rec == null) {
            sender.sendMessage(Colors.color("&cBrak rozmowcow."));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0 ; i < args.length ; i++) {
            message.append(args[i]);
            message.append(' ');
        }

        if (sender instanceof Player)
            ((Player) sender).performCommand("message " + rec + " " + message.toString());

        else
            sender.getServer().dispatchCommand(sender, "message " + rec + " " + message.toString());


    }
}
