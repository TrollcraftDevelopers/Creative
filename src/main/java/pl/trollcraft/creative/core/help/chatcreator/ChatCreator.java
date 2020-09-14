package pl.trollcraft.creative.core.help.chatcreator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

public class ChatCreator implements Listener {

    private ArrayList<Rule> rules;

    public ChatCreator() {
        rules = new ArrayList<>();
    }

    public Rule createRule(Player player) {

        String name = player.getName();

        Rule rule = find(player);
        if (rule != null)
            throw new IllegalStateException(
                    String.format("Player %s already has defined rule.", name));

        rule = new Rule(name);
        rules.add(rule);

        return rule;
    }

    private Rule find(Player player) {

        String name = player.getName();

        Optional<Rule> opt = rules.stream()
                .filter(rule -> rule.getPlayerName().equals(name))
                .findAny();

        return opt.orElse(null);
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Rule rule = find(player);

        if (rule != null) {

            if (rule.cancelsEvent())
                event.setCancelled(true);

            rule.getTask().accept(event.getMessage());

            if (!rule.isPersistent())
                rules.remove(rule);

        }

    }


}
