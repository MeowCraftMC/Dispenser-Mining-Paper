package io.github.elihuso.dispenseminingpaper.data;

import io.github.elihuso.dispenseminingpaper.utility.DataHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
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
public class MiningDispenserData {
    private static final Serializer SERIALIZER = new Serializer();
    private static final NamespacedKey KEY_DATA_TYPE = modLoc("dispenser");

    private Type type;

    public enum Type {
        BREAKER,
        PLACER,
    }

    public static @Nullable MiningDispenserData get(ItemStack stack) {
        return DataHelper.get(stack, KEY_DATA_TYPE, SERIALIZER);
    }

    public static void set(ItemStack stack, @Nullable MiningDispenserData data) {
        DataHelper.set(stack, KEY_DATA_TYPE, SERIALIZER, data);
    }

    public static @Nullable MiningDispenserData get(TileState state) {
        var container = state.getPersistentDataContainer();
        if (!container.has(KEY_DATA_TYPE)) {
            return null;
        }

        return container.get(KEY_DATA_TYPE, SERIALIZER);
    }

    public static void set(TileState state, @Nullable MiningDispenserData data) {
        var container = state.getPersistentDataContainer();
        if (data == null) {
            container.remove(KEY_DATA_TYPE);
            return;
        }
        container.set(KEY_DATA_TYPE, SERIALIZER, data);
    }

    private static class Serializer implements PersistentDataType<PersistentDataContainer, MiningDispenserData> {
        private static final NamespacedKey KEY_TYPE = modLoc("type");

        @Override
        public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public @NotNull Class<MiningDispenserData> getComplexType() {
            return MiningDispenserData.class;
        }

        @Override
        public @NotNull PersistentDataContainer toPrimitive(@NotNull MiningDispenserData complex, @NotNull PersistentDataAdapterContext context) {
            var container = context.newPersistentDataContainer();
            container.set(KEY_TYPE, STRING, complex.getType().name());
            return container;
        }

        @Override
        public @NotNull MiningDispenserData fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
            var type = Enum.valueOf(MiningDispenserData.Type.class, Objects.requireNonNull(primitive.get(KEY_TYPE, STRING)));
            return new MiningDispenserData(type);
        }
    }
}
