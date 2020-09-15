package pl.trollcraft.creative.chat.blockades;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.chat.blockades.actions.BlockadeAction;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatBlockadesController extends Controller<ChatBlockade, String> {

    @Override
    public ChatBlockade find(String sentence) {
        
        Optional<ChatBlockade> opt = instances.stream()
                .filter( blockade -> {
                    Pattern pattern = Pattern.compile(blockade.getRegex());
                    Matcher matcher = pattern.matcher(sentence);
                    return matcher.find();
                }  )
                .findFirst();

        return opt.orElse(null);
    }

    public void load() {

        Creative creative = Creative.getPlugin();

        YamlConfiguration conf = Configs.load("chatblockades.yml");
        conf.getConfigurationSection("chatblockades").getKeys(false).forEach( id -> {

            String regex = conf.getString("chatblockades." + id + ".regex");
            BlockadeAction action = creative.getBlockadeActionsController().find(conf.getString("chatblockades." + id + ".action"));

            Bukkit.getConsoleSender().sendMessage(regex);

            register(new ChatBlockade(regex, action));
        } );

    }

}
