package io.github.elihuso.dispenseminingpaper.handler.place;

import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;

public interface IPlacer {

    boolean canPlace(Dispenser placer, ItemStack item, Block target);

    void place(Dispenser placer, ItemStack item, Block target);
}
