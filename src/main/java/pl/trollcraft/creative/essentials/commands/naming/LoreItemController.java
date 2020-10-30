package pl.trollcraft.creative.essentials.commands.naming;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Arrays;
import java.util.List;

public class LoreItemController extends CommandController  {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (!sender.hasPermission("creative.lore")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien!"));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Colors.color("&eUzycie: &7/opis <opis (linie oddziela sie znakiem %n%)>"));
            return;
        }

        Player player = (Player) sender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            player.sendMessage(Colors.color("&cZly przedmiot."));
            return;
        }

        StringBuilder loreBuilder = new StringBuilder();
        for (int i = 0 ; i < args.length ; i++) {
            loreBuilder.append(args[i]);
            loreBuilder.append(' ');
        }

        String loreString = Colors.color(loreBuilder.toString());
        List<String> lore = Arrays.asList(loreString.split("%n%"));

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        player.sendMessage(Colors.color("&aOpis zostal zmieniony."));
    }

}
