package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.managers.MuteManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Created by brand on 11/28/2015.
 */
public class Unmute implements CommandExecutor {

    private final MuteManager muteManager;
    private final Permissions permissions;
    private final Messages messages;

    public Unmute(MuteManager muteManager, Permissions permissions, Messages messages) {
        this.muteManager = muteManager;
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
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "Invalid usage, /unmute (player)");
            return false;
        }

        if ((!muteManager.isMuted(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) && (!muteManager.isMuted(Bukkit.getPlayer(args[0]).getUniqueId()))) {
            sender.sendMessage(Messages.MessageType.ERROR.getText() + "There is no player with the name " + args[0] + " that has joined the server!");
            return false;
        }

        UUID player = null;

        if (Bukkit.getOfflinePlayer(args[0]) == null) {
            player = Bukkit.getPlayer(args[0]).getUniqueId();
            Bukkit.getPlayer(args[0]).sendMessage(Messages.MessageType.ALERT.getText() + "You have been unmuted!");
        } else {
            player = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
        }

        muteManager.unmutePlayer(player);
        sender.sendMessage("You have unmuted " + args[0]);
        messages.broadCastToPunishers(sender.getName() + " has unmuted " + args[0]);

        return false;
    }

}
