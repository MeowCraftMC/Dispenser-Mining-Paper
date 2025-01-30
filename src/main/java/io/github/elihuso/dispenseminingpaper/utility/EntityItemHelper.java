package io.github.elihuso.dispenseminingpaper.utility;

import io.github.elihuso.dispenseminingpaper.data.DummyItemData;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class EntityItemHelper {
    @SuppressWarnings("UnstableApiUsage")
    public static void dropItem(Item templateEntity, ItemStack stack) {
        var e = (Item) templateEntity.copy(templateEntity.getLocation());
        DummyItemData.set(stack, null);
        e.setItemStack(stack);
        templateEntity.getWorld().addEntity(e);
    }
}
