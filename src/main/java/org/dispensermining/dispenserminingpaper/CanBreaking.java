package org.dispensermining.dispenserminingpaper;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.ItemStack;

public class CanBreaking {
    static Material[] Iron = {
            Material.DIAMOND_ORE, Material.DIAMOND_BLOCK, Material.EMERALD_ORE, Material.EMERALD_BLOCK,
            Material.GOLD_ORE, Material.GOLD_BLOCK, Material.REDSTONE_ORE
    };

    static Material[] Diamond = {
            Material.ANCIENT_DEBRIS, Material.NETHERITE_BLOCK, Material.OBSIDIAN, Material.CRYING_OBSIDIAN,
            Material.RESPAWN_ANCHOR
    };

    static Material[] Stone = {
            Material.IRON_ORE, Material.IRON_BLOCK, Material.LAPIS_ORE, Material.LAPIS_BLOCK
    };

    static Material[] Wooden = {
            Material.STONE, Material.STONE_SLAB, Material.STONE_STAIRS,
            Material.SMOOTH_STONE, Material.SMOOTH_STONE_SLAB,
            Material.COBBLESTONE, Material.COBBLESTONE_SLAB, Material.COBBLESTONE_STAIRS, Material.COBBLESTONE_WALL,
            Material.MOSSY_COBBLESTONE, Material.MOSSY_COBBLESTONE_SLAB, Material.MOSSY_COBBLESTONE_STAIRS, Material.MOSSY_COBBLESTONE_WALL,
            Material.STONE_BRICKS, Material.STONE_BRICK_SLAB, Material.STONE_BRICK_STAIRS, Material.STONE_BRICK_WALL,
            Material.CHISELED_STONE_BRICKS, Material.CRACKED_STONE_BRICKS,
            Material.MOSSY_STONE_BRICKS, Material.MOSSY_STONE_BRICK_SLAB, Material.MOSSY_STONE_BRICK_STAIRS, Material.MOSSY_STONE_BRICK_WALL,
            Material.BLACKSTONE, Material.BLACKSTONE_SLAB, Material.BLACKSTONE_STAIRS, Material.BLACKSTONE_WALL,
            Material.POLISHED_BLACKSTONE, Material.POLISHED_BLACKSTONE_SLAB, Material.POLISHED_BLACKSTONE_STAIRS, Material.POLISHED_BLACKSTONE_WALL,
            Material.POLISHED_BLACKSTONE_BRICKS, Material.POLISHED_BLACKSTONE_BRICK_SLAB, Material.POLISHED_BLACKSTONE_BRICK_STAIRS, Material.POLISHED_BLACKSTONE_BRICK_WALL,
            Material.CHISELED_POLISHED_BLACKSTONE, Material.CRACKED_POLISHED_BLACKSTONE_BRICKS,
            Material.DIORITE, Material.DIORITE_SLAB, Material.DIORITE_STAIRS, Material.DIORITE_WALL,
            Material.POLISHED_DIORITE, Material.POLISHED_DIORITE_SLAB, Material.POLISHED_DIORITE_STAIRS,
            Material.ANDESITE, Material.ANDESITE_SLAB, Material.ANDESITE_STAIRS, Material.ANDESITE_WALL,
            Material.POLISHED_ANDESITE, Material.POLISHED_ANDESITE_SLAB, Material.POLISHED_ANDESITE_STAIRS,
            Material.GRANITE, Material.GRANITE_SLAB, Material.GRANITE_STAIRS, Material.GRANITE_WALL,
            Material.POLISHED_GRANITE, Material.POLISHED_GRANITE_SLAB, Material.POLISHED_GRANITE_STAIRS,
            Material.BASALT, Material.POLISHED_BASALT,
            Material.BRICKS, Material.BRICK_SLAB, Material.BRICK_STAIRS, Material.BRICK_WALL,
            Material.SANDSTONE, Material.SANDSTONE_SLAB, Material.SANDSTONE_STAIRS, Material.SANDSTONE_WALL,
            Material.CUT_SANDSTONE, Material.CUT_SANDSTONE_SLAB,
            Material.CHISELED_SANDSTONE, Material.SMOOTH_SANDSTONE, Material.SMOOTH_SANDSTONE_SLAB, Material.SMOOTH_SANDSTONE_STAIRS,
            Material.RED_SANDSTONE, Material.RED_SANDSTONE_SLAB, Material.RED_SANDSTONE_STAIRS, Material.RED_SANDSTONE_WALL,
            Material.CUT_RED_SANDSTONE, Material.CUT_RED_SANDSTONE_SLAB,
            Material.CHISELED_RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE_SLAB, Material.SMOOTH_RED_SANDSTONE_STAIRS,
            Material.PRISMARINE, Material.PRISMARINE_SLAB, Material.PRISMARINE_STAIRS, Material.PRISMARINE_WALL,
            Material.DARK_PRISMARINE, Material.DARK_PRISMARINE_SLAB, Material.DARK_PRISMARINE_STAIRS,
            Material.PRISMARINE_BRICKS, Material.PRISMARINE_BRICK_SLAB, Material.PRISMARINE_BRICK_STAIRS,
            Material.NETHERRACK, Material.WARPED_NYLIUM, Material.CRIMSON_NYLIUM,
            Material.NETHER_BRICK, Material.NETHER_BRICK_SLAB, Material.NETHER_BRICK_STAIRS, Material.NETHER_BRICK_WALL, Material.NETHER_BRICK_FENCE,
            Material.CHISELED_NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS,
            Material.RED_NETHER_BRICKS, Material.RED_NETHER_BRICK_SLAB, Material.RED_NETHER_BRICK_STAIRS, Material.RED_NETHER_BRICK_WALL,
            Material.END_STONE, Material.END_STONE_BRICKS, Material.END_STONE_BRICK_SLAB, Material.END_STONE_BRICK_STAIRS, Material.END_STONE_BRICK_WALL,
            Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.PURPUR_SLAB, Material.PURPUR_STAIRS,
            Material.BONE_BLOCK,//
            Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR, Material.QUARTZ_BRICKS, Material.QUARTZ_SLAB, Material.QUARTZ_STAIRS,
            Material.SMOOTH_QUARTZ, Material.SMOOTH_QUARTZ_SLAB, Material.SMOOTH_QUARTZ_STAIRS,
            Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE,
            Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA,
            Material.WHITE_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA,
            Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.FIRE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK,
            Material.DEAD_TUBE_CORAL_BLOCK, Material.DEAD_BRAIN_CORAL_BLOCK, Material.DEAD_BUBBLE_CORAL_BLOCK, Material.DEAD_FIRE_CORAL_BLOCK, Material.DEAD_HORN_CORAL_BLOCK,
            Material.MAGMA_BLOCK, Material.CHAIN, Material.IRON_BARS, Material.IRON_DOOR, Material.IRON_TRAPDOOR, Material.LANTERN, Material.SOUL_LANTERN,
            Material.REDSTONE_BLOCK, Material.COAL_BLOCK, Material.COAL_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE, Material.GILDED_BLACKSTONE,
            Material.ENDER_CHEST, Material.FURNACE, Material.BLAST_FURNACE, Material.SMOKER, Material.BREWING_STAND, Material.CAULDRON,
            Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL,
            Material.STONECUTTER, Material.GRINDSTONE, Material.BELL, Material.ENCHANTING_TABLE, Material.LODESTONE, Material.OBSERVER, Material.DISPENSER, Material.DROPPER, Material.HOPPER,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE
    };

    public static boolean canBreaking(ItemStack item, Block block) {
        if (item.getType().equals(Material.DIAMOND_PICKAXE)|| item.getType().equals(Material.NETHERITE_PICKAXE))
            return true;
        else if (item.getType().equals(Material.IRON_PICKAXE) || item.getType().equals(Material.GOLDEN_PICKAXE)) {
            for (Material v: Diamond) {
                if (v.equals(block.getType()))
                    return false;
            }
        }
        else if (item.getType().equals(Material.STONE_PICKAXE)) {
            for (Material v : Diamond) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Iron) {
                if (v.equals(block.getType()))
                    return false;
            }
        }
        else if (item.getType().equals(Material.WOODEN_PICKAXE)) {
            for (Material v : Diamond) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Iron) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Stone) {
                if (v.equals(block.getType()))
                    return false;
            }
        }
        else {
            for (Material v : Diamond) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Iron) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Stone) {
                if (v.equals(block.getType()))
                    return false;
            }
            for (Material v : Wooden) {
                if (v.equals(block.getType()))
                    return false;
            }
        }
        return true;
    }
}
