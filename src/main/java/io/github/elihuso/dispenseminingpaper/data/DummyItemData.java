package io.github.elihuso.dispenseminingpaper.data;

import io.github.elihuso.dispenseminingpaper.utility.DataHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static io.github.elihuso.dispenseminingpaper.PluginConstants.modLoc;

@Getter
@Setter
@AllArgsConstructor
public class DummyItemData {
    private static final Serializer SERIALIZER = new Serializer();
    private static final NamespacedKey KEY_DATA_TYPE = modLoc("dummy_item");

    private Location parent;

    public static @Nullable DummyItemData get(ItemStack stack) {
        return DataHelper.get(stack, KEY_DATA_TYPE, SERIALIZER);
    }

    public static void set(ItemStack stack, @Nullable DummyItemData data) {
        DataHelper.set(stack, KEY_DATA_TYPE, SERIALIZER, data);
    }

    private static class Serializer implements PersistentDataType<PersistentDataContainer, DummyItemData> {
        private static final NamespacedKey KEY_PARENT = modLoc("parent");
        private static final NamespacedKey KEY_PARENT_WORLD = modLoc("world");
        private static final NamespacedKey KEY_PARENT_X = modLoc("x");
        private static final NamespacedKey KEY_PARENT_Y = modLoc("y");
        private static final NamespacedKey KEY_PARENT_Z = modLoc("z");

        @Override
        public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public @NotNull Class<DummyItemData> getComplexType() {
            return DummyItemData.class;
        }

        @Override
        public @NotNull PersistentDataContainer toPrimitive(@NotNull DummyItemData complex, @NotNull PersistentDataAdapterContext context) {
            var container = context.newPersistentDataContainer();
            var parent = context.newPersistentDataContainer();
            parent.set(KEY_PARENT_WORLD, STRING, complex.getParent().getWorld().getKey().asString());
            parent.set(KEY_PARENT_X, INTEGER, complex.getParent().getBlockX());
            parent.set(KEY_PARENT_Y, INTEGER, complex.getParent().getBlockY());
            parent.set(KEY_PARENT_Z, INTEGER, complex.getParent().getBlockZ());
            container.set(KEY_PARENT, TAG_CONTAINER, parent);
            return container;
        }

        @Override
        public @NotNull DummyItemData fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
            var parent = Objects.requireNonNull(primitive.get(KEY_PARENT, TAG_CONTAINER));
            var worldKey = NamespacedKey.fromString(Objects.requireNonNull(parent.get(KEY_PARENT_WORLD, STRING)));
            var world = Bukkit.getWorld(Objects.requireNonNull(worldKey));
            var x = Objects.requireNonNull(parent.get(KEY_PARENT_X, INTEGER));
            var y = Objects.requireNonNull(parent.get(KEY_PARENT_Y, INTEGER));
            var z = Objects.requireNonNull(parent.get(KEY_PARENT_Z, INTEGER));
            return new DummyItemData(new Location(world, x, y, z));
        }
    }
}
