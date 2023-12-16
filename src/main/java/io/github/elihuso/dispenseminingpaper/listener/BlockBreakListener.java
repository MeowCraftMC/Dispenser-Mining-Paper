package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.DispenserHelper;
import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockBreakListener implements Listener {
    private final Plugin plugin;

    public BlockBreakListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack tool = event.getItem();
        String toolName = tool.getType().name();

        if (!(toolName.contains("_AXE")
                || toolName.contains("_PICKAXE")
                || toolName.contains("_SHOVEL")
                || toolName.contains("_HOE")
                || toolName.contains("_SWORD"))) {
            return;
        }

        Block target = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
        if (!target.getType().isAir()) {
            event.setItem(new ItemStack(Material.AIR));

            if (!target.getDrops(tool).isEmpty()) {
                target.breakNaturally(tool, true);

                DispenserHelper.damageItem(dispenserBlock, tool);
            }
        }
    }

    @EventHandler
    public void onDispenseEnchantedGoldenApple(BlockDispenseEvent event) {
        if (!Utils.LocalConfigs.breakBedrocks) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        if (!item.getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {
            return;
        }

        Block target = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
        if (!target.getType().equals(Material.BEDROCK)) {
            return;
        }

        event.setItem(new ItemStack(Material.AIR));

        target.setType(Material.AIR);//Break Bedrock but no drop.
    }
}
