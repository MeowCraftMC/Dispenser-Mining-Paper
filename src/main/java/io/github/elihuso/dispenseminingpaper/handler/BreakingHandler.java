package io.github.elihuso.dispenseminingpaper.handler;

import io.github.elihuso.dispenseminingpaper.config.ConfigManager;
import io.github.elihuso.dispenseminingpaper.data.DummyItemData;
import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class BreakingHandler implements Listener {
    private final ConfigManager configManager;

    public BreakingHandler(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onDispenser(BlockDispenseEvent event) {
        if (event.isCancelled()) {
            return;
        }

        var block = event.getBlock();
        if (block.getType() != Material.DISPENSER) {
            return;
        }

        if (!(block.getState() instanceof Dispenser dispenser)) {
            return;
        }

        var data = MiningDispenserData.get(dispenser);
        if (data == null) {
            return;
        }

        if (data.getType() != MiningDispenserData.Type.BREAKER) {
            return;
        }

        if (!configManager.breakingEnabled()) {
            return;
        }

        var front = block.getRelative(((Directional) block.getBlockData()).getFacing());
        var item = event.getItem();

        if (front.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        var result = front.breakNaturally(item, true, true);
        if (!result) {
            event.setCancelled(true);
            return;
        }

        DummyItemData.set(item, new DummyItemData(block.getLocation(), true, false));
        event.setItem(item);
    }
}
