package pl.trollcraft.creative.prefixes.obj;

public class Prefix {

    private String id;
    private String name;
    private PrefixType type;
    private double price;

    /**
     * Is prefix available to
     * be bought;
     */
    private boolean available;

    public Prefix(String id, String name, PrefixType type, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PrefixType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object obj) {
        String id = (String) obj;
        return this.id.equals(id);
    }

}
