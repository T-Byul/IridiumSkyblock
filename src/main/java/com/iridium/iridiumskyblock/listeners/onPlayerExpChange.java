package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.MissionRestart;
import com.iridium.iridiumskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class onPlayerExpChange implements Listener {

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        try {
            Player p = e.getPlayer();
            User user = User.getUser(p);
            if (user.getIsland() != null) {
                if (user.getIsland().treasureHunter > -1) {
                    user.getIsland().treasureHunter += e.getAmount();
                    if (user.getIsland().treasureHunter >= IridiumSkyblock.getMissions().treasureHunter.getAmount()) {
                        user.getIsland().treasureHunter = IridiumSkyblock.getConfiguration().missionRestart == MissionRestart.Instantly ? 0 : -1;
                        user.getIsland().completeMission("Treasure Hunter", IridiumSkyblock.getMissions().treasureHunter.getReward());
                    }
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}
