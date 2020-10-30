package pl.trollcraft.creative.essentials.colors.data;

public class ChatColorData {

    private String permission;
    private String color;

    public ChatColorData(String permission, String color) {
        this.permission = permission;
        this.color = color;
    }

    public String getPermission() {
        return permission;
    }

    public String getColor() {
        return color;
    }
}
