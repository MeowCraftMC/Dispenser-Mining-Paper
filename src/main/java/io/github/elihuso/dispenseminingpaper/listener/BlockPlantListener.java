package io.github.elihuso.dispenseminingpaper.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;
import org.bukkit.plugin.Plugin;

public class BlockPlantListener implements Listener {
    private final Plugin plugin;
    public BlockPlantListener(Plugin plugin) {this.plugin = plugin;};
    static Material[][] plants = {
            {Material.WHEAT_SEEDS, Material.WHEAT},
            {Material.BEETROOT_SEEDS, Material.BEETROOTS},
            {Material.CARROT,Material.CARROTS},
            {Material.POTATO, Material.POTATOES},
            {Material.PUMPKIN_SEEDS, Material.PUMPKIN_STEM},
            {Material.MELON_SEEDS, Material.MELON_STEM},
            {Material.SWEET_BERRIES, Material.SWEET_BERRY_BUSH}
    };
    @EventHandler
    public void OnPlant(BlockDispenseEvent event) {
        if (event.isCancelled())
            return;
        if (!event.getBlock().getType().equals(Material.DISPENSER))
            return;

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();
        boolean isselected = false;

        for(Material[] v : plants) {
            if (v[0].equals(item.getType())) {
                isselected = true;
                break;
            }
        }
        if (!isselected)
            return;

        Block target = dispenserBlock.getRelative(((Directional)dispenserBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!target.getType().isAir())
            return;
        if (!base.getType().equals(Material.FARMLAND))
            return;

        event.setCancelled(true);

        isselected = false;
        for(Material[] v : plants) {
            if (v[0].equals(item.getType())) {
                target.setType(v[1]);
                target.setBlockData(v[1].createBlockData());
                target.getState().update();
                isselected = true;
            }
        }

        if (!isselected)
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            Dispenser dispenser = (Dispenser)(dispenserBlock.getState());
            Inventory inventory = dispenser.getInventory();

            for (int i = 0; i < inventory.getSize(); ++i) {
                ItemStack invItem = inventory.getItem(i);
                if (invItem.isSimilar(event.getItem())) {
                    invItem.setAmount(invItem.getAmount() - 1);
                    inventory.setItem(i, invItem);
                    break;
                }
            }
        }, 1);
    }

    @EventHandler
    public void OnPlantCocoa(BlockDispenseEvent event) {
        if (event.isCancelled())
            return;
        if (!event.getBlock().getType().equals(Material.DISPENSER))
            return;

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        BlockFace facing = ((Directional)dispenserBlock.getBlockData()).getFacing();
        Block target = dispenserBlock.getRelative(facing);
        Block base = target.getRelative(facing);

        if (!item.getType().equals(Material.COCOA_BEANS))
            return;
        if (!target.getType().isAir())
            return;
        if (!base.getType().equals(Material.JUNGLE_LOG))
            return;

        event.setCancelled(true);

        target.setType(Material.COCOA);
        Cocoa c = (Cocoa)Material.COCOA.createBlockData();
        c.setFacing(facing);
        target.setBlockData(c);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            Dispenser dispenser = (Dispenser)(dispenserBlock.getState());
            Inventory inventory = dispenser.getInventory();

            for (int i = 0; i < inventory.getSize(); ++i) {
                ItemStack invItem = inventory.getItem(i);
                if (invItem.isSimilar(event.getItem())) {
                    invItem.setAmount(invItem.getAmount() - 1);
                    inventory.setItem(i, invItem);
                    break;
                }
            }
        }, 1);
    }
}
