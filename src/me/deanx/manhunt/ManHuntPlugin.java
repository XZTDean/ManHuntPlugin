package me.deanx.manhunt;

import me.deanx.manhunt.command.ManHunt;
import me.deanx.manhunt.listener.Respawn;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ManHuntPlugin extends JavaPlugin {
    private Player runner;
    private List<Player> hunters;

    @Override
    public void onEnable() {
        getLogger().info("ManHunt plugin start");
        new ManHunt(this);
        new Respawn(this);
    }

    @Override
    public void onDisable() {
    }

    public Player getRunner() {
        return runner;
    }

    public void setRunner(Player runner) {
        this.runner = runner;
    }

    public boolean isHunter(Player player) {
        return hunters.contains(player);
    }

    public void addHunter(Player hunter) {
        if (!isHunter(hunter)) {
            this.hunters.add(hunter);
        }
    }

    public void removeHunter(Player hunter) {
        this.hunters.remove(hunter);
    }

    public void newGame() {
        runner = null;
        hunters.clear();
    }

    public void runnerLost() {
        // Wait for implements
    }

    public void runnerWin() {
        // Wait for implements
    }
}
