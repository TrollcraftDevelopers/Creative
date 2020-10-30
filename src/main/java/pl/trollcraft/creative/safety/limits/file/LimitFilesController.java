package pl.trollcraft.creative.safety.limits.file;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;

import java.io.File;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class LimitFilesController extends Controller<LimitFile, String> {

    /**
     * Register holding all links directions.
     */
    private Multimap<String, LimitFile> registers;

    @Override
    public LimitFile find(String id) {
        return instances.stream()
                 .filter( limitFile -> limitFile.getId().equals(id) )
                 .findAny()
                 .orElse(null);
    }

    /**
     * Loads file to memory.
     */
    public void load() {

        File dir = new File(Creative.getPlugin().getDataFolder(), "limits");
        LimitFile limitFile;

        for (File f : Objects.requireNonNull(dir.listFiles())) {
            limitFile = new LimitFile(f);
            register(limitFile);
            Bukkit.getLogger().log(Level.INFO, "Loaded limit file " + limitFile.getId());
        }

    }

    /**
     * Load limits configuration file.
     */
    public void configure() {

        YamlConfiguration conf = Configs.load("limits.yml");
        assert conf != null;

        registers = ArrayListMultimap.create();

        conf.getStringList("limits.link")
                .stream()
                .map( str -> str.replaceAll(" ", "").split(">") )
                .forEach( arr -> registers.put(arr[1], find(arr[0])) );

        Bukkit.getLogger().log(Level.INFO, "Configured limits registers system.");

    }

    /**
     * Returns files linked to the register.
     *
     * @param register to get files from.
     * @return files.
     */
    public Collection<LimitFile> get(String register) {
        return registers.get(register);
    }

    public int findInRegister(String register, Material type) {

        String name = type.name();
        Optional<LimitFile.Limit> am;

        for (LimitFile f : registers.get(register)) {

            am = f.getLimits()
                  .stream()
                  .filter(l -> l.name.equalsIgnoreCase(name))
                  .findAny();

            if (am.isPresent())
                return am.get().max;

        }

        return -1;

    }

    /**
     * Gets registers available.
     *
     * @return set of names.
     */
    public Set<String> getRegisters() {
        return registers.keySet();
    }

}
