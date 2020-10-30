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
import pl.trollcraft.creative.essentials.colors.data.ChatColorData;
import pl.trollcraft.creative.essentials.colors.data.ChatColorDataController;

import java.util.ArrayList;
import java.util.List;

public class ChatColorCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.colors")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Komenda jedynie dla graczy online.");
                return;
            }

            Player player = (Player) sender;
            openGUI(player);

        }
        else if (args.length == 2) {

            if (!sender.hasPermission("creative.colors.others")) {
                sender.sendMessage(Colors.color("&cBrak uprawnien."));
                return;
            }

            User user = Creative.getPlugin().getUserController().find(args[0]);
            if (user == null)
                sender.sendMessage(Colors.color("&cBrak gracza"));

            else {

                ChatColorsComponent component = user.findComponent(ChatColorsComponent.COMP_NAME);
                component.setColor(args[1]);

                sender.sendMessage(Colors.color("&aZmieniono kolor graczowi."));

            }

        }
        else
            sender.sendMessage(Colors.color("&eUzycie: &7/colors (gracz) (kolor)"));

    }

    private void openGUI(Player player) {

        ChatColorDataController controller = Creative.getPlugin()
                .getChatColorDataController();

        GUI gui = new GUI(27, Colors.color("&a&lKolory"));
        ItemStack itemStack;

        List<ChatColorData> colors = controller.findAll(player);
        ChatColorData data;

        for (int i = 0 ; i < colors.size() ; i++) {

            data = colors.get(i);

            itemStack = ItemStackBuilder
                    .init(Material.PAPER)
                    .name(Colors.color(colors.get(i).getColor() + "███"))
                    .lore(Colors.color("//&eKliknij, by uzywac."))
                    .build();

            String color = data.getColor();
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
