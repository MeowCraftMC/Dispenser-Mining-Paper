package io.github.elihuso.dispenseminingpaper;

import io.github.elihuso.dispenseminingpaper.listener.*;
import io.github.elihuso.dispenseminingpaper.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class DispenserMiningPaper extends JavaPlugin {

    String configFile = this.getDataFolder() + "/config";
    String[] paths = {
            "enabled",
            "allowNegativeTools",
            "plantCrops",
            "breakBedrocks",
            "processByDropper"
    };

    @Override
    public void onEnable() {
        // Plugin startup logic
        for (String v : paths) {
            loadSpecificConfig(configFile, v);
        }
        if (!Utils.LocalConfigs.enabled)
            return;
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBoneMealListener(this), this);
        if (Utils.LocalConfigs.plantCrops) Bukkit.getPluginManager().registerEvents(new BlockPlantListener(this), this);
        if (Utils.LocalConfigs.processByDropper)
            Bukkit.getPluginManager().registerEvents(new BlockProcessListener(this), this);
        getLogger().log(Level.FINE, "Dispenser Mining Plugin Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadSpecificConfig(@NotNull String file, @NotNull String path) {
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (Exception ex) {
            for (String v : paths) {
                config.set(v, true);
            }
            try {
                config.save(file);
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "Failed to load/save config file");
            }
        }
        if (!config.isSet(path))
            return;
        boolean value = config.getBoolean(path);
        switch (path) {
            case "enabled":
                Utils.LocalConfigs.enabled = value;
                break;
            case "allowNegativeTools":
                Utils.LocalConfigs.allowNegativeTools = value;
                break;
            case "plantCrops":
                Utils.LocalConfigs.plantCrops = value;
                break;
            case "breakBedrocks":
                Utils.LocalConfigs.breakBedrocks = value;
                break;
            case "processByDropper":
                Utils.LocalConfigs.processByDropper = value;
                break;
            default:
        }
    }
}