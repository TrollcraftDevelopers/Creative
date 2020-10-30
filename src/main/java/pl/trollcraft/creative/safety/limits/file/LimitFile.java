package pl.trollcraft.creative.safety.limits.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LimitFile {

    public class Limit {
        public String name;
        public String data;
        public int max;
    }

    private String id;
    private List<Limit> limits;

    public LimitFile (File file) {

        if (!file.exists())
            throw new IllegalStateException("File does not exists.");

        id = file.getName();
        limits = new ArrayList<>();

        try {

            Scanner s = new Scanner(new FileInputStream(file));
            String[] line;

            while (s.hasNext()) {

                line = s.nextLine().split(",");
                Arrays.stream(line)
                        .map( el -> {

                            if (el.contains("#")) {

                                String[] exp = el.split("#");

                                String[] data = exp[0].split(":");
                                Limit l = new Limit();

                                l.max = Integer.parseInt(exp[1]);
                                l.name = data[0];
                                if (data.length > 1)
                                    l.data = data[1];

                                return l;

                            }
                            else {

                                String[] data = el.split(":");
                                Limit l = new Limit();

                                l.max = 0;
                                l.name = data[0];
                                if (data.length > 1)
                                    l.data = data[1];

                                return l;

                            }



                        } )
                        .forEach( l -> limits.add(l) );

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getId() {
        return id;
    }

    public List<Limit> getLimits() {
        return limits;
    }
}
