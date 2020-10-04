package pl.trollcraft.creative.essentials.seen;

import org.bukkit.command.CommandSender;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;

public class SeenCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie: &7/seen <gracz>"));
            return;
        }

        String name = args[0];
        User user = Creative.getPlugin().getUserController()
                .find(name);

        if (user != null) {
            sender.sendMessage(Colors.color("&aGracz jest online."));
            return;
        }

        SeenComponent component = Creative.getPlugin()
                .getUserController()
                .loadComponent(SeenComponent.COMP_NAME, name);

        if (component == null) {
            sender.sendMessage(Colors.color("&cBrak gracza."));
            return;
        }

        String seen = component.getLastSeen();
        sender.sendMessage(Colors.color("&aOstatnio widziano gracza: &e" + seen));

    }

}
