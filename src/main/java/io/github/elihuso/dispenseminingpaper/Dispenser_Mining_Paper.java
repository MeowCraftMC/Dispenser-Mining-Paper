package io.github.elihuso.dispenseminingpaper;

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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Dispenser_Mining_Paper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().log(Level.FINE, "Dispenser Mining Plugin Enabled");
    }

    @EventHandler
    public void BlockDispenseChange(BlockDispenseEvent event) {
        if (event.getBlock().getType().equals(Material.DISPENSER)) {
            if (event.isCancelled()) {
                return;
            }

            Block dispenserBlock = event.getBlock();

            ItemStack tool = event.getItem();

            Block block = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
            if (!block.getDrops(tool).isEmpty()) {
                event.setCancelled(true);

                block.breakNaturally(tool, true);

                // qyl27: Use scheduler to prevent cancel with empty container.
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                    Dispenser dispenser = (Dispenser) (dispenserBlock.getState());
                    Inventory inv = dispenser.getInventory();

                    for (int i = 0; i < inv.getSize(); i++) {
                        if (tool.equals(inv.getItem(i))) {
                            if (!tool.getItemMeta().isUnbreakable()) {
                                ItemMeta meta = tool.getItemMeta();
                                if (meta instanceof Damageable) {
                                    Damageable damageable = (Damageable) meta;
                                    damageable.setDamage(damageable.getDamage() + 1);
                                    tool.setItemMeta(meta);
                                    inv.setItem(i, tool);
                                }
                            }
                        }
                    }
                }, 1);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}