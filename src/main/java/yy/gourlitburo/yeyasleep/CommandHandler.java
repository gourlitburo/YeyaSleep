package yy.gourlitburo.yeyasleep;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
// import org.bukkit.command.ConsoleCommandSender;
// import org.bukkit.entity.Player;

class CommandHandler implements CommandExecutor {
    private Main plugin;

    public CommandHandler(Main instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.logger.info("YeyaSleep reloaded.");
            return true;
        // } else if (args[0].equalsIgnoreCase("advance")) {
        //     if (sender instanceof ConsoleCommandSender) return false;
        //     Player player = (Player) sender;
        //     plugin.advanceDay(player.getWorld());
        //     return true;
        }
        return false;
    }
}
