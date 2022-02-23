package me.playajames.unitedreclaim;

import me.gleeming.command.CommandHandler;
import me.playajames.unitedreclaim.commands.ReclaimCommand;
import me.playajames.unitedreclaim.gui.ReclaimView;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class UnitedReclaim extends JavaPlugin {

    public static Logger LOGGER;
    private ViewFrame viewFrame;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        saveDefaultConfig();
        registerViews();
        CommandHandler.registerCommands(ReclaimCommand.class, this);
        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled.");
    }

    private void registerViews() {
        viewFrame = new ViewFrame(this);
        viewFrame.addView(ReclaimView.getInstance());
        viewFrame.register();
    }

}
