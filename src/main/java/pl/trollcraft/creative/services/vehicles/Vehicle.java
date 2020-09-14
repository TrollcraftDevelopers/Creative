package pl.trollcraft.creative.services.vehicles;

import es.pollitoyeye.vehicles.VehiclesMain;
import es.pollitoyeye.vehicles.enums.VehicleType;
import es.pollitoyeye.vehicles.interfaces.VehicleSubType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.services.Service;

public class Vehicle implements Service {

    private static final String GROUP_NAME = "vehicles";

    private String id;

    private String type;
    private String subType;

    private String name;

    public Vehicle(String id, String type, String subType, String name) {
        this.id = id;
        this.type = type;
        this.subType = subType;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGroup() {
        return GROUP_NAME;
    }

    @Override
    public boolean isAllowedFor(Player player) {
        String name = player.getName();
        UsersController usersController = Creative.getPlugin().getUserController();
        User user = usersController.find(name);
        VehiclesComponent vehiclesComponent = (VehiclesComponent) user.getComponent(VehiclesComponent.COMP_NAME);
        return vehiclesComponent.getVehiclesAvailable().contains(id);
    }

    @Override
    public void allow(Player player) {
        String name = player.getName();
        UsersController usersController = Creative.getPlugin().getUserController();
        User user = usersController.find(name);
        VehiclesComponent vehiclesComponent = (VehiclesComponent) user.getComponent(VehiclesComponent.COMP_NAME);
        vehiclesComponent.addVehicle(id);
    }

    @Override
    public void serve(Player player) {
        VehicleType type = VehicleType.valueOf(this.type.toUpperCase());
        VehicleSubType subType =  getVehicleSubType(type, this.subType); //main.vehicleSubTypeFromString(type, this.subType);
        type.getVehicleManager().giveItem(player, subType.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String)
            return ((String) obj).equals(id);
        return false;
    }

    private static VehicleSubType getVehicleSubType(VehicleType type, String subType) {

        for (VehicleSubType st : VehiclesMain.getPlugin().vehicleSubTypesMap.get(type)){

            Bukkit.getConsoleSender().sendMessage(st.getName() + " ?= " + subType);
            if (st.getName().equals(subType))
                return st;

        }

        return null;
    }

}
