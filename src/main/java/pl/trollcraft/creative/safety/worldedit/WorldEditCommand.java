package pl.trollcraft.creative.safety.worldedit;

import pl.trollcraft.creative.core.user.GroupValues;

import java.util.ArrayList;

public class WorldEditCommand {

    private String name;
    private String[] aliases;

    private ArrayList<Argument> arguments;

    private GroupValues<Selection> maxSelections;
    private GroupValues<Integer> cooldown;
    private GroupValues<Integer> available;

    public WorldEditCommand(String name, String[] aliases, ArrayList<Argument> arguments,
                            GroupValues<Selection> maxSelections,
                            GroupValues<Integer> cooldown, GroupValues<Integer> available) {
        this.name = name;
        this.aliases = aliases;
        this.arguments = arguments;
        this.maxSelections = maxSelections;
        this.cooldown = cooldown;
        this.available = available;
    }

    // -------- -------- -------- --------

    public String getName() {
        return name;
    }

    public String getAliases() {
        StringBuilder builder = new StringBuilder();
        for (String s : aliases)
            builder.append(s + ", ");
        return builder.toString();
    }

    public Argument getArgument(int order) {
        for (Argument arg : arguments)
            if (arg.getOrder() == order)
                return arg;
        return null;
    }

    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    public GroupValues<Selection> getMaxSelections() {
        return maxSelections;
    }

    public GroupValues<Integer> getCooldown() {
        return cooldown;
    }

    public GroupValues<Integer> getAvailable() {
        return available;
    }

    // -------- -------- -------- --------

    @Override
    public boolean equals(Object obj) {
        String[] command = ((String) obj).split(" ");

        if (command[0].equalsIgnoreCase(name)) return true;
        else {
            for (String s : aliases)
                if (s.equalsIgnoreCase(command[0]))
                    return true;
            return false;
        }
    }
}
