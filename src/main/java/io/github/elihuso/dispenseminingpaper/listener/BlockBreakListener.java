package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
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
                        if (!tool.getItemMeta().isUnbreakable()) {
                            ItemMeta meta = tool.getItemMeta();
                            int durability = 0;
                            if (meta.hasEnchant(Enchantment.DURABILITY)) {
                                durability = meta.getEnchantLevel(Enchantment.DURABILITY);
                            }
                            if (meta instanceof Damageable) {
                                Damageable damageable = (Damageable) meta;
                                if (durability == 0)
                                    damageable.setDamage(damageable.getDamage() + 1);
                                else if (Utils.getRand(durability) == 0)
                                    damageable.setDamage(damageable.getDamage() + 1);
                                tool.setItemMeta(meta);
                                inv.setItem(i, tool);
                                break;
                            }
                        }
                    }
                }
            }, 1);
        }
    }
}
