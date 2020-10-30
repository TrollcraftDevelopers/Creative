package pl.trollcraft.creative.core.reloading;

import pl.trollcraft.creative.core.controlling.Controller;

public class ReloadsController extends Controller<Reloadable, String> {

    @Override
    public Reloadable find(String id) {
        return instances.stream()
                .filter(r -> r.name().equals(id))
                .findAny()
                .orElse(null);
    }

}
