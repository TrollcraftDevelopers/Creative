package pl.trollcraft.creative.essentials.commands.naming;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class RenameItemController extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (!sender.hasPermission("creative.rename")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien!"));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie: &7/nazwa <nazwa>"));
            return;
        }

        Player player = (Player) sender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(Colors.color("&cZly przedmiot."));
            return;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0 ; i < args.length ; i++) {
            nameBuilder.append(args[i]);
            nameBuilder.append(' ');
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Colors.color(nameBuilder.toString()));
        itemStack.setItemMeta(itemMeta);

        player.sendMessage(Colors.color("&aNazwa zostala zmieniona."));
    }
}
