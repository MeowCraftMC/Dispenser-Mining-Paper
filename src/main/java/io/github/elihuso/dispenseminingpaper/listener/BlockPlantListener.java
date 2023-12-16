package io.github.elihuso.dispenseminingpaper.listener;

import io.github.elihuso.dispenseminingpaper.utils.DispenserHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class BlockPlantListener implements Listener {
    private final Plugin plugin;

    public BlockPlantListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private static final Material[][] PLANTS = {
            {Material.WHEAT_SEEDS, Material.WHEAT},
            {Material.BEETROOT_SEEDS, Material.BEETROOTS},
            {Material.CARROT, Material.CARROTS},
            {Material.POTATO, Material.POTATOES},
            {Material.PUMPKIN_SEEDS, Material.PUMPKIN_STEM},
            {Material.MELON_SEEDS, Material.MELON_STEM}
    };

    private static final List<Material> DIRT_LIST = List.of(
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT,
            Material.MYCELIUM, Material.PODZOL, Material.FARMLAND
    );

    private static final Material[][] FLOWER_POT_PLANTS = {
            {Material.DANDELION, Material.POTTED_DANDELION},
            {Material.POPPY, Material.POTTED_POPPY},
            {Material.BLUE_ORCHID, Material.POTTED_BLUE_ORCHID},
            {Material.ALLIUM, Material.POTTED_ALLIUM},
            {Material.AZURE_BLUET, Material.POTTED_AZURE_BLUET},
            {Material.RED_TULIP, Material.POTTED_RED_TULIP},
            {Material.ORANGE_TULIP, Material.POTTED_ORANGE_TULIP},
            {Material.WHITE_TULIP, Material.POTTED_WHITE_TULIP},
            {Material.PINK_TULIP, Material.POTTED_PINK_TULIP},
            {Material.OXEYE_DAISY, Material.POTTED_OXEYE_DAISY},
            {Material.LILY_OF_THE_VALLEY, Material.POTTED_LILY_OF_THE_VALLEY},
            {Material.CORNFLOWER, Material.POTTED_CORNFLOWER},
            {Material.WITHER_ROSE, Material.POTTED_WITHER_ROSE},
            {Material.OAK_SAPLING, Material.POTTED_OAK_SAPLING},
            {Material.SPRUCE_SAPLING, Material.POTTED_SPRUCE_SAPLING},
            {Material.BIRCH_SAPLING, Material.POTTED_BIRCH_SAPLING},
            {Material.JUNGLE_SAPLING, Material.POTTED_JUNGLE_SAPLING},
            {Material.ACACIA_SAPLING, Material.POTTED_ACACIA_SAPLING},
            {Material.DARK_OAK_SAPLING, Material.POTTED_DARK_OAK_SAPLING},
            {Material.RED_MUSHROOM, Material.POTTED_RED_MUSHROOM},
            {Material.BROWN_MUSHROOM, Material.POTTED_BROWN_MUSHROOM},
            {Material.FERN, Material.POTTED_FERN},
            {Material.DEAD_BUSH, Material.POTTED_DEAD_BUSH},
            {Material.CACTUS, Material.POTTED_CACTUS},
            {Material.BAMBOO, Material.POTTED_BAMBOO},
            {Material.CRIMSON_FUNGUS, Material.POTTED_CRIMSON_FUNGUS},
            {Material.CRIMSON_ROOTS, Material.POTTED_CRIMSON_ROOTS},
            {Material.WARPED_FUNGUS, Material.POTTED_WARPED_FUNGUS},
            {Material.WARPED_ROOTS, Material.POTTED_WARPED_ROOTS}
    };

    @EventHandler
    public void onPlant(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        var select = Arrays.stream(PLANTS).filter(v -> v[0].equals(item.getType())).findFirst();
        if (select.isEmpty()) {
            return;
        }

        Block target = dispenserBlock.getRelative(((Directional) dispenserBlock.getBlockData()).getFacing());
        Block base = target.getRelative(0, -1, 0);

        if (!target.getType().isAir()) {
            return;
        }
        if (!base.getType().equals(Material.FARMLAND)) {
            return;
        }

        target.setType(select.get()[1]);
        target.setBlockData(select.get()[1].createBlockData());
        target.getState().update();

        DispenserHelper.removeItem(dispenserBlock, item);
        event.setItem(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void onPlantCocoa(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        BlockFace facing = ((Directional) dispenserBlock.getBlockData()).getFacing();
        Block target = dispenserBlock.getRelative(facing);
        Block base = target.getRelative(facing);

        if (!item.getType().equals(Material.COCOA_BEANS)) {
            return;
        }

        if (!target.getType().isAir()) {
            return;
        }

        if (!base.getType().equals(Material.JUNGLE_LOG)) {
            return;
        }

        target.setType(Material.COCOA);
        Cocoa c = (Cocoa) Material.COCOA.createBlockData();
        c.setFacing(facing);
        target.setBlockData(c);

        DispenserHelper.removeItem(dispenserBlock, item);
        event.setItem(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void onPlantSweetBerry(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        BlockFace facing = ((Directional) dispenserBlock.getBlockData()).getFacing();
        Block target = dispenserBlock.getRelative(facing);
        Block base = target.getRelative(0, -1, 0);

        if (!item.getType().equals(Material.SWEET_BERRIES)) {
            return;
        }

        if (!target.getType().isAir()) {
            return;
        }

        if (!DIRT_LIST.contains(base.getType())) {
            return;
        }

        target.setType(Material.SWEET_BERRY_BUSH);
        target.setBlockData(Material.SWEET_BERRY_BUSH.createBlockData());
        target.getState().update();

        DispenserHelper.removeItem(dispenserBlock, item);
        event.setItem(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void onPlantInPot(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.getBlock().getType().equals(Material.DISPENSER)) {
            return;
        }

        Block dispenserBlock = event.getBlock();
        ItemStack item = event.getItem();

        BlockFace facing = ((Directional) dispenserBlock.getBlockData()).getFacing();
        Block target = dispenserBlock.getRelative(facing);

        if (!target.getType().equals(Material.FLOWER_POT)) {
            return;
        }

        var selected = Arrays.stream(FLOWER_POT_PLANTS).filter(v -> v[0].equals(item.getType())).findFirst();

        if (selected.isPresent()) {
            var v = selected.get()[1];

            target.setType(v);
            target.setBlockData(v.createBlockData());
            target.getState().update();

            DispenserHelper.removeItem(dispenserBlock, item);
            event.setItem(new ItemStack(Material.AIR));
        }
    }
}
