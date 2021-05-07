package me.deanx.manhunt.command;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        runner.getInventory().clear();
        runner.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        runner.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        runner.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        runner.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        runner.getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
        runner.getInventory().addItem(new ItemStack(Material.OAK_LOG, 5));
        return "";
    }

    private String labelPlayer(String name) {
        Player p = gerPlayer(name);
        if (p != null) {
            runner = p;
            return "";
        } else {
            return "Can't find player " + name;
        }
    }

    private Player gerPlayer(String name) {
        Player[] playerList = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        for (Player p : playerList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
