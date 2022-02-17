package me.playajames.unitedreclaim.commands;

import me.gleeming.command.Command;
import me.playajames.unitedreclaim.gui.ReclaimView;
import org.bukkit.entity.Player;

public class ReclaimCommand {

    @Command(names = {"reclaim", "urc"}, permission = "unitedreclaim.gui.access", playerOnly = true)
    public void reclaimCommand(Player player) {
        ReclaimView.getInstance().open(player);
    }

}
