package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.DispenserHelper;
import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockProcessListener implements Listener {
    private final Plugin plugin;

    public BlockProcessListener(Plugin plugin) {
        this.plugin = plugin;
    }

    static Material[][] Wood = {
            {Material.OAK_LOG, Material.STRIPPED_OAK_LOG},
            {Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG},
            {Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG},
            {Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG},
            {Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG},
            {Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG},
            {Material.CRIMSON_STEM, Material.STRIPPED_CRIMSON_STEM},
            {Material.WARPED_STEM, Material.STRIPPED_WARPED_STEM}
    };
    static Material[] Dirt = {
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL
    };

    @EventHandler
    public void OnProcessWood(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DROPPER)) {
            return;
        }

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());

        if (!item.getType().name().contains("_AXE")) {
            return;
        }

        for (Material[] v : Wood) {
            if (v[0].equals(target.getType())) {
                target.setType(v[1]);
                target.setBlockData(v[1].createBlockData());
                target.getState().update();

                event.setItem(new ItemStack(Material.AIR));
                DispenserHelper.damageItem(dropperBlock, item);
            }
        }
    }

    @EventHandler
    public void OnProcessByShovel(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DROPPER)) {
            return;
        }

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!item.getType().name().contains("_SHOVEL")) {
            return;
        }

        if (!target.getType().isAir()) {
            if (processDirt(target, Material.GRASS_PATH)) {
                event.setItem(new ItemStack(Material.AIR));
                DispenserHelper.damageItem(dropperBlock, item);
            }
        } else if (!base.getType().isAir()) {
            if (processDirt(base, Material.GRASS_PATH)) {
                event.setItem(new ItemStack(Material.AIR));
                DispenserHelper.damageItem(dropperBlock, item);
            }
        }
    }

    @EventHandler
    public void OnProcessByHoe(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.getBlock().getType().equals(Material.DROPPER)) {
            return;
        }

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!item.getType().name().contains("_HOE")) {
            return;
        }

        if (!target.getType().isAir()) {
            if (processDirt(target, Material.FARMLAND)) {
                event.setItem(new ItemStack(Material.AIR));
                DispenserHelper.damageItem(dropperBlock, item);
            }
        } else if (!base.getType().isAir()) {
            if (processDirt(base, Material.FARMLAND)) {
                event.setItem(new ItemStack(Material.AIR));
                DispenserHelper.damageItem(dropperBlock, item);
            }
        }
    }

    private boolean processDirt(Block block, Material target) {
        for (Material v : Dirt) {
            if (v.equals(block.getType())) {
                block.setType(target);
                block.setBlockData(target.createBlockData());
                block.getState().update();

                return true;
            }
        }
        return false;
    }
}
