package pl.trollcraft.creative.essentials.moneysigns;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

public class MoneySignListener implements Listener {

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        Material type = event.getClickedBlock().getType();

        if (Help.isSign(type)){

            Sign sign = (Sign) event.getClickedBlock().getState();
            String[] lines = sign.getLines();

            if (lines[0].equals(Colors.color("&2&l[PRZEKAZ]"))) {

                Player player = Bukkit.getPlayer(lines[2]);
                double money = Double.parseDouble(lines[3].substring(0, lines[3].length() - 2));

                if (player.getEntityId() == event.getPlayer().getEntityId())
                    return;

                event.getPlayer().sendMessage(Colors.color("&aPrzekazales &e" + money + " TC &agraczowi &e" + player.getName() + "."));

                if (player != null)
                    player.sendMessage("&e" + event.getPlayer().getName() + " &aprzekazal Ci &e" + money + " TC.");

            }

        }

    }

    @EventHandler
    public void onSignEdit (SignChangeEvent event) {

        if (event.getLine(0).equalsIgnoreCase("TC")) {

            Player player = event.getPlayer();
            double money = Double.parseDouble(event.getLine(1));

            event.setLine(0, Colors.color("&2&l[PRZEKAZ]"));
            event.setLine(1, "");
            event.setLine(2, player.getName());
            event.setLine(3, money + "TC");

            player.sendMessage(Colors.color("&aTabliczka przekazu TC zostala utworzona."));

        }

    }

}
