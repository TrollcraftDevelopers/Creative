package pl.trollcraft.creative.safety.leaks;

import org.bukkit.command.CommandSender;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SafetyCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.admin")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&7Manager bezpieczenstwa."));
            sender.sendMessage("");
            sender.sendMessage(Colors.color("&e/safety run <id> -&7 uruchamia system bezpieczenstwa."));
            return;
        }
        else {

            if (args[0].equalsIgnoreCase("run")) {

                if (args.length != 2) {
                    sender.sendMessage(Colors.color("&cUzycie: &e/safety run <id>"));
                    return;
                }

                int id = Integer.parseInt(args[1]);
                SafetyProvider provider = Creative.getPlugin().getSafetyProviderController().find(id);

                if (provider == null) {
                    sender.sendMessage(Colors.color("&cBrak systemu o podanym identyfikatorze."));
                    return;
                }


                sender.sendMessage(Colors.color("&7Skanowanie..."));
                SafetyLeak leak = provider.scan();

                if (leak == null) {
                    sender.sendMessage(Colors.color("&aNie znaleziono zadnych problemow."));
                    return;
                }

                leak.solve();
                sender.sendMessage(Colors.color("&a" + leak.message()));

                return;
            }

        }

    }
}
