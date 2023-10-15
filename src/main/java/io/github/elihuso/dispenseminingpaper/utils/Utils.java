package io.github.elihuso.dispenseminingpaper.utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.lang.Math;

public class Utils {
    public static int getRand(int _max) {
        return (int) (Math.random() * _max);
    }

    public static ItemStack Damage(ItemStack _item, @Nullable Block _block) {
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
            if (!LocalConfigs.allowNegativeTools)
                if (_item.getType().getMaxDurability() <= damageable.getDamage()) {
                    _item.setAmount(0);
                    _block.getWorld().playSound(_block.getLocation(), Sound.ENTITY_ITEM_BREAK, 24, 0);
                }
        }
        return _item;
    }

    public static ItemStack Damage(ItemStack _item) {
        return Damage(_item, null);
    }

    public static class LocalConfigs {
        public static boolean enabled = true;
        public static boolean allowNegativeTools = true;
        public static boolean plantCrops = true;
        public static boolean breakBedrocks = true;
        public static boolean processByDropper = true;
    }

    private static final Material[] Dirt = {
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL, Material.FARMLAND
    };

    private static final Material[] SugarCaneDirts = {
            Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL, Material.GRASS_BLOCK, Material.SAND, Material.RED_SAND
    };

    private static final Material[] Flowers = {
            Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.LILY_OF_THE_VALLEY, Material.CORNFLOWER, Material.WITHER_ROSE, Material.FERN, Material.DEAD_BUSH
    };

    private static final Material[] Sand = {
            Material.SAND, Material.RED_SAND
    };

    public static boolean CouldPlace(ItemStack item, Block base) {
        if (item.hasItemMeta()) {
            if (item.getItemMeta() instanceof BlockStateMeta) {
                // Todo: qyl27: block state specific code.
                return false;
            }
        }

        if (item.getType().name().contains("_SAPLING")) {
            for (Material v : Dirt) {
                if (v.equals(base.getType())) {
                    return true;
                }
            }
        }
        else if (item.getType().name().contains("_MUSHROOM")) {
            return !base.getType().isAir();
        }
        else if (item.getType().equals(Material.BAMBOO)) {
            if (base.getType().equals(Material.BAMBOO))
                return true;
            else {
                for (Material v : SugarCaneDirts) {
                    if (v.equals(base.getType())) {
                        return true;
                    }
                }
            }
        }
        else if (item.getType().equals(Material.SUGAR_CANE)) {
            if (base.getType().equals(Material.SUGAR_CANE))
                return true;
            else {
                for (Material v : SugarCaneDirts) {
                    if (v.equals(base.getType())) {
                        Block[] roundblock = {
                                base.getRelative(1, 0, 0),
                                base.getRelative(0, 0, 1),
                                base.getRelative(-1, 0, 0),
                                base.getRelative(0, 0, -1)
                        };
                        for (Block u : roundblock) {
                            if ((u.getBlockData() instanceof Waterlogged)) {
                                if (((Waterlogged) u.getBlockData()).isWaterlogged()) {
                                    return true;
                                }
                            }
                            if (u.getType().equals(Material.WATER)) {
                                return true;
                            }
                            if (u.getType().equals(Material.FROSTED_ICE)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if (item.getType().equals(Material.NETHER_WART)) {
            return base.getType().equals(Material.SOUL_SAND);
        }
        else if (item.getType().equals(Material.CACTUS)) {
            if (base.getType().equals(Material.CACTUS))
                return true;
            else {
                for (var v : Sand) {
                    if (v.equals(base.getType())) {
                        return true;
                    }
                }
            }
        }
        else {
            boolean q = true;
            for (var v : Flowers) {
                if (v.equals(item.getType())) {
                    q = false;
                    for (var u : Dirt) {
                        if (base.getType().equals(u)) {
                            return true;
                        }
                    }
                }
            }
            return q;
        }
        return false;
    }
}
