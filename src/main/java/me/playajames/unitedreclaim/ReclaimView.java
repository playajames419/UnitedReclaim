package me.playajames.unitedreclaim;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import static me.playajames.unitedreclaim.UnitedReclaim.LOGGER;

public class ReclaimView extends ChestGui {

    private static ReclaimView instance = null;

    private ReclaimView(ConfigurationSection config) {
        super(config.getInt("rows"), config.getString("title"));

    }

    public static ReclaimView getInstance() {
        if (instance == null) {
            ConfigurationSection config = UnitedReclaim.getPlugin(UnitedReclaim.class).getConfig().getConfigurationSection("gui");
            if (validGuiConfig(config))
                instance = new ReclaimView(config);
        }
        return instance;
    }

    private static boolean validGuiConfig(ConfigurationSection config) {
        if (!config.isInt("rows")) {
            LOGGER.warning("Uh oh, gui.rows must be an integer in config.yml.");
            return false;
        }

        if (!config.isString("title")) {
            LOGGER.warning("Uh oh, gui.title must be a string in config.yml.");
            return false;
        }
        return true;
    }

}
