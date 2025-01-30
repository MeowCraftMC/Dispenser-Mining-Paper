package io.github.elihuso.dispenseminingpaper.handler;

import io.github.elihuso.dispenseminingpaper.data.DummyItemData;
import io.github.elihuso.dispenseminingpaper.data.MiningDispenserData;
import io.github.elihuso.dispenseminingpaper.utility.EntityItemHelper;
import io.github.elihuso.dispenseminingpaper.utility.ItemStackHelper;
import org.bukkit.Bukkit;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

public class DummyHandler implements Listener {

    private final Plugin plugin;

    public DummyHandler(Plugin plugin) {
        this.plugin = plugin;
    }

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

            item.remove();
            if (data.isNoCollect()) {
                return;
            }

            if (data.isWillDamage()) {
                ItemStackHelper.damage(stack, data.getCollectBack().getBlock());
                if (stack.isEmpty()) {
                    return;
                }
            }

            DummyItemData.set(stack, null);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                var loc = data.getCollectBack();
                var state = loc.getBlock().getState();
                if (state instanceof Container container) {
                    var inv = container.getInventory();
                    var r = inv.addItem(stack);
                    for (var i : r.values()) {
                        EntityItemHelper.dropItem(item, i);
                    }
                } else {
                    EntityItemHelper.dropItem(item, stack);
                }
            }, 1);
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

    @EventHandler
    public void onBlockBreak(BlockDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        var state = event.getBlockState();
        if (!(state instanceof TileState tile)) {
            return;
        }

        var data = MiningDispenserData.get(tile);
        if (data != null) {
            var item = event.getItems();
            for (var e : item) {
                var i = e.getItemStack();
                if (i.getType() == state.getType()) {
                    MiningDispenserData.set(i, data);
                    e.setItemStack(i);
                }
            }
        }
    }
}
