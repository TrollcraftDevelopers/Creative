package pl.trollcraft.creative.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {

    private String name;
    private List<UserComponent> components;

    public User(String name) {
        this.name = name;
        components = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addComponent(UserComponent component) {
        components.add(component);
    }

    public UserComponent getComponent(String componentName) {
        Optional<UserComponent> opt = components.stream()
                .filter( comp -> comp.getName().equals(componentName) )
                .findAny();

        if (opt.isPresent())
            return opt.get();

        return null;
    }

    public List<UserComponent> getComponents() {
        return components;
    }

    public List<String> getComponentsClasses() {
        ArrayList<String> components = new ArrayList<>();
        for (UserComponent component : this.components)
            components.add(component.getClass().getName());
        return components;
    }

    public boolean isEmpty() {
        if (components.isEmpty()) return true;

        for (UserComponent component : components)
            if (!component.isEmpty()) return false;

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            String id = (String) obj;
            return name.equals(id);
        }
        return false;
    }
}
