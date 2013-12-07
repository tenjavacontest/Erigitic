package com.erigitic.listener;

import com.erigitic.main.MobTalk;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MTListener implements Listener {

    private MobTalk plugin;

    public MTListener(MobTalk plugin) {

        this.plugin = plugin;

    }

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {

        if (event.getEntity().getType() == EntityType.VILLAGER) {

            event.setCancelled(true);

        }

    }

}
