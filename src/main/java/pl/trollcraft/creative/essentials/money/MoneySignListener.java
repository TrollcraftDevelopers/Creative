package pl.trollcraft.creative.essentials.money;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

import java.util.Objects;

public class MoneySignListener implements Listener {

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        Material type = event.getClickedBlock().getType();

        if (Help.isSign(type)){

            Sign sign = (Sign) event.getClickedBlock().getState();
            String[] lines = sign.getLines();

            if (lines[0].equals(Colors.color("&2&l[PRZEKAZ]"))) {

                if (lines[2].isEmpty() || lines[3].isEmpty()) return;

                if (lines[2].equals(event.getPlayer().getName()))
                    return;


                String playerName = lines[2];
                OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

                double money = Double.parseDouble(lines[3].substring(0, lines[3].length() - 2));

                Economy eco = Creative.getPlugin().getEconomy();

                EconomyResponse res = eco.withdrawPlayer(event.getPlayer(), money);
                if (res.transactionSuccess()) {
                    Creative.getPlugin().getEconomy().depositPlayer(player, money);
                    event.getPlayer().sendMessage(Colors.color("&aWyslano &e" + money + "TC &a do &e" + lines[2]));
                }
                else
                    event.getPlayer().sendMessage(Colors.color("&cBrak srodkow do przekazania."));

            }

        }

    }

    @EventHandler
    public void onSignEdit (SignChangeEvent event) {

        if (event.getLine(0).equalsIgnoreCase("TC")) {

            if (Objects.requireNonNull(event.getLine(1)).isEmpty())
                return;

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
