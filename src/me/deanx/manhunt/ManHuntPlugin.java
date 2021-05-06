package me.deanx.manhunt;

import me.deanx.manhunt.command.ManHunt;
import me.deanx.manhunt.listener.Respawn;
import org.bukkit.plugin.java.JavaPlugin;

public class ManHuntPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("ManHunt plugin start");
        new ManHunt(this);
        new Respawn(this);
    }

    @Override
    public void onDisable() {
    }
}
