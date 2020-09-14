package pl.trollcraft.creative.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class BroadcastCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("prison.admin")){
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&7Uzycie: &e/" + label + " (-p) <wiadomosc>"));
            return;
        }

        int start = 0;
        boolean p = false; // Show nickname.

        if (args[0].startsWith("-")){

            if (args[0].length() > 1){
                start = 1;
                if (args[0].contains("p"))
                    p = true;

            }
            else {
                sender.sendMessage(Colors.color("&cBledne argumenty."));
                return;
            }

        }

        String prefix = Colors.color("&e------------- &e&l[Ogloszenie] &e-------------");
        String suffix = Colors.color("&e----------------------------------------------");

        StringBuilder msg = new StringBuilder();

        if (p)
            prefix = Colors.color("&e----- &e&l[Ogloszenie " + sender.getName() + "] &e-----");

        for (int i = start ; i < args.length ; i++)
            msg.append(args[i] + " ");

        String message = Colors.color(msg.toString());

        final String pre = prefix;
        final String mes = message;
        final String suf = suffix;
        
        Bukkit.getOnlinePlayers().forEach( player -> {

            player.sendMessage(pre);
            player.sendMessage("");
            player.sendMessage(mes);
            player.sendMessage("");
            player.sendMessage(suf);

        } );

        return;
    }
}
