package me.bman7842.legacyban.events;

import me.bman7842.legacyban.managers.MuteManager;
import me.bman7842.legacyban.utils.Messages;
import me.bman7842.legacyban.utils.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by brand on 11/28/2015.
 */
public class ChatEvent implements Listener {

    private final MuteManager muteManager;
    private final Permissions permissions;
    private boolean chatMuted = false;

    public ChatEvent(MuteManager muteManager, Permissions permissions) {
        this.muteManager = muteManager;
        this.permissions = permissions;
    }

    @EventHandler
    public void playerChatted(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

        if (muteManager.isMuted(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (muteManager.getWhoMuted(event.getPlayer().getUniqueId()) == null) {
                event.getPlayer().sendMessage(Messages.MessageType.MUTED.getText() + "You are currently muted");
            } else {
                event.getPlayer().sendMessage(Messages.MessageType.MUTED.getText() + "You were muted by " + muteManager.getWhoMuted(event.getPlayer().getUniqueId()));
            }
        } else if (muteManager.isChatMuted()) {
            if (!p.hasPermission(permissions.getKyraSafe())) {
                event.setCancelled(true);
                p.sendMessage(Messages.MessageType.MUTED.toString() + "Chat is currently muted!");
            }
        }

    }

    public boolean isMuted() { return chatMuted; }

    public void setMuted(boolean value) { chatMuted = value; }

}
