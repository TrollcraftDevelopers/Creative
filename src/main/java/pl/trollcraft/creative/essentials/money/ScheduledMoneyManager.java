package pl.trollcraft.creative.essentials.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.GroupValues;

public final class ScheduledMoneyManager {

    public static void init() {

        GroupValues<Double> money = new GroupValues<>()
                .add("creative.mvip", 12)
                .add("creative.svip", 8)
                .add("creative.vip", 5)
                .add("creative.player", 2);

        Economy economy = Creative.getPlugin().getEconomy();

        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach( player -> {
                    double mon = money.resolve(player);
                    economy.depositPlayer(player, mon);
                    player.sendMessage(Colors.color("&aOtrzymujesz &e" + mon + " &aza gre na serwerze."));
                } );
            }

        }.runTaskTimer(Creative.getPlugin(), 20*60*5, 20*60*5);

    }

}
