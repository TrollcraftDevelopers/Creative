package pl.trollcraft.creative.prefixes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.prefixes.gui.Frame;
import pl.trollcraft.creative.prefixes.obj.Prefix;
import pl.trollcraft.creative.prefixes.obj.PrefixType;
import pl.trollcraft.creative.prefixes.obj.PrefixesController;

import java.util.List;
import java.util.stream.Collectors;

public class PrefixesCommand extends CommandController {

    private PrefixesController controller = Creative.getPlugin().getPrefixesController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;
        normalPrefixes(player);

    }

    private void normalPrefixes(Player player) {

        List<Prefix> prefixList = controller.getInstances().stream().
                filter(prefix -> prefix.getType() == PrefixType.NORMAL)
                .collect(Collectors.toList());

        Frame frame = new Frame(Colors.color("&9&lPrefixy &e&lnormalne"), prefixList, player);
        frame.setSwitcher("&e&lPrefixy specjalne", "//&ePrefixy platne i dla MVIPow.");
        frame.setSwitcherAction( e -> {

            e.setCancelled(true);
            specialPrefixes(player);

        } );

        frame.display();

    }

    private void specialPrefixes(Player player) {

        List<Prefix> prefixList = controller.getInstances().stream().
                filter(prefix -> prefix.getType() == PrefixType.SPECIAL)
                .collect(Collectors.toList());

        Frame frame = new Frame(Colors.color("&9&lPrefixy &e&lspecjalne"), prefixList, player);
        frame.setSwitcher("&e&lPrefixy normalne", "//&eDostepne do zakupu dla kazdego.");
        frame.setSwitcherAction( e -> {

            e.setCancelled(true);
            normalPrefixes(player);

        } );

        frame.display();

    }

}
