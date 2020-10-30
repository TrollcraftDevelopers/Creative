package pl.trollcraft.creative.safety.worldedit.model;

public class Selection {

    private int x, y, z;

    public Selection (String s) {
        String[] values = s.split(",");
        x = Integer.parseInt(values[0].trim());
        y = Integer.parseInt(values[0].trim());
        z = Integer.parseInt(values[0].trim());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public long getVolume() {
        return x*y*z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
