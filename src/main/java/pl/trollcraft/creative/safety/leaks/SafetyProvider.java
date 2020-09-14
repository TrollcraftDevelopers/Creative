package pl.trollcraft.creative.safety.leaks;

public interface SafetyProvider {

    int getId();
    SafetyLeak scan();

}
