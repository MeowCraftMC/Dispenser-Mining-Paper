package io.github.elihuso.dispenseminingpaper;

import io.github.elihuso.dispenseminingpaper.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DispenserMiningPaper extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlantListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockProcessListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBoneMealListener(this), this);
        getLogger().log(Level.FINE, "Dispenser Mining Plugin Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}