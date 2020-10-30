package pl.trollcraft.creative.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.chat.ChatProfile;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.blockades.Blockade;
import pl.trollcraft.creative.core.help.blockades.BlockadesController;

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
    private static HashMap<CommandSender, String> lastReceivedFrom
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

        BlockadesController blockadesController = Creative
                .getPlugin()
                .getBlockadesController();

        Blockade msgBlockade = blockadesController.find("messages");
        assert msgBlockade != null;

        if (msgBlockade.isBlocked(to)) {
            sender.sendMessage(Colors.color("&cGracz ma wylaczone otrzymywanie wiadomosci prywatnych."));
            return;
        }

        ChatProfile chatProfile = ChatProfile.get(to);
        if (chatProfile.getIgnored().contains(sender.getName())) {
            sender.sendMessage(Colors.color("&cNie mozesz napisac to tego gracza, poniewaz Cie ignoruje."));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1 ; i < args.length ; i++) {
            message.append(args[i]);
            message.append(' ');
        }

        String messageStr = message.toString();

        // Adding to /r
        registerR(sender, to);

        to.sendMessage(Colors.color("&e&lHEJ! &e[" + sender.getName() + " > ja] &r" + messageStr ));
        sender.sendMessage(Colors.color("&e[ja > " + to.getName() + "] &r" + messageStr));

        Creative.getPlugin().getSocialSpyManager().forward(messageStr, sender, to);
    }

    /**
     * Gets a receivers history map.
     * 
     * @return history map
     */
    public static HashMap<CommandSender, String> getLastReceivedFrom() {
        return lastReceivedFrom;
    }

    public static void registerR(CommandSender sender, CommandSender receiver) {

        String last;
        if (lastReceivedFrom.containsKey(sender)){
            last = lastReceivedFrom.get(sender);
            if (!last.equals(receiver.getName()))
                lastReceivedFrom.replace(sender, receiver.getName());

        }
        else
            lastReceivedFrom.put(receiver, receiver.getName());

        if (lastReceivedFrom.containsKey(receiver)){
            last = lastReceivedFrom.get(receiver);
            if (!last.equals(sender.getName()))
                lastReceivedFrom.replace(receiver, sender.getName());

        }
        else
            lastReceivedFrom.put(receiver, sender.getName());

    }

}
