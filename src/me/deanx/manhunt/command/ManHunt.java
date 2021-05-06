package me.deanx.manhunt.command;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ManHunt implements CommandExecutor {
    public ManHunt(ManHuntPlugin plugin) {
        plugin.getCommand("ManHunt").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("hello")) {
            sender.sendMessage("Hello bro!");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                sender.sendMessage(Double.toString(maxHealth));
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);
                player.getInventory().addItem(new ItemStack(Material.COOKED_PORKCHOP, 3));
            }
            return true;
        }
        return false;
    }
}
