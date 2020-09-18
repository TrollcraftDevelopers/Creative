package pl.trollcraft.creative.games;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.games.parkour.Parkour;

import java.util.List;

public class GamesCommand extends CommandController {

    private static PlayablesController playablesController = Creative.getPlugin().getPlayablesController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Colors.color("&aGry Creative:"));
            player.sendMessage("");
            player.sendMessage(Colors.color("&aWitaj w systemie gier i atrakcji Creative TC!"));
            player.sendMessage(Colors.color("&aTworz niesamowite gry i udostepniaj je innym graczom!"));
            player.sendMessage("");
            player.sendMessage(Colors.color("&e/gry dolacz <nazwa> &7- dolacz do gry o danej nazwie,"));
            player.sendMessage(Colors.color("&e/gry opusc &7- opuszcza aktualna gre,"));
            player.sendMessage("");
            player.sendMessage(Colors.color("&e/gry info <nazwa> &7- informacje o grze,"));
            player.sendMessage(Colors.color("&e/gry <gracz> &7- gry danego gracza."));
            player.sendMessage("");
            //TODO player.sendMessage(Colors.color("&e/gry tworzenie &7- informacje odnosnie tworzenia gier."));
        }

        else {

            if (args[0].equalsIgnoreCase("dolacz")) {

                if (args.length != 2) {
                    player.sendMessage(Colors.color("&eUzycie: &7/gra dolacz <nazwa>"));
                    return;
                }

                String name = args[1];
                Playable playable = playablesController.find(name, Parkour.TYPENAME);

                if (playable == null) {
                    player.sendMessage(Colors.color("&cParkour o podanej nazwie nie istnieje."));
                    return;
                }

                if (playable.isConserved()) {
                    player.sendMessage(Colors.color("&cParkour o podanej nazwie jest konserwowany."));
                    return;
                }

                Result result = playable.join(player);
                if (result != Result.JOINED){
                    player.sendMessage(Colors.color("&c" + result.getMessage()));
                    return;
                }

                player.sendMessage(Colors.color("&aDolaczono do gry."));
                return;

            }
            else if (args[0].equalsIgnoreCase("opusc")) {

                Playable playable = playablesController.find(player);

                if (playable == null) {
                    player.sendMessage(Colors.color("&cNie korzystasz z zadnej gry ani atrakcji."));
                    return;
                }

                Result result = playable.leave(player);

                if (result != Result.LEFT){
                    player.sendMessage(Colors.color("&c" + result.getMessage()));
                    return;
                }

                player.sendMessage(Colors.color("&aOpuszczono gre."));

            }
            else if (args[0].equalsIgnoreCase("info")) {

                if (args.length != 2) {
                    player.sendMessage(Colors.color("&eUzycie: &7/gra info <nazwa>"));
                    return;
                }

                String name = args[1];
                Playable playable = playablesController.find(name, Parkour.TYPENAME);

                player.sendMessage(playable.toString());

                return;

            }
            else {

                String creatorName = args[0];
                List<Playable> list = playablesController.findAll(creatorName);

                if (list.isEmpty()){
                    player.sendMessage(Colors.color("&cTen gracz nie stworzyl jeszcze nic."));
                    return;
                }

                player.sendMessage(Colors.color("&aGry stworzone przez &e" + creatorName + ":"));
                player.sendMessage("");
                list.forEach( playable -> player.sendMessage(Colors.color("&a- " + playable.getName() + ", typ: " + playable.getType())) );

                return;

            }

        }

    }

}
