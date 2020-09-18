package pl.trollcraft.creative.essentials.money;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;

import java.util.HashMap;

//TODO make signs more complex!
//TODO make signs more complex!
//TODO make signs more complex!
public class MoneySignListener implements Listener {

    private static final long MONEY_SIGN_COOLDOWN = 1000*60*5;

    private HashMap<String, Long> limits = new HashMap<>();

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        Material type = event.getClickedBlock().getType();

        if (Help.isSign(type)){

            Sign sign = (Sign) event.getClickedBlock().getState();
            String[] lines = sign.getLines();

            if (lines[0].equals(Colors.color("&2&l[PRZEKAZ]"))) {

                if (lines[2].equals(event.getPlayer().getName()))
                    return;

                if (!canUseMoneySign(event.getPlayer())) {
                    event.getPlayer().sendMessage(Colors.color("&cZnakow mozna uzywac co &e5 minut."));
                    return;
                }

                String playerName = lines[2];
                Player player = Bukkit.getPlayer(playerName);

                if (player == null || !player.isOnline()){
                    event.getPlayer().sendMessage(Colors.color("&cGracz jest offline. Z powodu bezpieczenstwa, transakcja odrzucona."));
                    return;
                }

                double money = Double.parseDouble(lines[3].substring(0, lines[3].length() - 2));

                Creative.getPlugin().getEconomy().withdrawPlayer(player, money);
                Creative.getPlugin().getEconomy().depositPlayer(event.getPlayer(), money);

                limits.put(event.getPlayer().getName(), System.currentTimeMillis() + MONEY_SIGN_COOLDOWN);

                player.sendMessage(Colors.color("&7Przekazano &e" + money + " TC. &7graczowi &e" + event.getPlayer().getName() + " (Tabliczka)"));
                event.getPlayer().sendMessage(Colors.color("&aOtrzymano &e" + money + " TC.&a od &e" + lines[2]));


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



    private boolean canUseMoneySign(Player player) {

        String name = player.getName();
        if (!limits.containsKey(name))
            return true;

        return System.currentTimeMillis() >= limits.get(name);

    }

}
