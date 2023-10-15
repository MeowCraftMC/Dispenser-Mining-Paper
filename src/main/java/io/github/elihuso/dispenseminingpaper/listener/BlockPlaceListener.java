package io.github.elihuso.dispenseminingpaper.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.Plugin;

public class BlockPlaceListener implements Listener {
    private final Plugin plugin;

    public BlockPlaceListener(Plugin plugin) {
        this.plugin = plugin;
    }

    Material[] Dirt = {
            Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL, Material.FARMLAND
    };

    Material[] SugarCaneDirts = {
            Material.DIRT, Material.COARSE_DIRT, Material.MYCELIUM, Material.PODZOL, Material.GRASS_BLOCK, Material.SAND, Material.RED_SAND
    };

    Material[] Flowers = {
            Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.LILY_OF_THE_VALLEY, Material.CORNFLOWER, Material.WITHER_ROSE, Material.FERN, Material.DEAD_BUSH
    };

    Material[] Sand = {
            Material.SAND, Material.RED_SAND
    };

    @EventHandler
    public void onBlockDropItem(BlockDispenseEvent event) {
        if (!event.getBlock().getType().equals(Material.DROPPER)) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        Block dropperBlock = event.getBlock();
        ItemStack item = event.getItem();

        if (item.getType().isBlock()) {
            Block target = dropperBlock.getRelative(((Directional) dropperBlock.getBlockData()).getFacing());
            Block base = target.getRelative(0, -1, 0);
            if (target.getType().isAir()) {

                if (item.hasItemMeta()) {
                    if (item.getItemMeta() instanceof BlockStateMeta) {
                        // Todo: qyl27: block state specific code.
                        return;
                    }
                }

                if (item.getType().name().contains("_SAPLING")) {
                    for (Material v : Dirt) {
                        if (v.equals(base.getType())) {
                            event.setCancelled(true);
                            break;
                        }
                    }
                }
                else if (item.getType().name().contains("_MUSHROOM")) {
                    if (!base.getType().isAir())
                        event.setCancelled(true);
                }
                else if (item.getType().equals(Material.BAMBOO)) {
                    if (base.getType().equals(Material.BAMBOO))
                        event.setCancelled(true);
                    else {
                        for (Material v : SugarCaneDirts) {
                            if (v.equals(base.getType())) {
                                event.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
                else if (item.getType().equals(Material.SUGAR_CANE)) {
                    if (base.getType().equals(Material.SUGAR_CANE))
                        event.setCancelled(true);
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
                                            event.setCancelled(true);
                                            break;
                                        }
                                    }
                                    if (u.getType().equals(Material.WATER)) {
                                        event.setCancelled(true);
                                        break;
                                    }
                                    if (u.getType().equals(Material.FROSTED_ICE)) {
                                        event.setCancelled(true);
                                        break;
                                    }
                                }
                            }
                            if (event.isCancelled())
                                break;
                        }
                    }
                }
                else if (item.getType().equals(Material.NETHER_WART)) {
                    if (base.getType().equals(Material.SOUL_SAND))
                        event.setCancelled(true);
                }
                else if (item.getType().equals(Material.CACTUS)) {
                    if (base.getType().equals(Material.CACTUS))
                        event.setCancelled(true);
                    else {
                        for (var v : Sand) {
                            if (v.equals(base.getType())) {
                                event.setCancelled(true);
                                break;
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
                                    event.setCancelled(true);
                                    break;
                                }
                            }
                        }
                        if (!q)
                            break;
                    }
                    if (q)
                        event.setCancelled(true);
                }

                if (!event.isCancelled())
                    return;

                target.setType((item.getType().equals(Material.BAMBOO) && (!base.getType().equals(Material.BAMBOO))) ? Material.BAMBOO_SAPLING : item.getType());
                target.setBlockData(item.getType().createBlockData());
                target.getState().update();

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Dropper dropper = (Dropper) (dropperBlock.getState());
                    Inventory inv = dropper.getInventory();

                    for (int i = 0; i < inv.getSize(); i++) {
                        ItemStack invItem = inv.getItem(i);
                        if (item.isSimilar(invItem) && invItem != null) {
                            invItem.setAmount(invItem.getAmount() - 1);
                            inv.setItem(i, invItem);
                            break;
                        }
                    }
                }, 1);
            }
        }
    }
}
