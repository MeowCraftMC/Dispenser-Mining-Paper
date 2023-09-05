package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
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
        String toolname = tool.getType().name();

        if (!(toolname.contains("_AXE") || toolname.contains("_PICKAXE") || toolname.contains("_SHOVEL") || toolname.contains("_HOE") || toolname.contains("_SWORD")))
            return;

        Block target = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
        if (target.getType().isAir()) {
            event.setCancelled(true);
        }
        else if (!target.getDrops(tool).isEmpty()) {
            event.setCancelled(true);

            target.breakNaturally(tool, true);

            // qyl27: Use scheduler to prevent cancel with empty container.
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Dispenser dispenser = (Dispenser) (dispenserBlock.getState());
                Inventory inv = dispenser.getInventory();

                for (int i = 0; i < inv.getSize(); i++) {
                    if (tool.equals(inv.getItem(i))) {
                        inv.setItem(i, Utils.Damage(tool));
                        break;
                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void onDispenseEnchantedGoldenApple(BlockDispenseEvent event) {
        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        if (!item.getType().equals(Material.ENCHANTED_GOLDEN_APPLE))
            return;

        Block target = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
        if (!target.getType().equals(Material.BEDROCK))
            return;
        target.setType(Material.AIR);//Break Bedrock but no drop.

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Dispenser dispenser = (Dispenser) (dispenserBlock.getState());
            Inventory inventory = dispenser.getInventory();

            for (int i = 0; i < inventory.getSize(); ++i) {
                if (item.equals(inventory.getItem(i))) {
                    ItemStack invItem = inventory.getItem(i);
                    invItem.setAmount(invItem.getAmount() - 1);
                    inventory.setItem(i, invItem);
                }
            }
        }, 1);
    }
}
