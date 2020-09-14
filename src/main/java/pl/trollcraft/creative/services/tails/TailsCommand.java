package pl.trollcraft.creative.services.tails;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;

import java.util.Arrays;

public class TailsCommand extends CommandController {

    private final Creative plugin = Creative.getPlugin();
    private final UsersController usersController = plugin.getUserController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (sender.hasPermission("creative.tails.admin")) {

            if (args.length == 0) {
                openTailsMenu((Player) sender);

                sender.sendMessage(Colors.color("&7/tails add <gracz> <ogon>"));
                sender.sendMessage(Colors.color("&7/tails remove <gracz> <ogon>"));
                sender.sendMessage(Colors.color("&7/tails list <gracz>"));
                return;
            }

            if (args[0].equalsIgnoreCase("add")) {

                if (args.length != 3){
                    sender.sendMessage(Colors.color("&7Uzycie: &e/tails add <gracz> <ogon>"));
                    return;
                }

                String name = args[1];
                String tailName = args[2];

                User user = usersController.find(name);

                if (user == null) {
                    sender.sendMessage(Colors.color("&cBrak uzytkownika o podanym nick'u."));
                    return;
                }

                TailsComponent tailsComponent = (TailsComponent) user.getComponent(TailsComponent.COMP_NAME);
                tailsComponent.addTail(tailName);

                sender.sendMessage(Colors.color("&aDodano ogon uzytkownikowi."));
            }

            else if (args[0].equalsIgnoreCase("list")) {

                if (args.length != 2){
                    sender.sendMessage(Colors.color("&7Uzycie: &e/tails list <gracz>"));
                    return;
                }

                String name = args[1];

                User user = usersController.find(name);

                if (user == null) {
                    sender.sendMessage(Colors.color("&cBrak uzytkownika o podanym nick'u."));
                    return;
                }

                TailsComponent tailsComponent = (TailsComponent) user.getComponent(TailsComponent.COMP_NAME);
                tailsComponent.getTailsAvailable().forEach( tailName -> sender.sendMessage(Colors.color("&7- " + tailName)) );

            }

        }

        else openTailsMenu((Player) sender);

    }

    private void openTailsMenu(Player player) {

        GUI gui = new GUI(54, Colors.color("&2&lOGONY"));

        User user = usersController.find(player.getName());
        TailsComponent component = (TailsComponent) user.getComponent(TailsComponent.COMP_NAME);

        if (component.getSelectedTail() == null) {
            ItemStack icon = new ItemStack(Material.BARRIER);
            ItemMeta itemMeta = icon.getItemMeta();
            itemMeta.setDisplayName(Colors.color("&c&lBRAK OGONA"));
            icon.setItemMeta(itemMeta);

            gui.addItem(4, icon, e -> e.setCancelled(true));
        }
        else {
            Tail tail = Tail.find(component.getSelectedTail());

            if (tail == null) {

                ItemStack icon = new ItemStack(Material.BARRIER);
                ItemMeta itemMeta = icon.getItemMeta();
                itemMeta.setDisplayName(Colors.color("&c&lBRAK OGONA"));
                icon.setItemMeta(itemMeta);

                gui.addItem(4, icon, e -> e.setCancelled(true));
            }
            else {
                ItemStack icon = new ItemStack(Material.LEAD);
                ItemMeta itemMeta = icon.getItemMeta();
                itemMeta.setDisplayName(tail.getName());
                itemMeta.setLore(Arrays.asList(new String[] {"", Colors.color("&6&lObecnie wlaczony ogon."), Colors.color("&6&lKliknij, by wylaczyc")}));
                icon.setItemMeta(itemMeta);

                gui.addItem(4, icon, e ->  {

                    component.setSelectedTail(null);
                    Tail.disableTail(player);
                    e.setCancelled(true);

                    gui.setTransacting(true);
                    openTailsMenu(player);

                });
            }

        }

        int slot = 9;
        for (String tailName : component.getTailsAvailable()){

            Tail tail = Tail.find(tailName);

            ItemStack icon = new ItemStack(Material.LEAD);
            ItemMeta itemMeta = icon.getItemMeta();
            itemMeta.setDisplayName(tail.getName());
            itemMeta.setLore(Arrays.asList(new String[] {"", Colors.color("&6&lKliknij, by wlaczyc")}));
            icon.setItemMeta(itemMeta);

            gui.addItem(slot, icon, e -> {

                component.setSelectedTail(tail.getId());
                Tail.runTail(player, tail);
                e.setCancelled(true);

                gui.setTransacting(true);
                openTailsMenu(player);

            });

            slot++;

        }

        Creative.getPlugin().getGuiManager().open(player, gui);

    }

}
