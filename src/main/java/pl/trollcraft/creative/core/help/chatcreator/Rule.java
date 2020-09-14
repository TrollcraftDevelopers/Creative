package pl.trollcraft.creative.core.help.chatcreator;

import java.util.function.Consumer;

public class Rule {

    private String playerName;
    private int tempCode;

    private boolean persistent;
    private boolean cancelEvent;

    public Consumer<String> task;

    public Rule(String playerName) {
        this.playerName = playerName;
        tempCode = (int) Math.random() * 100000;
        persistent = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setTask(Consumer<String> task) {
        this.task = task;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public boolean cancelsEvent() {
        return cancelEvent;
    }

    public void setCancelsEvent(boolean cancelsEvent){
        cancelEvent = cancelsEvent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public Consumer<String> getTask() {
        return task;
    }
}
