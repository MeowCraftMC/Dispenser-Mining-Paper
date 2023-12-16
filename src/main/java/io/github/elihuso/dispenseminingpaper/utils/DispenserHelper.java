package io.github.elihuso.dispenseminingpaper.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DispenserHelper {
    public static void removeItem(Block dispenserBlock, ItemStack itemStack) {
        Dispenser dispenser = (Dispenser) dispenserBlock.getState();
        Inventory inventory = dispenser.getInventory();
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack invItem = inventory.getItem(i);
            if (itemStack.isSimilar(invItem) && (invItem != null)) {
                invItem.setAmount(invItem.getAmount() - 1);
                inventory.setItem(i, invItem);
                break;
            }
        }
    }

    public static void damageItem(Block block, ItemStack tool) {
        // qyl27: It's a hacky method to fix de-sync.
//        var tool = new ItemStack(Material.AIR);
//        if (itemStack instanceof CraftItemStack) {
//            tool = CraftItemStack.asCraftMirror(((CraftItemStack) itemStack).handle);
//        }
        
        var state = block.getState();
        Inventory inv = null;

        if (state instanceof Dispenser) {
            inv = ((Dispenser) state).getInventory();
        } else if (state instanceof Dropper) {
            inv = ((Dropper) state).getInventory();
        }

        if (inv != null) {
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                    inv.setItem(i, Utils.damageTool(tool, block));
                    break;
                }
            }
        }
    }
}
