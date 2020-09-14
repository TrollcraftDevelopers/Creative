package pl.trollcraft.creative.services;

import java.util.ArrayList;
import java.util.Optional;

public class ServiceManager {

    private static ArrayList<Service> services = new ArrayList<>();

    public static void register(Service service) {
        services.add(service);
    }

    public static Service find(String group, String name) {

        Optional<Service> res = services.stream()
                .filter(service -> service.getGroup().equals(group))
                .filter(service -> service.getId().equals(name))
                .findFirst();

        if (res.isPresent())
            return res.get();

        return null;

    }

}
