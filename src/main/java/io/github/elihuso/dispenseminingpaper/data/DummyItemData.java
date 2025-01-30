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

    private Location collectBack;

    /**
     * True for calc damage.
     * False for not.
     */
    private boolean willDamage;

    /**
     * True for removing the entity.
     * False for collect it back.
     */
    private boolean noCollect;

    public static @Nullable DummyItemData get(ItemStack stack) {
        return DataHelper.get(stack, KEY_DATA_TYPE, SERIALIZER);
    }

    public static void set(@Nullable ItemStack stack, @Nullable DummyItemData data) {
        DataHelper.set(stack, KEY_DATA_TYPE, SERIALIZER, data);
    }

    private static class Serializer implements PersistentDataType<PersistentDataContainer, DummyItemData> {
        private static final NamespacedKey KEY_PARENT = modLoc("collect_back");
        private static final NamespacedKey KEY_PARENT_WORLD = modLoc("world");
        private static final NamespacedKey KEY_PARENT_X = modLoc("x");
        private static final NamespacedKey KEY_PARENT_Y = modLoc("y");
        private static final NamespacedKey KEY_PARENT_Z = modLoc("z");
        private static final NamespacedKey KEY_WILL_DAMAGE = modLoc("will_damage");
        private static final NamespacedKey KEY_TO_REMOVE = modLoc("no_collect");

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
            parent.set(KEY_PARENT_WORLD, STRING, complex.getCollectBack().getWorld().getKey().asString());
            parent.set(KEY_PARENT_X, INTEGER, complex.getCollectBack().getBlockX());
            parent.set(KEY_PARENT_Y, INTEGER, complex.getCollectBack().getBlockY());
            parent.set(KEY_PARENT_Z, INTEGER, complex.getCollectBack().getBlockZ());
            container.set(KEY_PARENT, TAG_CONTAINER, parent);

            container.set(KEY_WILL_DAMAGE, BOOLEAN, complex.isWillDamage());
            container.set(KEY_TO_REMOVE, BOOLEAN, complex.isNoCollect());
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

            var damage = Objects.requireNonNull(primitive.get(KEY_WILL_DAMAGE, BOOLEAN));
            var toRemove = Objects.requireNonNull(primitive.get(KEY_TO_REMOVE, BOOLEAN));

            return new DummyItemData(new Location(world, x, y, z), damage, toRemove);
        }
    }
}
