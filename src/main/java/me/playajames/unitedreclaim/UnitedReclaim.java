package me.playajames.unitedreclaim;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class UnitedReclaim extends JavaPlugin {

    public static Logger LOGGER;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        getLogger().info("Plugin has been enabled.");
    }


    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled.");
    }

}
