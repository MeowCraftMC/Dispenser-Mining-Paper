package io.github.elihuso.dispenseminingpaper.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class BlockPlaceListener implements Listener {
    private final Plugin plugin;

    public BlockPlaceListener(Plugin plugin) {
        this.plugin = plugin;
    }

    Material[] TreeDirt = {
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL, Material.FARMLAND
    };

    @EventHandler
    public void onBlockDropItem(BlockDispenseEvent event) {
        if (!event.getBlock().getType().equals(Material.DROPPER)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();

        if (item.getType().isBlock()) {
            Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
            Block base = target.getRelative(0, -1, 0);
            if (target.getType().isAir()) {

                if (item.hasItemMeta()) {
                    if (item.getItemMeta() instanceof BlockStateMeta) {
                        // Todo: qyl27: block state specific code.
                        return;
                    }
                }

                if (item.getType().name().contains("_SAPLING")) {
                    for (Material v : TreeDirt) {
                        if (v.equals(base.getType())) {
                            event.setCancelled(true);
                            break;
                        }
                    }
                }
                else if (item.getType().name().contains("_MUSHROOM")) {
                    if (!base.getType().isAir())
                        event.setCancelled(true);
                }
                else {
                    event.setCancelled(true);
                }

                if (!event.isCancelled())
                    return;

                target.setType(item.getType());
                target.setBlockData(item.getType().createBlockData());
                target.getState().update();

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Dropper dropper = (Dropper) (dropperBlock.getState());
                    Inventory inv = dropper.getInventory();

                    for (int i = 0; i < inv.getSize(); i++) {
                        ItemStack invItem = inv.getItem(i);
                        if (item.isSimilar(invItem) && invItem != null) {
                            invItem.setAmount(invItem.getAmount() - 1);
                            inv.setItem(i, invItem);
                            break;
                        }
                    }
                }, 1);
            }
        }
    }
}
