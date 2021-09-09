package me.deanx.manhunt.command;

import me.deanx.manhunt.ManHuntPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManHuntTabCompleter implements TabCompleter {
    private final List<String> COMMANDS = Arrays.asList("start", "stop", "compass", "runner", "random");

    public ManHuntTabCompleter(ManHuntPlugin plugin) {
        plugin.getCommand("ManHunt").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> hint = new ArrayList<>();
        switch (args.length) {
            case 1:
                StringUtil.copyPartialMatches(args[0], COMMANDS, hint);
                break;
            case 2:
                if (args[0].equalsIgnoreCase("runner")) {
                    return null; // hint will be default (Player list)
                }
                break;
        }
        return hint;
    }
}
