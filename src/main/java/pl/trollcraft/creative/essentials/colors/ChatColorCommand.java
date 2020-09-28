package pl.trollcraft.creative.essentials.colors;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackBuilder;
import pl.trollcraft.creative.core.user.User;

import java.util.ArrayList;

public class ChatColorCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.vip")) {
            sender.sendMessage(Colors.color("&cKomenda jedynie dla graczy VIP"));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;
        openGUI(player);

    }

    private ArrayList<String> colors;

    public ChatColorCommand () {
        colors = new ArrayList<>();
        colors.add(Colors.color("&a"));
        colors.add(Colors.color("&b"));
        colors.add(Colors.color("&c"));
        colors.add(Colors.color("&d"));
        colors.add(Colors.color("&e"));
        colors.add(Colors.color("&f"));
        colors.add(Colors.color("&1"));
        colors.add(Colors.color("&2"));
        colors.add(Colors.color("&3"));
        colors.add(Colors.color("&4"));
        colors.add(Colors.color("&5"));
        colors.add(Colors.color("&6"));
        colors.add(Colors.color("&7"));
        colors.add(Colors.color("&8"));
        colors.add(Colors.color("&9"));
    }

    private void openGUI(Player player) {

        GUI gui = new GUI(27, Colors.color("&a&lKolory"));
        ItemStack itemStack;

        for (int i = 0 ; i < colors.size() ; i++) {

            itemStack = ItemStackBuilder
                    .init(Material.PAPER)
                    .name(Colors.color(colors.get(i) + "███"))
                    .lore(Colors.color("//&eKliknij, by uzywac."))
                    .build();

            String color = colors.get(i);
            gui.addItem(i, itemStack, e -> {

                e.setCancelled(true);

                User user = Creative.getPlugin().getUserController().find(player.getName());
                ChatColorsComponent component = user.findComponent(ChatColorsComponent.COMP_NAME);

                component.setColor(color);
                player.sendMessage(Colors.color("&eWybrano kolor - " + color + "███"));

            });

        }

        Creative.getPlugin().getGuiManager().open(player, gui);

    }

}
