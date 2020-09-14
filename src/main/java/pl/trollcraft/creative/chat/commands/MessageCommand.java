package pl.trollcraft.creative.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashMap;
import java.util.Stack;

/**
 * Simple messaging system.
 * /msg <player> <message>.
 */
public class MessageCommand extends CommandController {

    /**
     * Recent senders, who have sent a message to
     * the player or console.
     */
    private static HashMap<CommandSender, Stack<String>> messagesReceivers
            = new HashMap<>();

    /**
     * On command listener.
     *
     * @param sender player who sent a command.
     * @param label command name used to invoke.
     * @param args array of arguments.
     */
    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(Colors.color("&eUzycie: &e/" + label + " <gracz> <wiadomosc>"));
            return;
        }

        Player to = Bukkit.getPlayer(args[0]);
        if (to == null || !to.isOnline()) {
            sender.sendMessage(Colors.color("&cGracz " + args[0] + " jest offline lub nie istnieje."));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1 ; i < args.length ; i++) {
            message.append(args[i]);
            message.append(' ');
        }

        String messageStr = message.toString();

        // Adding to /r

        if (messagesReceivers.containsKey(to))
            messagesReceivers.get(to).push(sender.getName());

        else {
            Stack<String> rec = new Stack<>();
            rec.push(sender.getName());
            messagesReceivers.put(to, rec);
        }

        to.sendMessage(Colors.color("&e&lHEJ! &e[" + sender.getName() + " > ja] &r" + messageStr ));
        sender.sendMessage(Colors.color("&e[ja > " + to.getName() + "] &r" + messageStr));

    }

    /**
     * Gets a receivers history map.
     * 
     * @return history map
     */
    public static HashMap<CommandSender, Stack<String>> getMessagesReceivers() {
        return messagesReceivers;
    }
}
