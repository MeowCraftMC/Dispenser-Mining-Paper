package io.github.elihuso.dispenseminingpaper.utility;

import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import io.github.elihuso.dispenseminingpaper.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;

public class ItemStackHelper {
    private static final Random RAND = new Random();

    public static ItemStack createBreaker() {
        var item = new ItemStack(Material.DISPENSER);
        var meta = item.getItemMeta();
        meta.displayName(Component.text("破坏者").decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        MiningDispenserData.set(item, new MiningDispenserData(MiningDispenserData.Type.BREAKER));
        return item;
    }

    public static ItemStack createPlacer() {
        var item = new ItemStack(Material.DISPENSER);
        var meta = item.getItemMeta();
        meta.displayName(Component.text("放置者").decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
        MiningDispenserData.set(item, new MiningDispenserData(MiningDispenserData.Type.PLACER));
        return item;
    }

    public static void damage(ItemStack item, Block userBlock) {
        if (item.getItemMeta().isUnbreakable()) {
            return;
        }
        var meta = item.getItemMeta();
        if (meta instanceof Damageable damageable) {
            var damage = damageable.getDamage();
            var unbreakingLevel = 0;
            if (meta.hasEnchant(Enchantment.UNBREAKING)) {
                unbreakingLevel = meta.getEnchantLevel(Enchantment.UNBREAKING);
            }
            if (unbreakingLevel == 0 || RAND.nextInt(unbreakingLevel) == 0) {
                damage += 1;
            }
            damageable.setDamage(damage);
            item.setItemMeta(meta);
            if (item.getType().getMaxDurability() <= damageable.getDamage()) {
                item.subtract();
                userBlock.getWorld().playSound(userBlock.getLocation(), Sound.ENTITY_ITEM_BREAK, 24, 0);
            }
        }
    }
}
