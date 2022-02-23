package me.playajames.unitedreclaim.gui;

import me.playajames.unitedreclaim.UnitedReclaim;
import me.saiintbrisson.minecraft.View;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

import static me.playajames.unitedreclaim.UnitedReclaim.LOGGER;

public class ReclaimView extends View {

    private static ReclaimView instance = null;

    private ReclaimView(ConfigurationSection config) {
        super(config.getInt("gui.rows"), config.getString("gui.title"));
        this.build(config);
    }

    public static ReclaimView getInstance() {
        if (instance == null)
            instance = new ReclaimView(UnitedReclaim.getPlugin(UnitedReclaim.class).getConfig());
        return instance;
    }

    private void build(ConfigurationSection config) {
        if (!config.isConfigurationSection("reclaims")) {
            LOGGER.warning("reclaims must be defined in the config.yml file");
            return;
        }

        Set<String> reclaimKeys = config.getConfigurationSection("reclaims").getKeys(false);
        for (String reclaimKey : reclaimKeys) {
            ConfigurationSection reclaimSection = config.getConfigurationSection("reclaims." + reclaimKey);
            ItemStack item = new ItemStack(Material.valueOf(reclaimSection.getString("gui-item.item")));
            if (reclaimSection.isString("gui-item.display-name")) {
                ItemMeta meta = item.getItemMeta();
                if (reclaimSection.getString("gui-item.display-name").equalsIgnoreCase("null"))
                    meta.setDisplayName(" ");
                else
                    meta.setDisplayName(reclaimSection.getString("gui-item.display-name"));
                item.setItemMeta(meta);
            }

            slot(reclaimSection.getInt("gui-item.slot"), item)
                    .onClick(context -> {
                        context.setCancelled(true);
                        Player player = context.getPlayer();
                        if (reclaimSection.isConfigurationSection("permission") && !player.hasPermission(reclaimSection.getString("permission"))) {
                            close();
                            player.sendMessage(config.getString("message-prefix") + config.getString("deny-permission-message"));
                            return;
                        }

                        if (!reclaimSection.isList("rewards"))
                            return;

                        List<String> rewards = (List<String>) reclaimSection.getList("rewards");

                        for (String reward : rewards) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), populateArguments(player, reward));
                        }
                        close();
                    });
        }
    }

    private String populateArguments(Player player, String string) {
        String[] args = {"$player.name", "$message.prefix"};

        for(String arg : args) {
            switch(arg) {
                case "$player.name":
                    if (string.contains(arg))
                        string = string.replace(arg, player.getName());
                    break;
                case "$message.prefix":
                    if (string.contains(arg))
                        string = string.replace(arg, UnitedReclaim.getPlugin(UnitedReclaim.class).getConfig().getString("message-prefix"));
                    break;
            }
        }

        return string;
    }
}
