package io.github.elihuso.dispenseminingpaper.utility;

import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackHelper {
    public static ItemStack createBreaker() {
        var item = new ItemStack(Material.DISPENSER);
        var meta = item.getItemMeta();
        meta.displayName(Component.text("破坏者"));
        item.setItemMeta(meta);
        MiningDispenserData.set(item, new MiningDispenserData(MiningDispenserData.Type.BREAKER));
        return item;
    }

    public static ItemStack createPlacer() {
        var item = new ItemStack(Material.DISPENSER);
        var meta = item.getItemMeta();
        meta.displayName(Component.text("放置者"));
        item.setItemMeta(meta);
        MiningDispenserData.set(item, new MiningDispenserData(MiningDispenserData.Type.PLACER));
        return item;
    }
}
