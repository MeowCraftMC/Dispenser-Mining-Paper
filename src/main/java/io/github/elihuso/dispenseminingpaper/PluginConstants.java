package io.github.elihuso.dispenseminingpaper;

import org.bukkit.NamespacedKey;

public class PluginConstants {
    public static final String NAMESPACE = "mining_dispenser";

    public static NamespacedKey modLoc(String path) {
        return new NamespacedKey(NAMESPACE, path);
    }
}
