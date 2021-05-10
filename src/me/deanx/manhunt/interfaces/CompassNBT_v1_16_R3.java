package me.deanx.manhunt.interfaces;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CompassNBT_v1_16_R3 implements CompassNBT {
    private org.bukkit.inventory.ItemStack compass;

    @Override
    public void updateCompass(Player runner) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.COMPASS));
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound pos = new NBTTagCompound();
        Location location = runner.getLocation();
        pos.setInt("X", location.getBlockX());
        pos.setInt("Y", location.getBlockY());
        pos.setInt("Z", location.getBlockZ());
        compound.set("LodestoneTracked", NBTTagByte.a(false));
        compound.set("LodestoneDimension", NBTTagString.a("minecraft:overworld"));
        compound.set("LodestonePos", pos);
        nmsStack.setTag(compound);

        compass = CraftItemStack.asBukkitCopy(nmsStack);
    }

    @Override
    public void updateInventory(Player player) {
        Inventory inventory = player.getInventory();
        int stackNum = inventory.first(Material.COMPASS);

        inventory.setItem(stackNum, compass);
    }
}
