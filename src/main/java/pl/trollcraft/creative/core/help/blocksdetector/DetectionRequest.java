package pl.trollcraft.creative.core.help.blocksdetector;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class DetectionRequest {

    private String tag;
    private Player player;
    private Material type;
    private Consumer<DetectionRequest> task;
    private Object data;
    private boolean ignore;

    public DetectionRequest(String tag, Player player, Material type, Consumer<DetectionRequest> task, boolean ignore) {
        this.tag = tag;
        this.player = player;
        this.type = type;
        this.task = task;
        this.ignore = ignore;
    }

    public String getTag() {
        return tag;
    }

    public Player getPlayer() {
        return player;
    }

    public Material getType() {
        return type;
    }

    public Consumer<DetectionRequest> getTask() {
        return task;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean shouldBeIgnored() {
        return ignore;
    }
}
