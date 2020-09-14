package pl.trollcraft.creative.games.parkour;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.blocksdetector.BlockDetector;
import pl.trollcraft.creative.core.help.blocksdetector.DetectionRequest;
import pl.trollcraft.creative.games.AttractionsController;
import pl.trollcraft.creative.games.Result;

public class ParkourCommand extends CommandController {

    private AttractionsController attractionsController
            = Creative.getPlugin().getAttractionsController();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla administracji.");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Colors.color("&aParkour'y - obsluga:"));
            player.sendMessage(Colors.color("&e/parkour dolacz <nazwa> -&7 dolacz do parkour'u,"));
            player.sendMessage(Colors.color("&e/parkour opusc - &7opuszcza parkour,"));
            player.sendMessage(Colors.color("&e/parkour <gracz> - &7parkour'y podanego gracza."));
            player.sendMessage("");
            player.sendMessage(Colors.color("&aParkoury - tworzenie:"));
            player.sendMessage(Colors.color("&e/parkour nowy <nazwa> - &7tworzy nowy parkour,"));
            player.sendMessage(Colors.color("&e/parkour lokacja start - &7okresla poczatek parkour'u,"));
            player.sendMessage(Colors.color("&e/parkour blok spadek - &7tworzy blok spadku,"));
            player.sendMessage(Colors.color("&e/parkour blok checkpoint - &7tworzy blok checkpoint'u,"));
            player.sendMessage(Colors.color("&e/parkour blok koniec - &7tworzy blok zakonczenia parkour'u,"));
            player.sendMessage(Colors.color("&e/parkour opublikuj - &7sprawdza i uruchamia parkour."));
            return;
        }

        else if (args[0].equalsIgnoreCase("dolacz")) {

            if (args.length != 2) {
                player.sendMessage(Colors.color("&eUzycie: &7/parkour dolacz <nazwa>"));
                return;
            }

            String name = args[1];
            Parkour parkour = (Parkour) attractionsController.find(name, Parkour.TYPENAME);

            if (parkour == null) {
                player.sendMessage(Colors.color("&cParkour o podanej nazwie nie istnieje."));
                return;
            }

            if (parkour.isConserved()) {
                player.sendMessage(Colors.color("&cParkour o podanej nazwie jest konserwowany."));
                return;
            }

            Result result = parkour.join(player);
            if (result != Result.JOINED){
                player.sendMessage(Colors.color("&c" + result.getMessage()));
                return;
            }

            player.sendMessage(Colors.color("&aDolaczono na parkour."));
            return;

        }

        else if (args[0].equalsIgnoreCase("nowy")) {

            if (args.length != 2) {
                player.sendMessage(Colors.color("&eUzycie: &7/parkour nowy <nazwa>"));
                return;
            }

            // Check if player is playing or creating a game.

            if (attractionsController.find(player, Parkour.TYPENAME) != null) {
                player.sendMessage(Colors.color("&cObecnie grasz - nie mozesz tworzyc gry."));
                return;
            }

            if (attractionsController.findConserved(player) != null){
                player.sendMessage(Colors.color("&cObecnie tworzysz juz gre."));
                return;
            }

            //TODO check if player has not too much created games.

            String name = args[1];

            if (attractionsController.find(name) != null) {
                player.sendMessage(Colors.color("&cGra o takiej nazwie juz istnieje."));
                return;
            }

            Parkour parkour = new Parkour(name, player);
            parkour.setConserved(true);

            attractionsController.register(parkour);

            player.sendMessage(Colors.color("&aParkour zostal utworzony. Teraz nalezy go skonfigurowac.\n" +
                    "&7Gdy zakonczysz konfiguracje - uzyj komendy /parkour opublikuj."));

            return;

        }
        else if (args[0].equalsIgnoreCase("lokacja")) {

            if (args.length != 2) {
                player.sendMessage(Colors.color("&eUzycie: &7/parkour lokacja start"));
                return;
            }

            Parkour parkour = (Parkour) attractionsController.findConserved(player, Parkour.TYPENAME);

            if (parkour == null) {
                player.sendMessage(Colors.color("&cNie konserwujesz zadnego parkour'a."));
                return;
            }

            parkour.setStartLocation(player.getLocation());

            player.sendMessage(Colors.color("&aUstawiono lokacje startowa parkour'u."));

            return;

        }
        else if (args[0].equalsIgnoreCase("blok")) {

            if (args.length != 2) {
                player.sendMessage(Colors.color("&eUzycie: &7/parkour blok <spadek|checkpoint|koniec>"));
                return;
            }

            Parkour parkour = (Parkour) attractionsController.findConserved(player, Parkour.TYPENAME);

            if (parkour == null) {
                player.sendMessage(Colors.color("&cNie konserwujesz zadnego parkour'a."));
                return;
            }

            String type = args[1];
            ItemStack hand = player.getInventory().getItemInMainHand();

            if (type.equalsIgnoreCase("spadek")) {
                parkour.setFallBlock(hand.getType());
                player.sendMessage(Colors.color("&aUstawiono blok spadku."));
            }

            else if (type.equalsIgnoreCase("checkpoint")) {
                parkour.setCheckpointBlock(hand.getType());
                player.sendMessage(Colors.color("&aDodano blok checkpoint'u."));
            }

            else if (type.equalsIgnoreCase("koniec")) {
                parkour.setEndBlock(hand.getType());
                player.sendMessage(Colors.color("&aUstawiono blok konca parkour'a."));
            }

            return;

        }
        else if (args[0].equalsIgnoreCase("opublikuj")) {

            Parkour parkour = (Parkour) attractionsController.findConserved(player, Parkour.TYPENAME);

            if (parkour == null) {
                player.sendMessage(Colors.color("&cNie konserwujesz zadnego parkour'a."));
                return;
            }

            Result result = parkour.test();

            if (result != Result.OK) {
                player.sendMessage(Colors.color("&c" + result.getMessage()));
                return;
            }

            parkour.setConserved(false);

            player.sendMessage(Colors.color("&aParkour zostal sprawdzony i opublikowany.\n" +
                    "&eMozna na niego wejsc komenda: /parkour dolacz " + parkour.getName()));

            return;

        }

        else if (args[0].equalsIgnoreCase("test")) {

            BlockDetector detector = Creative.getPlugin().getBlockDetector();
            DetectionRequest request = new DetectionRequest("game", player, Material.GRASS_BLOCK ,req -> {

               req.getPlayer().sendMessage("Nastapiles naa blok specjalny. " + req.getType().name());

            }, true);

            detector.define(request);

        }

    }
}
