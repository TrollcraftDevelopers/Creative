package pl.trollcraft.creative.essentials.teleport.demanded;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.demands.DemandHandler;
import pl.trollcraft.creative.core.help.demands.DemandsController;

public class DemandedTeleportCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/tpa <do kogo>"));
            return;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null || !target.isOnline()) {
            sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
            return;
        }

        if (VanishAPI.isInvisible(target)) {
            sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
            return;
        }

        if(TeleportToggleCommand.players_toggles.containsKey(targetName)){
            if(!TeleportToggleCommand.players_toggles.get(targetName)){
                sender.sendMessage(Colors.color("&cTen gracz ma wylaczona mozliwosc teleportacji!"));
                return;
            }
        }

        DemandsController demandsController = Creative.getPlugin().getDemandsController();

        if (demandsController.find("TPA", sender.getName(), target.getName()) != null) {
            sender.sendMessage(Colors.color("&cWyslales juz zadanie teleportacji do tego gracza."));
            return;
        }

        Player player = (Player) sender;

        demandsController.create("TPA", player.getName(), target.getName(), new DemandHandler() {

            @Override
            public void accepted() {
                player.sendMessage(Colors.color("&aGracz &e" + target.getName() + " &azaakceptowal Twoja prosbe teleportacji."));
                player.teleport(target);
            }

            @Override
            public void refused() {
                player.sendMessage(Colors.color("&cGracz &e" + target.getName() + " &codrzucil Twoja prosbe teleportacji."));
            }

        });

        target.sendMessage(Colors.color("&7Prosba teleportacji od gracza &e" + player.getName()));
        target.sendMessage(Colors.color("&e/tpaccept - &7by zaakceptowac,"));
        target.sendMessage(Colors.color("&e/tpdeny - &7by odrzucic."));

        player.sendMessage(Colors.color("&7Wyslano prosbe teleportacji do gracza &e" + target.getName()));

    }

}
