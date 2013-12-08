package com.erigitic.main;

import com.erigitic.listener.MTListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
                reader.writeMobMessage(args[0], "No message set", mobLoc);
                reader.writeMobLoc(args[0], loc, mobLoc);

                v.addPotionEffect(new PotionEffect((PotionEffectType.JUMP), 999999, 128));
                v.addPotionEffect(new PotionEffect((PotionEffectType.SLOW), 999999, 6));
            }

        } else if (cmdLabel.equalsIgnoreCase("message")) {

            if (args[0] != null) {
                String msg = args[0];

                for (int i = 1; i < args.length; i++) {

                    msg += " " + args[i];

                }

                reader.writeMobMessage(mtListener.getMobName(), msg, mobLoc);

            }

        } else if (cmdLabel.equalsIgnoreCase("item")) {

            if (args[0] != null) {

                reader.writeMobItem(mtListener.getMobName(), Material.getMaterial(args[0]), mobLoc);

            }

        } else if (cmdLabel.equalsIgnoreCase("remove")) {


            if (args[0] != null) {

                Location loc = reader.readMobLoc(args[0], mobLoc);
                p.sendMessage("" + loc);
                List<Entity> entityList = loc.getWorld().getEntities();

                for (int i = 0; i < entityList.size(); i++) {

                    if (entityList.get(i) instanceof Villager) {

                        Villager v = (Villager) entityList.get(i);

                        if (v.getCustomName().equalsIgnoreCase(args[0])) {

                            v.remove();
                            reader.removeMob(args[0], mobLoc);

                        }

                    }

                }

            }

        }

        return true;
    }

    public File getMobLoc() {

        return mobLoc;
    }

}
