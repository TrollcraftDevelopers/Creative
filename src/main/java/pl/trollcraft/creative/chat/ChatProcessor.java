package pl.trollcraft.creative.chat;

import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.help.Colors;

public class ChatProcessor {

    public enum Response {
        DISABLED("&cChat jest wylaczony."),
        SAME_MSG("&cProsze nie powtarzac wiadomosci."),
        CHAT_OFF("&cTwoj chat jest wylaczony."),
        LOCKED("&cMozesz pisac co 2 sekundy."),
        CANNOT_WRITE("&cDolaczyles pierwszy raz - pisac bedziesz mogl po 5 minutach."),
        OK("OK");

        private String message;

        Response(String message){
            this.message = Colors.color(message);
        }

        public String getMessage() { return message; }
    }

    // -------- -------- -------- --------

    private static boolean chatEnabled = true;

    // -------- -------- -------- --------

    public static boolean isChatEnabled() { return chatEnabled; }
    public static void switchChat() { ChatProcessor.chatEnabled = !chatEnabled; }

    // -------- -------- -------- --------

    //public static String deflood(String message) { return message.replaceAll("(...+?)\\1+", "$1"); }

    public static Response process(Player player, String message) {
        if (player.hasPermission("creative.chat.admin")) return Response.OK;

        ChatProfile chatProfile = ChatProfile.get(player);

        if (!chatEnabled) return Response.DISABLED;

        if (!chatProfile.isAbleToWrite()) return Response.CANNOT_WRITE;
        if (!chatProfile.hasChatEnabled()) return Response.CHAT_OFF;
        if (chatProfile.changeLastMessage(message)) return Response.SAME_MSG;
        if (!chatProfile.canWrite() && !player.hasPermission("creative.chat.limit.bypass")) return Response.LOCKED;

        chatProfile.lockMessaging(2);
        return Response.OK;
    }

}
