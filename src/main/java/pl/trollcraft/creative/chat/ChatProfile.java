package pl.trollcraft.creative.chat;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ChatProfile {

    private static HashMap<Integer, ChatProfile> profiles = new HashMap<>();
    private static HashSet<Player> chatOff = new HashSet<>();

    private Player player;
    private boolean chatEnabled;
    private String lastMessage;
    private long lockedMessagingTill;
    private List<String> ignored;
    private long cannotWriteSince;

    public ChatProfile (Player player, boolean chatEnabled, List<String> ignored) {
        this.player = player;
        this.chatEnabled = chatEnabled;
        lastMessage = null;
        lockedMessagingTill = 0;
        this.ignored = ignored;
        profiles.put(player.getEntityId(), this);
    }

    public void blockWriting() {
        cannotWriteSince = System.currentTimeMillis();
    }

    public boolean isAbleToWrite() {
        if (cannotWriteSince == -1 || cannotWriteSince == 0)
            return true;

        if (System.currentTimeMillis() >= cannotWriteSince + 5*1000*60) {
            cannotWriteSince = -1;
            return true;
        }

        return false;
    }

    public void setCannotWriteSince(long cannotWriteSince) {
        this.cannotWriteSince = cannotWriteSince;
    }

    // -------- -------- -------- -------- --------

    public boolean hasChatEnabled() { return chatEnabled; }
    public boolean canWrite() { return lockedMessagingTill == 0 || System.currentTimeMillis() > lockedMessagingTill; }

    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
        if (chatEnabled)
            chatOff.remove(player);
        else
            chatOff.add(player);
    }

    public boolean changeLastMessage(String lastMessage) {
        boolean same = false;

        if (lastMessage != null && lastMessage.equalsIgnoreCase(this.lastMessage)) same = true;
        else this.lastMessage = lastMessage;

        return same;
    }

    public void lockMessaging(long seconds) { lockedMessagingTill = System.currentTimeMillis() + 1000L * seconds; }

    public List<String> getIgnored() {
        return ignored;
    }

    public void save() {
        new BukkitRunnable() {

            @Override
            public void run() {
                YamlConfiguration conf = Configs.load("chatprofiles.yml");
                conf.set("chatprofiles." + player.getName() + ".chatEnabled", chatEnabled);
                conf.set("chatprofiles." + player.getName() + ".ignored", ignored);

                if (cannotWriteSince != -1 && cannotWriteSince != 0)
                    conf.set("chatprofiles." + player.getName() + ".cannotWriteSince", cannotWriteSince);
                else
                    conf.set("chatprofiles." + player.getName() + ".cannotWriteSince", null);

                Configs.save(conf, "chatprofiles.yml");

            }

        }.runTaskAsynchronously(Creative.getPlugin());
    }

    public Player getPlayer() {
        return player;
    }

    // -------- -------- -------- -------- --------

    public static HashSet<Player> getChatOff() { return chatOff; }

    public static ChatProfile get(Player player) {
        return profiles.get(player.getEntityId());
    }

    public static HashMap<Integer, ChatProfile> getProfiles() {
        return profiles;
    }

    // -------- -------- -------- -------- --------

    public static void load(Player player) {

        boolean chatEnabled;
        List<String> ignored;

        String name = player.getName();
        YamlConfiguration conf = Configs.load("chatprofiles.yml");

        if (conf.contains("chatprofiles." + name)) {
            chatEnabled = conf.getBoolean("chatprofiles." + name + ".chatEnabled");

            if (conf.contains("chatprofiles." + name + ".ignored"))
                ignored = conf.getStringList("chatprofiles." + name + ".ignored");
            else
                ignored = new ArrayList<>();

            ChatProfile profile = new ChatProfile(player, chatEnabled, ignored);

            if (conf.contains("chatprofiles." + name + ".cannotWriteSince"))
                profile.setCannotWriteSince(conf.getLong("chatprofiles." + name + ".cannotWriteSince"));
            else
                profile.setCannotWriteSince(-1);

        }
        else {
            new ChatProfile(player, true, new ArrayList<>()).blockWriting();
            player.sendMessage(Colors.color("&7Pisac bedziesz mogl za &e5 minut z powodu wejsca po raz pierwszy."));
        }

    }

}
