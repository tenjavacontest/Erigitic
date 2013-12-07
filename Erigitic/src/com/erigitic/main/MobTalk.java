package com.erigitic.main;

import com.erigitic.listener.MTListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;

public class MobTalk extends JavaPlugin {

    private final String VERSION = "Version 1.0.0";
    private final String mainDir = "plugins/MobTalk/";

    private File mobLoc = new File(mainDir + "MobLoc.loc");

    private MTListener mtListener;
    private FileReader reader;

    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();

        getLogger().info("MobTalk " + VERSION + " Successfully Enabled.");

        //Creates a file for storing mob locations
        new File(mainDir).mkdir();

        if (!mobLoc.exists()) {

            try {
                mobLoc.createNewFile();
            } catch (IOException ex) {

            }
        }

        reader = new FileReader(this);
        mtListener = new MTListener(this, reader);

        pm.registerEvents(mtListener, this);

    }

    public void onDisable() {

        getLogger().info("MobTalk " + VERSION + " Successfully Disabled.");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

        Player p = (Player) sender;

        if (cmdLabel.equalsIgnoreCase("create")) {//Spawns mob. Will allow for sender to choose mob of choice

            if (args.length == 1) {
                Location loc = p.getLocation();
                World world = loc.getWorld();
                Villager v = world.spawn(loc, Villager.class);

                v.setCustomName(args[0]);
                reader.writeMobMessage(args[0], "", mobLoc);

                v.addPotionEffect(new PotionEffect((PotionEffectType.JUMP), 999999, 128));
                v.addPotionEffect(new PotionEffect((PotionEffectType.SLOW), 999999, 6));
            }

        } else if (cmdLabel.equalsIgnoreCase("message")) {

            if (args[0] != null) {

                reader.writeMobMessage(mtListener.getMobName(), args[0], mobLoc);

            }

        }

        return true;
    }

    public File getMobLoc() {

        return mobLoc;
    }

}
