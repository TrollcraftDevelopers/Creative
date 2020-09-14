package pl.trollcraft.creative.essentials.redstone;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.redstone.signals.RedstoneSignal;
import pl.trollcraft.creative.essentials.redstone.signals.SignalsController;

/**
 * Command schema
 * /redstonesign <type> <data>
 */
public class SignalsCommand extends CommandController {

    private SignalsController signalsController = Creative.getPlugin().getSignalsController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla administracji.");
            return;
        }

        Player player = (Player) sender;
        Block sign = player.getTargetBlock(null, 5);

        /*if (sign == null && sign.getType() != Material.SIGN && sign.getType() != Material.WALL_SIGN) {
            player.sendMessage(Colors.color("&cMusisz skierowac wzrok na znak."));
            return;
        }*/

        if (args.length == 0) {
            player.sendMessage(Colors.color("&7Uzycie: &e/redstonesign <typ>"));
            player.sendMessage(Colors.color("&7Typy:"));
            player.sendMessage(Colors.color("&eprzelacznik - &7/redstonesign przelacznik"));
            player.sendMessage(Colors.color("&eprzycisk - &7/redstonesign przycisk <czas zasilenia>"));
            return;
        }

        signalsController.register(new RedstoneSignal(sign));
        player.sendMessage(Colors.color("&aZnak typu: <null> zostal utworzony."));
        player.sendMessage(Colors.color("&7(Aby wylaczyc znak - zniszcz go)"));

    }

}
