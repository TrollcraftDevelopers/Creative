package pl.trollcraft.creative.essentials.giving;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

public class GiveCommand extends CommandController {

    private static final ItemsController itemsController
            = Creative.getPlugin().getItemsController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (args.length == 0)
            sender.sendMessage(Colors.color("&eUzycie: &e/give <przedmiot> (ilosc) (gracz)"));

        // Giving to himself.
        else if (args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Komenda jedynie dla graczy online.");
                return;
            }

            if (!sender.hasPermission("creative.give")){
                sender.sendMessage(Colors.color("&cBrak uprawnien."));
                return;
            }

            tryGive(args[0], 64, (Player) sender);
            
        }

        // Giving to himself certain amount or 64x to other player.
        else if (args.length == 2) {

            int amount = Help.isInteger(args[1], -1);

            // Giving to other player.
            if (amount < 0) {

                if (!sender.hasPermission("creative.give.others")) {
                    sender.sendMessage(Colors.color("&cBrak uprawnien."));
                    return;
                }

                Player player = Bukkit.getPlayer(args[1]);
                if (player == null || !player.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza na serwerze."));
                    return;
                }

                tryGive(args[0], 64, player);

            }
            // Giving a certain amount to himself.
            else {

                if (!(sender instanceof Player)) {
                    sender.sendMessage("Komenda jedynie dla graczy online.");
                    return;
                }

                tryGive(args[0], amount, (Player) sender);

            }

        }

        // Giving a certain amount to other player
        else if (args.length == 3) {

            if (!sender.hasPermission("creative.give.others")) {
                sender.sendMessage(Colors.color("&cBrak uprawnien."));
                return;
            }

            int amount = Help.isInteger(args[1], -1);

            if (amount < 0) {
                sender.sendMessage(Colors.color("&cNieprawidlowa liczba."));
                return;
            }

            Player player = Bukkit.getPlayer(args[2]);
            if (player == null || !player.isOnline()) {
                sender.sendMessage(Colors.color("&cBrak gracza na serwerze."));
                return;
            }

            tryGive(args[0], amount, player);

        }

    }

    private void tryGive(String name, int amount, Player player) {

        Item item = itemsController.find(name);
        if (item == null)
            player.sendMessage(Colors.color("&cNieznany przedmiot."));

        else {

            Material m = item.getType();
            if (m == null)
                player.sendMessage(Colors.color("&cNieznany przedmiot. (MC)"));

            else {

                ItemStack itemStack = new ItemStack(m, amount);
                player.getInventory().addItem(itemStack);
                player.sendMessage(Colors.color("&aDano przedmiot."));

            }
        }
    }

}
