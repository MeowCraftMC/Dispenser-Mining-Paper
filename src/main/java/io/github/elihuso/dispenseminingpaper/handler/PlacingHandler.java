package io.github.elihuso.dispenseminingpaper.handler;

import io.github.elihuso.dispenseminingpaper.config.ConfigManager;
import io.github.elihuso.dispenseminingpaper.data.DummyItemData;
import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import io.github.elihuso.dispenseminingpaper.handler.place.IPlacer;
import io.github.elihuso.dispenseminingpaper.handler.place.NormalBlockPlacer;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class PlacingHandler implements Listener {
    private static final IPlacer NORMAL_PLACER = new NormalBlockPlacer();

    private final ConfigManager configManager;

    public PlacingHandler(ConfigManager configManager) {
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

        if (data.getType() != MiningDispenserData.Type.PLACER) {
            return;
        }

        if (!configManager.placingEnabled()) {
            return;
        }

        var front = block.getRelative(((Directional) block.getBlockData()).getFacing());
        var item = event.getItem();

        if (!front.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        if (NORMAL_PLACER.canPlace(dispenser, item, front)) {
            NORMAL_PLACER.place(dispenser, item, front);
            DummyItemData.set(item, new DummyItemData(block.getLocation(), false, true));
            event.setItem(item);
        } else {
            event.setCancelled(true);
        }
    }
}
