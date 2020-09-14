package pl.trollcraft.creative.chat.blockades;

import pl.trollcraft.creative.chat.blockades.actions.BlockadeAction;

public class ChatBlockade {

    private String regex;
    private BlockadeAction action;

    public ChatBlockade(String regex, BlockadeAction action) {
        this.regex = regex;
        this.action = action;
    }

    public String getRegex() {
        return regex;
    }

    public BlockadeAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object obj) {
        String sentence = (String) obj;
        if (sentence.contains(regex))
            return true;
        else return false;
    }
}
