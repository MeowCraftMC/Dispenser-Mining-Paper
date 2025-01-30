package io.github.elihuso.dispenseminingpaper.utility;

import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public class DataHelper {
    public static <P, C> @Nullable C get(ItemStack stack, NamespacedKey id, PersistentDataType<P, C> type) {
        var container = stack.getPersistentDataContainer();
        if (!container.has(id)) {
            return null;
        }

        return container.get(id, type);
    }

    public static <P, C> void set(ItemStack stack, NamespacedKey id, PersistentDataType<P, C> type, @Nullable C value) {
        var meta = stack.getItemMeta();
        var container = meta.getPersistentDataContainer();
        if (value == null) {
            container.remove(id);
            return;
        }
        container.set(id, type, value);
        stack.setItemMeta(meta);
    }

    public static <P, C> @Nullable C get(TileState state, NamespacedKey id, PersistentDataType<P, C> type) {
        var container = state.getPersistentDataContainer();
        if (!container.has(id)) {
            return null;
        }

        return container.get(id, type);
    }

    public static <P, C> void set(TileState state, NamespacedKey id, PersistentDataType<P, C> type, @Nullable C value) {
        var container = state.getPersistentDataContainer();
        if (value == null) {
            container.remove(id);
            return;
        }
        container.set(id, type, value);
    }
}
