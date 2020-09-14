package pl.trollcraft.creative.prefixes.gui;

public class Pos {

    int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos translated(int x, int y) {
        return new Pos(this.x + x, this.y + y);
    }

    public int slot() {
        return y*9+x;
    }

}
