package pl.trollcraft.creative.safety.worldedit;

public class Argument {

    private int order;
    private String[] forbidden;

    public Argument(int order, String[] forbidden) {
        this.order = order;
        this.forbidden = forbidden;
    }

    public int getOrder() {
        return order;
    }

    public String getForbidden() {
        StringBuilder builder = new StringBuilder();
        for (String s : forbidden)
            builder.append(s + ", ");
        return builder.toString();
    }

    public boolean containsForbidden(String arg) {
        for (String s : forbidden)
            if (arg.contains(s))
                return true;
        return false;
    }

}
