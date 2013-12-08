package com.erigitic.listener;

import com.erigitic.main.FileReader;
import com.erigitic.main.VillagerTalk;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class VTListener implements Listener {

    private boolean editing;
    private String mobName;

    private HashMap<String, Player> players = new HashMap<String, Player>();

    private VillagerTalk plugin;
    private FileReader reader;

    public VTListener(VillagerTalk plugin, FileReader reader) {

        this.plugin = plugin;
        this.reader = reader;

    }

    @EventHandler
    public void onMobDamage(EntityDamageEvent event) {//Prevents VillagerTalk mobs from being damaged

        if (event.getEntity().getType() == EntityType.VILLAGER) {

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();

        if (event.getRightClicked() instanceof  Villager) {
            Villager v = (Villager) event.getRightClicked();
            mobName = v.getCustomName();


            if (event.getRightClicked().getType() == EntityType.VILLAGER && v.getCustomName() != null && p.isSneaking() && p.isOp()) {

                p.sendMessage("");
                p.sendMessage(ChatColor.GRAY + "You are now editing " + ChatColor.RED + v.getCustomName());
                p.sendMessage("");
                p.sendMessage(ChatColor.GRAY + "/message <message> - " + ChatColor.RED + "Sets the entities message.");
                p.sendMessage(ChatColor.GRAY + "/item <item> - " + ChatColor.RED + "Set item to give.");

                editing = true;
                mobName = v.getCustomName();

                event.setCancelled(true);

            } else if (event.getRightClicked().getType() == EntityType.VILLAGER && v.getCustomName() != null && (!players.containsValue(p) || !players.containsKey(mobName) || !players.get(mobName).equals(p) )) {

                mobName = v.getCustomName();

                p.sendMessage(ChatColor.RED + mobName + ": " + ChatColor.GRAY + reader.readMobMessage(mobName, plugin.getMobLoc()));

                try {

                    p.getInventory().addItem(new ItemStack(reader.readMobItem(mobName + "i", plugin.getMobLoc())));
                    players.put(mobName, p);

                } catch (IllegalArgumentException e) {

                }

                event.setCancelled(true);

            } else if (event.getRightClicked().getType() == EntityType.VILLAGER && v.getCustomName() != null && players.get(mobName).equals(p)) {

                mobName = v.getCustomName();

                p.sendMessage(ChatColor.RED + mobName + ": " + ChatColor.GRAY + reader.readMobMessage(mobName, plugin.getMobLoc()));

                event.setCancelled(true);

            }
        }

    }

    public String getMobName() {

        return mobName;
    }

}
