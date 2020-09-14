package pl.trollcraft.creative.services;

import org.bukkit.entity.Player;

public interface Service {

    String getId();
    String getName();
    String getGroup();

    boolean isAllowedFor(Player player);
    void allow(Player player);

    void serve(Player player);

}
