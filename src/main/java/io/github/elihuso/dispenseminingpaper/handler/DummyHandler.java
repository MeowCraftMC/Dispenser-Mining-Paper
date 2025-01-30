package io.github.elihuso.dispenseminingpaper.handler;

import io.github.elihuso.dispenseminingpaper.data.DummyItemData;
import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class DummyHandler implements Listener {
    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onItemEntity(EntitySpawnEvent event) {
        if (event.isCancelled()) {
            return;
        }

        var entity = event.getEntity();
        if (entity instanceof Item item) {
            var stack = item.getItemStack().clone();
            var data = DummyItemData.get(stack);
            if (data == null) {
                return;
            }

            var block = data.getParent().getBlock();
            if (block instanceof Container container) {
                var world = entity.getWorld();
                var snapshot = (Item) item.copy(entity.getLocation());
                entity.remove();
                var result = container.getInventory().addItem(stack);
                for (var r : result.values()) {
                    var e = (Item) snapshot.copy();
                    e.setItemStack(r);
                    world.addEntity(e);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled() || !event.canBuild()) {
            return;
        }

        var item = event.getItemInHand();
        var data = MiningDispenserData.get(item);
        if (data == null) {
            return;
        }

        MiningDispenserData.set((TileState) event.getBlockPlaced().getState(), data);
    }
}
