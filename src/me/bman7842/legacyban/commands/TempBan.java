package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.managers.BanManager;
import me.bman7842.legacyban.utils.AdvancedMessages;
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
public class TempBan implements CommandExecutor {

    private final BanManager banManager;
    private final Permissions permissions;
    private final Messages messages;

    public TempBan(BanManager banManager, Permissions permissions, Messages messages) {
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

        if (args.length <= 1) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, type /tempban (name) (time) (reason)");
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
                sender.sendMessage(Messages.MessageType.ERROR.getText() + "You cannot temp ban this player!");
                return false;
            }
        } else {
            if ((banOnlinePlayer.isOp()) || (banOnlinePlayer.hasPermission(permissions.getKyraSafe()))) {
                sender.sendMessage(Messages.MessageType.ERROR.getText() + "You cannot temp ban this player!");
                return false;
            }
        }

        if (stringToTime(args[1]) == null) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "You did not set a valid time, ");
            return false;
        }

        String tempBanMSG = StringUtils.join(args, " ", 2, args.length);

        banManager.tempBan(args[0], sender.getName(), stringToTime(args[1]), tempBanMSG);
        sender.sendMessage(Messages.MessageType.ALERT.getText() + "You have tempbanned " + args[0]);
        messages.broadCastToPunishers(args[0] + " has been banned by " + sender.getName() + ", reason: " + tempBanMSG + ", time: " + AdvancedMessages.timeToString(stringToTime(args[1])));

        return false;
    }

    public Integer stringToTime(String strTime) {
        if (strTime.endsWith("y")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 29030400;
        } else if (strTime.endsWith("m")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 2419200;
        } else if (strTime.endsWith("w")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 604800;
        } else if (strTime.endsWith("d")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 86400;
        } else if (strTime.endsWith("h")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 3600;
        } else if (strTime.endsWith("min")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr) * 60;
        } else if (strTime.endsWith("s")) {
            StringBuilder sb = new StringBuilder(strTime);
            sb.deleteCharAt(strTime.length()-1);
            String finalStr = sb.toString();
            return Integer.parseInt(finalStr);
        }
        return null;
    }
}
