package io.github.elihuso.dispenseminingpaper.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager(Plugin plugin) {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public boolean placingEnabled() {
        return config.getBoolean("placing.enabled", true);
    }

    public boolean breakingEnabled() {
        return config.getBoolean("breaking.enabled", true);
    }
}
