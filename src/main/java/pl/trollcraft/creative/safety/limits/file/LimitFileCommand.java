package pl.trollcraft.creative.safety.limits.file;

import org.bukkit.command.CommandSender;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class LimitFileCommand extends CommandController {

    private final LimitFilesController controller =
            Creative.getPlugin().getLimitFilesController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.limits.files")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie:"));
            sender.sendMessage(Colors.color("&e/lf <id> &7- informacje pliku,"));
            sender.sendMessage(Colors.color("&e/lf list &7- lista zaladowanych plikow,"));
            sender.sendMessage(Colors.color("&e/lf registers &7- lista rejestrow,"));
            sender.sendMessage(Colors.color("&e/lf register <nazwa> &7- lista plikow w rejestrze."));
        }

        else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("list")) {

                sender.sendMessage(Colors.color("&eZaladowane pliki limitow:"));
                controller.getInstances()
                          .forEach(fl -> sender.sendMessage(Colors.color(String.format("&e- %s", fl.getId()))));

            }

            else if (args[0].equalsIgnoreCase("registers")) {

                sender.sendMessage(Colors.color("&ePliki podlaczone:"));
                controller.getRegisters()
                        .forEach(fl -> sender.sendMessage(Colors.color(String.format("&e- %s", fl))));

            }

            else {

                LimitFile lf = controller.find(args[0]);
                if (lf == null)
                    sender.sendMessage(Colors.color("&cNieznany plik."));

                else {

                    sender.sendMessage(Colors.color("&eDane z pliku:"));
                    lf.getLimits().forEach(l -> {

                        if (l.data != null)
                            sender.sendMessage(Colors.color(String.format("&e- %s:%s, max: %d", l.name, l.data, l.max)));
                        else
                            sender.sendMessage(Colors.color(String.format("&e- %s, max: %d", l.name, l.max)));

                    });

                }

            }

        }
        else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("register")) {

                String regName = args[1];
                sender.sendMessage(Colors.color("&ePliki w rejestrze:"));
                controller.get(regName).forEach( lim -> sender.sendMessage(Colors.color(String.format("&e- %s", lim.getId()))) );

            }

        }

    }

}
