package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MissionRestart;
import com.iridium.iridiumskyblock.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        try {
            if (e.getEntity().getKiller() == null) return;
            if (e.getEntity().getKiller().getPlayer() == null) return;
            Island island = User.getUser(e.getEntity().getKiller().getPlayer()).getIsland();
            if (island != null) {
                if (island.hunter > -1) {
                    island.hunter++;
                    if (island.hunter >= IridiumSkyblock.getMissions().hunter.getAmount()) {
                        island.hunter = IridiumSkyblock.getConfiguration().missionRestart == MissionRestart.Instantly ? 0 : -1;
                        island.completeMission("Hunter", IridiumSkyblock.getMissions().hunter.getReward());
                    }
                }
                if (island.getExpBooster() != 0) {
                    e.setDroppedExp(e.getDroppedExp() * 2);
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}
