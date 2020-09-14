package pl.trollcraft.creative.core.user;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GroupValues<T> {

    private class Value {
        private String permission;
        private T value;
    }

    private ArrayList<Value> values;

    public GroupValues() {
        values = new ArrayList<>();
    }

    public GroupValues add(String permission, T value) {
        Value v = new Value();
        v.permission = permission;
        v.value = value;
        values.add(v);
        return this;
    }

    public T resolve(Player player) {
        for (Value v : values)
            if (player.hasPermission(v.permission))
                return v.value;

        return values.get(values.size()-1).value;
    }

}
