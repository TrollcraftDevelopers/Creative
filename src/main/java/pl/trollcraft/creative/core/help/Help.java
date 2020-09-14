package pl.trollcraft.creative.core.help;

import org.bukkit.Material;

public class Help {

    public static int isInteger(String a, int errNum) {
        int n;
        try {
            n = Integer.parseInt(a);
        }
        catch (NumberFormatException e) {
            return errNum;
        }
        return n;
    }

    public static boolean isSign(Material type) {
        return type.name().contains("SIGN");
    }

    public static int getSlots(int s) {

        int mul;

        // Maximum is 6 because 9 x 6 = 54
        // where 54 is maximum slots amount in
        // GUI.
        for (int n = 1 ; n < 6 ; n++) {

            mul = n*9;
            if (mul - s > 0)
                return mul;

        }

        return 54;

    }

    public static boolean isInArray(String[] array, String string) {
        for (String s : array)
            if (s.equalsIgnoreCase(string)) return true;
        return false;
    }

    public static String parseTime(long time) {
        long minutes = time / 1000 / 60;
        long seconds = time / 1000 % 60;

        String min = "", sec = "";
        if (minutes < 10)
            min += "0";
        if (seconds < 10)
            sec += "0";

        min += minutes;
        sec += seconds;

        return min + ":" + sec;
    }

}
