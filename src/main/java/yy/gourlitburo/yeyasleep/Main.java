package yy.gourlitburo.yeyasleep;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
    Logger logger = getLogger();
    Server server = getServer();
    PluginManager manager = Bukkit.getPluginManager();

    private Map<UUID, Double> sleepingCount = new HashMap<>(); // TODO: change this to a ConcurrentHashMap
    private Map<UUID, Boolean> isAdvancing = new ConcurrentHashMap<>();

    void setSleepingCount(World world, double direction) {
        UUID worldId = world.getUID();
        Double count = sleepingCount.get(worldId);
        if (count == null) sleepingCount.put(worldId, direction);
        else sleepingCount.put(worldId, count + direction);
        checkSleepingCount(world);
    }

    void checkSleepingCount(World world) {
        UUID worldId = world.getUID();
        double thresholdProportion = getConfig().getDouble("proportion");
        double playerCount = world.getPlayers().size();
        // double playerCount = 3;
        double sleepingProportion = sleepingCount.get(worldId) / playerCount;
        logger.info(String.format("Player count in world is %s", playerCount));
        logger.info(String.format("Sleeping proportion in world is %f", sleepingProportion));
        if (sleepingProportion >= thresholdProportion && sleepingProportion < 1 && isAdvancing.putIfAbsent(worldId, true) == null) {
            advanceDay(world);
        }
    }

    private void advanceDay(World world) {
        logger.info("Waiting to advance day...");
        BukkitRunnable task = new BukkitRunnable(){
            @Override
            public void run() {
                logger.info("Advance day.");
                world.setTime(0); // is this right?
                world.setStorm(false);
                world.setThundering(false);
                for (Player player : world.getPlayers()) {
                    if (player.isSleeping()) {
                        player.wakeup(false);
                    }
                }
                isAdvancing.remove(world.getUID());
            }
        };
        task.runTaskLater(this, 5 * 20);
    }

    @Override
    public void onEnable() {
        getCommand("yeyasleep").setExecutor(new CommandHandler(this));
        manager.registerEvents(new PlayerEventHandler(this), this);
        saveDefaultConfig();

        logger.info("YeyaSleep ready.");
    }
}
