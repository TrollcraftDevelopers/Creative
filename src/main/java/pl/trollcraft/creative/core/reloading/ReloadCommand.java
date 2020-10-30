package pl.trollcraft.creative.core.reloading;

import org.bukkit.command.CommandSender;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class ReloadCommand extends CommandController {

    private ReloadsController reloadsController = Creative.getPlugin()
            .getReloadsController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("perspectivum.reload")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie: &7/reload <komponent>"));
            return;
        }

        Reloadable reloadable = reloadsController.find(args[0]);

        if (reloadable == null) {
            sender.sendMessage(Colors.color("&cNie znaleziono komponentu."));
            return;
        }

        if (reloadable.reload())
            sender.sendMessage(Colors.color("&aPrzeladowano komponent &e" + args[0]));
        else
            sender.sendMessage(Colors.color("&cBlad przeladowywania (sprawdz konsole)."));

    }

}
