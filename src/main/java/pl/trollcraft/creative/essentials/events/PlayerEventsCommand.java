package pl.trollcraft.creative.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.core.help.chatcreator.ChatCreator;
import pl.trollcraft.creative.core.help.chatcreator.Rule;

public class PlayerEventsCommand extends CommandController {

    private Creative plugin = Creative.getPlugin();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Colors.color("&aEventy"));
            player.sendMessage("");
            player.sendMessage(Colors.color("&e/event nowy &7- tworzy nowy event,"));
            player.sendMessage(Colors.color("&e/event zakoncz &7- konczy Twoj event,"));
            player.sendMessage("");
            player.sendMessage(Colors.color("&e/event dolacz <gracz> &7- dolacza do event'u."));
            player.sendMessage(Colors.color("&e/event opusc &7- opuszcza obecny event."));
            return;
        }

        else {

            if (args[0].equalsIgnoreCase("nowy")){

                PlayerEventsController controller
                        = plugin.getPlayerEventsController();

                if (controller.participates(player)) {
                    player.sendMessage(Colors.color("&cBierzesz udzial w event'cie."));
                    return;
                }

                long time = controller.canOrganize(player);
                if (time > 0) {
                    player.sendMessage(Colors.color("&cKolejny event bedziesz mogl zorganizowac " +
                            "za &e" + Help.parseTime(time) + "."));
                    return;
                }

                player.sendMessage(Colors.color("&aWprowadz nazwe event'u lub wpisz 'anuluj'," +
                        "by anulowac tworzenie."));

                ChatCreator chatCreator = plugin.getChatCreator();

                Rule rule = chatCreator.createRule(player);
                rule.setCancelsEvent(true);
                rule.setTask(message -> {
                    if (message.equalsIgnoreCase("anuluj")) {
                        player.sendMessage(Colors.color("&7Anulowano tworzenie event'u."));
                        return;
                    }

                    controller.organize(player, message);
                    player.sendMessage(Colors.color("&aEvent zostal utworzony!\n&ePotrwa 5 minut."));
                });

            }

            if (args[0].equalsIgnoreCase("zakoncz")) {

                PlayerEventsController controller
                        = plugin.getPlayerEventsController();

                PlayerEvent event = controller.findByOwner(player);

                if (event == null) {
                    player.sendMessage(Colors.color("&cNie prowadzisz event'u."));
                    return;
                }

                controller.finish(event);

            }

            if (args[0].equalsIgnoreCase("dolacz")) {

                if (args.length != 2) {
                    player.sendMessage(Colors.color("&eUzycie: &7/event dolacz <gracz>"));
                    return;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Colors.color("&cNie mozesz dolaczyc do swojego event'u."));
                    return;
                }

                PlayerEventsController controller
                        = plugin.getPlayerEventsController();

                Player owner = Bukkit.getPlayer(args[1]);

                if (owner == null || !owner.isOnline()){
                    player.sendMessage(Colors.color("&cBrak gracza na serwerze."));
                    return;
                }

                if (controller.participates(player)) {
                    player.sendMessage(Colors.color("&cBierzesz juz udzial w event'cie."));
                    return;
                }

                PlayerEvent playerEvent = controller.findByOwner(owner);

                if (playerEvent == null) {
                    player.sendMessage(Colors.color("&cGracz nie prowadzi event'u."));
                    return;
                }

                if (!playerEvent.join(player)) {
                    player.sendMessage(Colors.color("&cBierzesz juz udzial w tym event'cie."));
                    return;
                }

                player.sendMessage(Colors.color("&aDolaczyles do event'u gracza &e" + owner.getName()));

            }

            if (args[0].equalsIgnoreCase("opusc")) {

                PlayerEventsController controller
                        = plugin.getPlayerEventsController();

                if (controller.findByOwner(player) != null) {
                    player.sendMessage(Colors.color("&cJestes prowadzacym event. Mozesz go zakonczyc komenda" +
                            "&e/event zakoncz."));
                    return;
                }

                if (controller.leave(player))
                    player.sendMessage(Colors.color("&aOpusciles event pomyslnie."));
                else
                    player.sendMessage(Colors.color("&cNie bierzesz udzialu w zadnym event'cie."));



            }

            if (args[0].equalsIgnoreCase("debug")) {

                PlayerEventsController controller
                        = plugin.getPlayerEventsController();

                String debug = controller.debug();

                player.sendMessage(Colors.color("&7" + debug));

            }

        }

    }

}
