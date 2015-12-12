package me.bman7842.legacyban.managers;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by brand on 11/28/2015.
 */
public class MuteManager {

    private ConfigManager mutes;

    private boolean chatMuted = false;
    private HashMap<UUID, String> mutedPlayers = new HashMap<UUID, String>();

    public MuteManager(ConfigManager mutes) {
        this.mutes = mutes;

        for (String puuid : mutes.getKeys()) {
            mutedPlayers.put(UUID.fromString(puuid), mutes.get(puuid + ".whomutedplayer"));
        }
    }

    public void saveMutes() {
        for (UUID pUUID : mutedPlayers.keySet()) {
            mutes.set(pUUID.toString() + ".whomutedplayer", mutedPlayers.get(pUUID));
        }
    }

    public void mutePlayer(UUID player, String whoMutedplayer) {
        if (!mutedPlayers.containsKey(player)) {
            mutedPlayers.put(player, whoMutedplayer);
        }
    }

    public boolean isMuted(UUID player) {
        return mutedPlayers.containsKey(player);
    }

    public String getWhoMuted(UUID player) {
        return (isMuted(player) ? mutedPlayers.get(player) : null);
    }

    public void unmutePlayer(UUID player) {
        if (isMuted(player)) {
            mutedPlayers.remove(player);
            mutes.set(player.toString(), null);
        }
    }

    public boolean isChatMuted() { return chatMuted; }
    public void setChatMuted(boolean value) { chatMuted = value; }
}
