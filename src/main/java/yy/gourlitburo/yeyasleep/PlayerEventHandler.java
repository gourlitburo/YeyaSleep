package yy.gourlitburo.yeyasleep;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

class PlayerEventHandler implements Listener {
    private Main plugin;

    public PlayerEventHandler(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() != BedEnterResult.OK) return;
        plugin.logger.info("Enter bed.");
        plugin.setSleepingCount(event.getPlayer().getWorld(), 1);
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        // this seems to be fired as well when player quits
        plugin.logger.info("Leave bed.");
        plugin.setSleepingCount(event.getPlayer().getWorld(), -1);
    }
}
