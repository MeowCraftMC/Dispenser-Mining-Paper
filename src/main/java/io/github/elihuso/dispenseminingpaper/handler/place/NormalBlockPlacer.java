package io.github.elihuso.dispenseminingpaper.handler.place;

import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;

public class NormalBlockPlacer implements IPlacer {
    @Override
    public boolean canPlace(Dispenser placer, ItemStack item, Block target) {
        if (!target.isEmpty()) {
            return false;
        }

        var type = item.getType();
        if (!type.isBlock()) {
            return false;
        }

        return target.canPlace(type.createBlockData());
    }

    @Override
    public void place(Dispenser placer, ItemStack item, Block target) {
        var world = target.getWorld();
        var location = target.getLocation();
        world.setBlockData(location, item.getType().createBlockData());
        target.getState().update();
    }
}
