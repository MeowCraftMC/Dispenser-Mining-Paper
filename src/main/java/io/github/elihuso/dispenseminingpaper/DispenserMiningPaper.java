package io.github.elihuso.dispenseminingpaper;

import io.github.elihuso.dispenseminingpaper.config.ConfigManager;
import io.github.elihuso.dispenseminingpaper.handler.BreakingHandler;
import io.github.elihuso.dispenseminingpaper.handler.DummyHandler;
import io.github.elihuso.dispenseminingpaper.utility.ItemStackHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

import static io.github.elihuso.dispenseminingpaper.PluginConstants.modLoc;

@Setter
@Getter
public final class DispenserMiningPaper extends JavaPlugin {

    private ConfigManager configManager = new ConfigManager(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

        addRecipes();

        getServer().getPluginManager().registerEvents(new DummyHandler(this), this);
        getServer().getPluginManager().registerEvents(new BreakingHandler(configManager), this);

//        for (String v : paths) {
//            loadSpecificConfig(configFile, v);
//        }
//        if (!Utils.LocalConfigs.enabled)
//            return;
//        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
//        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
//        Bukkit.getPluginManager().registerEvents(new BlockBoneMealListener(this), this);
//        if (Utils.LocalConfigs.plantCrops) Bukkit.getPluginManager().registerEvents(new BlockPlantListener(this), this);
//        if (Utils.LocalConfigs.processByDropper)
//            Bukkit.getPluginManager().registerEvents(new BlockProcessListener(this), this);
//        getLogger().log(Level.FINE, "Dispenser Mining Plugin Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        removeRecipes();
    }

    private void addRecipes() {
        {
            var recipe = new ShapedRecipe(modLoc("breaker"), ItemStackHelper.createBreaker())
                    .shape("CCC",
                            "CPC",
                            "CRC")
                    .setIngredient('C', Material.COBBLESTONE)
                    .setIngredient('P', Material.DIAMOND_PICKAXE)
                    .setIngredient('R', Material.REDSTONE);
            recipe.setCategory(CraftingBookCategory.REDSTONE);
            recipe.setGroup("mining_dispenser");
            getServer().addRecipe(recipe, true);
        }

        {
            var recipe = new ShapedRecipe(modLoc("placer"), ItemStackHelper.createPlacer())
                    .shape("CCC",
                            "CPC",
                            "CRC")
                    .setIngredient('C', Material.COBBLESTONE)
                    .setIngredient('P', Material.FISHING_ROD)
                    .setIngredient('R', Material.REDSTONE);
            recipe.setCategory(CraftingBookCategory.REDSTONE);
            recipe.setGroup("mining_dispenser");
            getServer().addRecipe(recipe, true);
        }
    }

    private void removeRecipes() {
        getServer().removeRecipe(modLoc("breaker"));
        getServer().removeRecipe(modLoc("placer"));
    }
}