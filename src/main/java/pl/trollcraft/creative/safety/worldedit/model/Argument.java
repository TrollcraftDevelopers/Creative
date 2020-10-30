package pl.trollcraft.creative.safety.worldedit.model;

import org.bukkit.Bukkit;
import pl.trollcraft.creative.safety.limits.file.LimitFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class Argument {

    private int order;
    private List<LimitFile> limits;

    public Argument(int order) {
        this.order = order;
        limits = new ArrayList<>();
    }

    public List<LimitFile> getLimits() {
        return limits;
    }

    public void addLimit(LimitFile limitFile) {
        limits.add(limitFile);
    }

    public int getOrder() {
        return order;
    }

    /**
     * Checks if
     *
     * @param arg the argument the player provided.
     * @return boolean indicating whether arg is forbidden.
     */
    public boolean forbidden(String arg) {

        String[] args = arg.split(",");
        int lim;

        for (String a : args) {

            // 5:3%76
            if (a.contains("%")) {

                // 0 - , 1 - percent
                String[] exp = a.split("%");
                String item = exp[0];

                // 5:3%2
                if (item.contains(":")) {

                    exp = item.split(":");
                    String name = exp[0];
                    String data = exp[1];

                    Bukkit.getLogger().log(Level.INFO, "5:3%2");
                    lim = findLimit(name, data);

                }

                // 5%3
                else {
                    lim = findLimit(item, null);
                    Bukkit.getLogger().log(Level.INFO, "5%3");
                }
                if (lim != -1)
                    return true;

            }

            // 5:3 or 5
            else {

                if (a.contains(":")) {
                    String[] exp = a.split(":");
                    String name = exp[0];
                    String data = exp[1];

                    Bukkit.getLogger().log(Level.INFO, "5:3");
                    lim = findLimit(name, data);
                }

                else {
                    Bukkit.getLogger().log(Level.INFO, "5");
                    lim = findLimit(a, null);
                }

                if (lim != -1)
                    return true;

            }

        }

        return false;

    }

    private int findLimit(String name, String data) {

        Optional<LimitFile.Limit> lim;
        for (LimitFile file : limits) {

            if (data == null) {
                lim = file.getLimits()
                        .stream()
                        .filter(l -> l.name.equalsIgnoreCase(name))
                        .filter(l -> l.data == null)
                        .findAny();

            }
            else {

                lim = file.getLimits()
                        .stream()
                        .filter(l -> l.name.equalsIgnoreCase(name))
                        .filter(l -> l.data == null || l.data.equalsIgnoreCase(data))
                        .findAny();
            }

            if (lim.isPresent())
                return lim.get().max;

        }

        return -1;

    }

}
