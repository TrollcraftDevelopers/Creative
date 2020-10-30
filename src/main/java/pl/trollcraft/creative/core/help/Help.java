package pl.trollcraft.creative.core.help;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

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

    public static double isDouble(String a, double errNum) {
        double n;
        try {
            n = Double.parseDouble(a);
        }
        catch (NumberFormatException e) {
            return errNum;
        }
        return n;
    }

    public static long isLong(String a, long errNum) {
        long n;
        try {
            n = Long.parseLong(a);
        }
        catch (NumberFormatException e) {
            return errNum;
        }
        return n;
    }

    public static float isFloat(String a, float errNum) {
        float n;
        try {
            n = Float.parseFloat(a);
        }
        catch (NumberFormatException e) {
            return errNum;
        }
        return n;
    }

    public static byte isByte(String a, byte errNum) {
        byte n;
        try {
            n = Byte.parseByte(a);
        }
        catch (NumberFormatException e) {
            return errNum;
        }
        return n;
    }

    // ----

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
            if (string.contains(s)) return true;
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

    public static String locationToString(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    public static Location stringToLocation(String location) {
        String[] data = location.split(":");
        World world = Bukkit.getWorld(data[0]);
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

}
