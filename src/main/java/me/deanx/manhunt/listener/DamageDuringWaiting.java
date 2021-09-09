package me.deanx.manhunt.listener;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageDuringWaiting implements Listener {
    private final ManHuntPlugin plugin;

    public DamageDuringWaiting(ManHuntPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (plugin.isHunter(player)) {
                event.setCancelled(true);
            } else {
                // Only damage from player to runner will be cancelled.
                Entity damager = event.getDamager();
                if (damager instanceof Player) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
