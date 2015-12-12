package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.managers.BanManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by brand on 11/27/2015.
 */
public class Ban implements CommandExecutor {

    private final BanManager banManager;
    private final Permissions permissions;
    private final Messages messages;

    public Ban(BanManager banManager, Permissions permissions, Messages messages) {
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
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, type /ban (name) (reason)");
            return false;
        }

        Player banOnlinePlayer = Bukkit.getPlayer(args[0]);
        OfflinePlayer banOfflinePlayer = Bukkit.getOfflinePlayer(args[0]);

        if ((banOnlinePlayer == null) && (banOfflinePlayer == null)) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "No player with this name has ever joined the server before.");
            return false;
        }

        if (banOnlinePlayer == null) {
            if (banOfflinePlayer.isOp()) {
                sender.sendMessage(Messages.MessageType.ERROR.getText() + "You cannot ban this player!");
                return false;
            }
        } else {
            if ((banOnlinePlayer.isOp()) || (banOnlinePlayer.hasPermission(permissions.getKyraSafe()))) {
                sender.sendMessage(Messages.MessageType.ERROR.getText() + "You cannot ban this player!");
                return false;
            }
        }

        String banMSG = StringUtils.join(args, " ", 2, args.length);

        banManager.banPlayer(args[0], sender.getName(), banMSG);
        sender.sendMessage(Messages.MessageType.ALERT.getText() + "You have banned " + args[0]);
        messages.broadCastToPunishers(args[0] + " has been banned by " + sender.getName() + ", reason: " + banMSG + ", time: FOREVER");

        return false;

    }
}
