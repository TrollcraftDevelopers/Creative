package pl.trollcraft.creative.core.controlling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller<T, Z> {

    protected List<T> instances;

    public Controller() {
        instances = new ArrayList<>();
    }

    public void register(T t) {
        instances.add(t);
    }

    public void unregister(T t) {
        instances.remove(t);
    }

    public T find(Z id) {

        Optional<T> opt = instances.stream()
                .filter(t -> t.equals(id))
                .findAny();

        if (opt.isPresent())
            return opt.get();

        return null;

    }

    public List<T> getInstances() {
        return instances;
    }

}
