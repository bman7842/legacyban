package me.bman7842.legacyban.commands;

import me.bman7842.legacyban.events.ChatEvent;
import me.bman7842.legacyban.managers.MuteManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by brand on 12/10/2015.
 */
public class MuteChat implements CommandExecutor {

    private final Permissions permissions;
    private final MuteManager muteManager;

    public MuteChat(Permissions permissions, MuteManager muteManager) {
        this.permissions = permissions;
        this.muteManager = muteManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission(permissions.getKyraPunish())) {
            sender.sendMessage(Messages.MessageType.ERROR.toString() + "You don't have permission to run this command!");
            return false;
        }

        if (muteManager.isChatMuted()) {
            Bukkit.broadcastMessage(Messages.MessageType.STAFFBROADCAST.toString() + "The chat has been unmuted by " + sender.getName() + "!");
            muteManager.setChatMuted(false);
        } else {
            Bukkit.broadcastMessage(Messages.MessageType.STAFFBROADCAST.toString() + "The chat has been muted by " + sender.getName() + "!");
            muteManager.setChatMuted(true);
        }

        return false;
    }

}
