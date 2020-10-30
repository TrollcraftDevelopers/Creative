package pl.trollcraft.creative.essentials.colors.data;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.List;
import java.util.stream.Collectors;

public class ChatColorDataController extends Controller<ChatColorData, String> {

    @Override
    public ChatColorData find(String color) {
        return instances.stream()
                .filter( col -> col.getColor().equals(color) )
                .findFirst()
                .orElse(null);
    }

    public List<ChatColorData> findAll(Player player) {
        return instances.stream()
                .filter( col -> player.hasPermission(col.getPermission()) )
                .collect(Collectors.toList());
    }

    public String getRegex(Player player) {
        StringBuilder builder = new StringBuilder("/");
        instances.stream()
                .filter(col -> !player.hasPermission(col.getPermission()))
                .forEach(col -> builder.append(col.getColor()).append("|"));

        int ind = builder.lastIndexOf("/");
        builder.replace(ind, ind+1, "/g");
        return builder.toString();
    }

    @Override
    public List<ChatColorData> getInstances() {
        return super.getInstances();
    }

    public void load() {
        register(new ChatColorData("creative.color.vip", "&1"));
        register(new ChatColorData("creative.color.vip", "&2"));
        register(new ChatColorData("creative.color.vip", "&3"));
        register(new ChatColorData("creative.color.vip", "&5"));
        register(new ChatColorData("creative.color.vip", "&6"));
        register(new ChatColorData("creative.color.vip", "&7"));
        register(new ChatColorData("creative.color.vip", "&8"));
        register(new ChatColorData("creative.color.vip", "&9"));
        register(new ChatColorData("creative.color.vip", "&a"));
        register(new ChatColorData("creative.color.vip", "&b"));
        register(new ChatColorData("creative.color.vip", "&d"));
        register(new ChatColorData("creative.color.vip", "&e"));
        register(new ChatColorData("creative.color.vip", "&f"));

        register(new ChatColorData("creative.color.mvip", "&n"));
        register(new ChatColorData("creative.color.mvip", "&o"));

        register(new ChatColorData("creative.color.admin", "&4"));
        register(new ChatColorData("creative.color.admin", "&c"));
        register(new ChatColorData("creative.color.admin", "&m"));
        register(new ChatColorData("creative.color.admin", "&k"));
        register(new ChatColorData("creative.color.admin", "&r"));
        register(new ChatColorData("creative.color.admin", "&l"));
    }

}
