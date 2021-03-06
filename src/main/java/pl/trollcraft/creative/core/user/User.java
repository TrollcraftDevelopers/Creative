package pl.trollcraft.creative.core.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
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

    @Nullable
    public<T extends UserComponent> T findComponent(String componentName) {

        Optional<UserComponent> opt = components.stream()
                .filter( comp -> comp.getName().equals(componentName) )
                .findAny();

        UserComponent comp = opt.orElse(null);

        if (comp == null) return null;
        else
            return (T) comp;

    }

    public boolean hasComponent(String componentName) {
        return components.stream().anyMatch(comp -> comp.getName().equals(componentName));
    }

    @Deprecated
    public UserComponent getComponent(String componentName) {
        Optional<UserComponent> opt = components.stream()
                .filter( comp -> comp.getName().equals(componentName) )
                .findAny();

        return opt.orElse(null);

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

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            String id = (String) obj;
            return name.equals(id);
        }
        return super.equals(obj);
    }
}
