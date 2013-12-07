package com.erigitic.main;

import com.erigitic.listener.MTListener;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.EntityVillager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobTalk extends JavaPlugin {

    private final String VERSION = "Version 1.0.0";

    private MTListener mtListener;

    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();

        getLogger().info("MobTalk " + VERSION + " Succesfully Enabled.");

        mtListener = new MTListener(this);

        pm.registerEvents(mtListener, this);

    }

    public void onDisable() {

        getLogger().info("MobTalk " + VERSION + " Succesfully Disabled.");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

        Player p = (Player) sender;

        if (cmdLabel.equalsIgnoreCase("test")) {

            Location loc = p.getLocation();
            World world = loc.getWorld();
            Villager v = world.spawn(loc, Villager.class);

            v.addPotionEffect(new PotionEffect((PotionEffectType.JUMP), 999999, 128));
            v.addPotionEffect(new PotionEffect((PotionEffectType.SLOW), 999999, 6));

        }

        return false;
    }

}
