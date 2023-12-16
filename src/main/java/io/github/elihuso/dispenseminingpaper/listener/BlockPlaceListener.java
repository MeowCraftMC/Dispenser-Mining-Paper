package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockPlaceListener implements Listener {
    private final Plugin plugin;

    public BlockPlaceListener(Plugin plugin) {
        this.plugin = plugin;
    }

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

                if (!Utils.CouldPlace(item, base)) {
                    return;
                }

                target.setType((item.getType().equals(Material.BAMBOO) && (!base.getType().equals(Material.BAMBOO))) ? Material.BAMBOO_SAPLING : item.getType());
                target.setBlockData(item.getType().createBlockData());
                target.getState().update();

                event.setItem(new ItemStack(Material.AIR));
            }
        }
    }
}
