package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.managers.BanManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by brand on 11/28/2015.
 */
public class Unban implements CommandExecutor {

    private final BanManager banManager;
    private final Permissions permissions;
    private final Messages messages;

    public Unban(BanManager banManager, Permissions permissions, Messages messages) {
        this.banManager = banManager;
        this.permissions = permissions;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission(permissions.getKyraPunish())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "You don't have permission to run this command!");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, /unban (username/UUID)");
            return false;
        }

        OfflinePlayer banPlayer = Bukkit.getOfflinePlayer(args[0]);

        if (!banManager.isBan(banPlayer.getUniqueId())) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "This player is not banned!");
            return false;
        }

        banManager.unBan(banPlayer.getUniqueId());
        sender.sendMessage(Messages.MessageType.ALERT.getText() + "You unbanned " + args[0]);
        messages.broadCastToPunishers("Player " + banPlayer.getName() + " has been unbanned by " + sender.getName());

        return true;
    }

}
