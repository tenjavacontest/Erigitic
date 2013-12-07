package com.erigitic.listener;

import com.erigitic.main.FileReader;
import com.erigitic.main.MobTalk;
import net.minecraft.server.v1_7_R1.EntityVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MTListener implements Listener {

    private boolean editing;
    private String mobName;

    private MobTalk plugin;
    private FileReader reader;

    public MTListener(MobTalk plugin, FileReader reader) {

        this.plugin = plugin;
        this.reader = reader;

    }

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {//Prevents MobTalk mobs from being damaged

        if (event.getEntity().getType() == EntityType.VILLAGER) {

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();

        if (event.getRightClicked().getType() == EntityType.VILLAGER && p.isSneaking() == true && p.isOp()) {

            p.sendMessage("----MobTalk Edit Mode----");
            p.sendMessage("-Commands-");
            p.sendMessage("/message <message> - Sets the message displayed when clicked.");

            editing = true;
            Villager v = (Villager) event.getRightClicked();
            mobName = v.getCustomName();

            event.setCancelled(true);

        } else if (event.getRightClicked().getType() == EntityType.VILLAGER) {

            p.sendMessage(reader.readMobMessage(mobName, plugin.getMobLoc()));

            event.setCancelled(true);

        }

    }

    public void onChatSend(AsyncPlayerChatEvent event) {

        Player p = event.getPlayer();
        String msg = event.getMessage();

        if (editing == true) {

            editing = false;
            event.setCancelled(true);

        }

    }

    public String getMobName() {

        return mobName;
    }

}
