package me.deanx.manhunt.listener;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RunnerLocation implements Listener {
    private final ManHuntPlugin plugin;

    public RunnerLocation(ManHuntPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRunnerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player == plugin.getRunner() && event.getFrom().getBlock().equals(event.getTo().getBlock())) {
            plugin.updateCompass();
        }
    }
}
