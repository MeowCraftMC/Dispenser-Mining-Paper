package io.github.elihuso.dispenseminingpaper.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.Math;

public class Utils {
    public static int getRand(int _max) {
        return (int)(Math.random() * _max);
    }

    public static ItemStack Damage(ItemStack _item) {
        if (_item.getItemMeta().isUnbreakable())
            return _item;
        ItemMeta itemMeta = _item.getItemMeta();
        int durability = 0;
        if (itemMeta.hasEnchant(Enchantment.DURABILITY))
            durability = itemMeta.getEnchantLevel(Enchantment.DURABILITY);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            if (durability == 0)
                damageable.setDamage(damageable.getDamage() + 1);
            else if (Utils.getRand(durability + 1) == 0)
                damageable.setDamage(damageable.getDamage() + 1);
            _item.setItemMeta(itemMeta);
        }
        return _item;
    }
}
