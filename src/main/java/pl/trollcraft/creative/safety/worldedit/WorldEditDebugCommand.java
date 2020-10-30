package pl.trollcraft.creative.safety.worldedit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditDebugCommand extends CommandController {

    private WorldEditCommandsController controller = Creative.getPlugin().getWorldEditCommandsController();
    private UsersController userController = Creative.getPlugin().getUserController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("creative.admin")) {
            player.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(Colors.color("&eUzycie: &7/" + label + " <komenda>"));
            return;
        }

        WorldEditCommand command = controller.find(args[0]);
        User user = userController.find(player.getName());
        WorldEditComponent worldEditComponent = user.findComponent(WorldEditComponent.COMP_NAME);

        if (command == null) {
            player.sendMessage(Colors.color("&cKomenda nie zostala znaleziona."));
            return;
        }

        player.sendMessage(Colors.color("&eKomenda: " + command.getName()));

        player.sendMessage(Colors.color("&eAliasy:"));
        for (int i = 0; i < command.getAliases().length ; i++)
            sender.sendMessage(Colors.color("&e- " + command.getAliases()[i]));

        player.sendMessage("");
        player.sendMessage(Colors.color("&eArgumenty:"));

        command.getArguments().forEach( arg -> {

            player.sendMessage(Colors.color("&e" + arg.getOrder() + ". podpiete limity: "));
            arg.getLimits().forEach(lim -> sender.sendMessage(Colors.color("&e- " + lim.getId())));

        });

        player.sendMessage("");
        player.sendMessage(Colors.color("&eMax. zaznaczenie dla Ciebie: " + command.getMaxSelections().resolve(player)));
        player.sendMessage(Colors.color("&eCzas cooldown'u po komendzie: " + command.getCooldown().resolve(player)));
        player.sendMessage(Colors.color("&eCzas przed mozliwoscia uzycia komendy: " + command.getAvailable().resolve(player)));
        player.sendMessage("");

        player.sendMessage(Colors.color("&eCzas do bycia online 30min: " + worldEditComponent.getTimeToPlayFor(30 * 60)));
        player.sendMessage(Colors.color("&eOnline 30s?: " + worldEditComponent.hasPlayedFor(30)));
        player.sendMessage(Colors.color("&eOnline 1min?: " + worldEditComponent.hasPlayedFor(60)));
        player.sendMessage(Colors.color("&eOnline 2min?: " + worldEditComponent.hasPlayedFor(120)));
    }
}
