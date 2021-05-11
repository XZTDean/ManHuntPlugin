package me.deanx.manhunt;

import me.deanx.manhunt.command.ManHunt;
import me.deanx.manhunt.command.ManHuntTabCompleter;
import me.deanx.manhunt.interfaces.CompassNBT;
import me.deanx.manhunt.listener.DropItem;
import me.deanx.manhunt.listener.Respawn;
import me.deanx.manhunt.listener.Result;
import me.deanx.manhunt.listener.RunnerLocation;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ManHuntPlugin extends JavaPlugin {
    private Player runner;
    private boolean status;
    private CompassNBT compassNBT;
    private final List<Player> hunters = new ArrayList<>();

    @Override
    public void onEnable() {
        getLogger().info("ManHunt plugin start");
        compassNBT = CompassNBT.getInstance();
        new ManHunt(this);
        new ManHuntTabCompleter(this);
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

    public void registerListener() {
        new Respawn(this);
        new Result(this);
        new RunnerLocation(this);
        new DropItem(this);
        status = true;
    }

    public void endGame() {
        runner = null;
        hunters.clear();
        HandlerList.unregisterAll(this);
        status = false;
    }

    public boolean isStart() {
        return status;
    }

    public void runnerLose() {
        runner.playSound(runner.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 1, 1);
        runner.sendTitle("You Lose", "", 20, 40, 20);
        for (Player hunter : hunters) {
            hunter.playSound(hunter.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            hunter.sendTitle("You Win", "Runner is killed", 20, 40, 20);
        }
        endGame();
    }

    public void runnerWin() {
        runner.playSound(runner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        runner.sendTitle("You Win", "", 20, 40, 20);
        for (Player hunter : hunters) {
            hunter.playSound(hunter.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 1, 1);
            hunter.sendTitle("You Lose", "Runner Entered Nether", 20, 40, 20);
        }
        endGame();
    }

    public void updateCompass() {
        compassNBT.updateCompass(runner);
        for (Player hunter : hunters) {
            compassNBT.updateInventory(hunter);
        }
    }
}
