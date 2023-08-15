package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
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
        if (event.isCancelled())
            return;
        if (!event.getBlock().getType().equals(Material.DROPPER))
            return;

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());

        if (!item.getType().name().contains("_AXE"))
            return;

        for (Material[] v : Wood) {
            if (v[0].equals(target.getType())) {
                event.setCancelled(true);
                target.setType(v[1]);
                target.setBlockData(v[1].createBlockData());
                target.getState().update();
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Dropper dropper = (Dropper) (dropperBlock.getState());
            Inventory inventory = dropper.getInventory();
            for (int i = 0; i < inventory.getSize(); ++i) {
                if (item.equals(inventory.getItem(i))) {
                    inventory.setItem(i, Utils.Damage(item));
                }
            }
        }, 1);
    }

    @EventHandler
    public void OnProcessByShovel(BlockDispenseEvent event) {
        if (event.isCancelled())
            return;
        if (!event.getBlock().getType().equals(Material.DROPPER))
            return;

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!item.getType().name().contains("_SHOVEL"))
            return;

        if (!target.getType().isAir()) {
            for (Material v : Dirt) {
                if (v.equals(target.getType())) {
                    event.setCancelled(true);
                    target.setType(Material.GRASS_PATH);
                    target.setBlockData(Material.GRASS_PATH.createBlockData());
                    target.getState().update();
                    break;
                }
            }
        }
        else if (!base.getType().isAir()) {
            for (Material v : Dirt) {
                if (v.equals(base.getType())) {
                    event.setCancelled(true);
                    base.setType(Material.GRASS_PATH);
                    base.setBlockData(Material.GRASS_PATH.createBlockData());
                    base.getState().update();
                    break;
                }
            }
        }

        if (!event.isCancelled())
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Dropper dropper = (Dropper) (dropperBlock.getState());
            Inventory inventory = dropper.getInventory();
            for (int i = 0; i < inventory.getSize(); ++i) {
                if (item.equals(inventory.getItem(i))) {
                    inventory.setItem(i, Utils.Damage(item));
                }
            }
        }, 1);
    }

    @EventHandler
    public void OnProcessByHoe(BlockDispenseEvent event) {
        if (event.isCancelled())
            return;
        if (!event.getBlock().getType().equals(Material.DROPPER))
            return;

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();
        Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!item.getType().name().contains("_HOE"))
            return;

        if (!target.getType().isAir()) {
            for (Material v : Dirt) {
                if (v.equals(target.getType())) {
                    event.setCancelled(true);
                    target.setType(Material.FARMLAND);
                    target.setBlockData(Material.FARMLAND.createBlockData());
                    target.getState().update();
                    break;
                }
            }
        }
        else if (!base.getType().isAir()) {
            for (Material v : Dirt) {
                if (v.equals(base.getType())) {
                    event.setCancelled(true);
                    base.setType(Material.FARMLAND);
                    base.setBlockData(Material.FARMLAND.createBlockData());
                    base.getState().update();
                    break;
                }
            }
        }

        if (!event.isCancelled())
            return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Dropper dropper = (Dropper) (dropperBlock.getState());
            Inventory inventory = dropper.getInventory();
            for (int i = 0; i < inventory.getSize(); ++i) {
                if (item.equals(inventory.getItem(i))) {
                    inventory.setItem(i, Utils.Damage(item));
                }
            }
        }, 1);
    }
}
