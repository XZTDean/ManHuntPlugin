package me.deanx.manhunt.command;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

public class ManHunt implements CommandExecutor {
    public ManHunt(ManHuntPlugin plugin) {
        plugin.getCommand("ManHunt").setExecutor(this);
    }
    private Player runner;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String ret = "";
        if (args[0].equalsIgnoreCase("start")) {
            ret = start();
        } else if (args[0].equalsIgnoreCase("stop")) {
            runner = null;
        } else {
            ret = labelPlayer(args[0]);
        }
        if (!ret.isEmpty()) {
            sender.sendMessage(ret);
        }
        return true;
    }

    private String start() {
        if (runner == null) {
            return "Please set the runner first";
        }
        runner.getWorld().setTime(1000);
        setRunner();
        Player[] playerList = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        for (Player p : playerList) {
            if (p != runner) {
                setHunter(p);
            }
        }
        return "";
    }

    private void setRunner() {
        runner.getInventory().clear();
        runner.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        runner.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        runner.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        runner.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        runner.getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
        runner.getInventory().addItem(new ItemStack(Material.OAK_LOG, 5));
        setInitialState(runner);
        runner.sendMessage("You are Runner. RUN!");
        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runner.sendMessage("Hunter start hunting now.");
        });
    }

    private void setHunter(Player hunter) {
        hunter.getInventory().clear();
        hunter.getInventory().addItem(new ItemStack(Material.COMPASS));
        hunter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 0));
        hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 300, 128));
        hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 128));
        hunter.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 300, 128));
        hunter.setBedSpawnLocation(runner.getLocation());
        setInitialState(hunter);
        hunter.sendMessage("You are hunter, please wait for 15s.");
        new Thread(() -> {
            waitingCountdown(hunter, 15);
        }).start();
    }

    private void waitingCountdown(Player player, int time) {
        for (int i = time; i > 0; i--) {
            player.sendTitle(String.valueOf(i), "", 2, 16, 2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        player.sendTitle("Go!", "", 2, 16, 2);
    }

    private void setInitialState(Player player) {
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setSaturation(5);
        player.setGameMode(GameMode.SURVIVAL);
        // reset advancement
        for(Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator(); iterator.hasNext();) {
            AdvancementProgress progress = player.getAdvancementProgress(iterator.next());
            for (String criteria : progress.getAwardedCriteria())
                progress.revokeCriteria(criteria);
        }

    }

    private String labelPlayer(String name) {
        Player p = getPlayer(name);
        if (p != null) {
            runner = p;
            return "";
        } else {
            return "Can't find player " + name;
        }
    }

    private Player getPlayer(String name) {
        Player[] playerList = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
