package org.dispensermining.dispenserminingpaper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Dispenser_Mining_Paper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().log(Level.FINE, "Dispenser Mining Plugin Enabled");
    }

    @EventHandler
    public void BlockDispenseChange(BlockDispenseEvent event) {
        //getLogger().log(Level.INFO, "Dispense");//For Debug
        //getLogger().log(Level.INFO, event.getBlock().toString());
        if (event.getBlock().getType().equals(Material.DISPENSER)) {
            //getLogger().log(Level.INFO, "DETACTED");
            String strItem = event.getItem().getType().name();
            if (strItem.contains("AXE")/*axe and pickaxe*/ || strItem.contains("_SHOVEL") || strItem.contains("_HOE")) {
                if (!event.isCancelled()) {
                    event.setCancelled(true);
                    BlockFace facing = ((Directional)(event.getBlock().getState().getBlockData())).getFacing();
                    Block block = event.getBlock().getRelative(facing).getLocation().getBlock();
                    if (CanBreaking.canBreaking(event.getItem(), block)) {
                        block.breakNaturally(event.getItem());
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}