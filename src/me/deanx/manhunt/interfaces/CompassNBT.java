package me.deanx.manhunt.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface CompassNBT {
    void updateCompass(Player runner);
    void updateInventory(Player player);
    static CompassNBT newInstance() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        switch (version) {
            case "v1_16_R3":
                return new CompassNBT_v1_16_R3();
            default:
                throw new IllegalStateException("Minecraft Version is not accepted.");
        }
    }
}
