package pl.trollcraft.creative.shops;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;

public class ShopCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        ShopSession shopSession
                = Creative.getPlugin().getShopSessionsController().find(player);

        if (shopSession == null)
            Creative.getPlugin().getShopSessionsController().register(new ShopSession(player));
        else
            shopSession.reset();

        Shop shop = Creative.getPlugin().getShopManager().getRoot();
        shop.open(player);
    }

}
