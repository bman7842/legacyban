package me.bman7842.legacyban.events;

import me.bman7842.legacyban.managers.BanManager;
import me.bman7842.legacyban.utils.AdvancedMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

/**
 * Created by brand on 11/27/2015.
 */
public class JoinEvent implements Listener {

    private final BanManager banManager;
    private final AdvancedMessages advancedMessages;

    public JoinEvent(BanManager banManager, AdvancedMessages advancedMessages) {
        this.banManager = banManager;
        this.advancedMessages = advancedMessages;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void pJoinEvent(AsyncPlayerPreLoginEvent event) {
        UUID pUUID = event.getUniqueId();

        if (banManager.isBan(pUUID)) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            if (banManager.getTime(pUUID) != null) {
                event.setKickMessage(advancedMessages.createBanMessage(banManager.getReason(pUUID), banManager.getWhoBannedPlayer(pUUID), banManager.getTime(pUUID)));
            } else {
                event.setKickMessage(advancedMessages.createBanMessage(banManager.getReason(pUUID), banManager.getWhoBannedPlayer(pUUID), null));
            }
        }
    }

}
