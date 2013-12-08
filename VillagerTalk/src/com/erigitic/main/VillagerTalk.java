package com.erigitic.main;

import com.erigitic.listener.VTListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VillagerTalk extends JavaPlugin {

    private final String VERSION = "Version 1.0.0";
    private final String mainDir = "plugins/VillagerTalk/";

    private File mobLoc = new File(mainDir + "MobLoc.loc");

    private VTListener VTListener;
    private FileReader reader;

    public void onEnable() {

        PluginManager pm = getServer().getPluginManager();

        getLogger().info("VillagerTalk " + VERSION + " Successfully Enabled.");

        //Creates a file for storing mob locations
        new File(mainDir).mkdir();

        if (!mobLoc.exists()) {

            try {
                mobLoc.createNewFile();
            } catch (IOException ex) {

            }
        }

        reader = new FileReader(this);
        VTListener = new VTListener(this, reader);

        pm.registerEvents(VTListener, this);

    }

    public void onDisable() {

        getLogger().info("VillagerTalk " + VERSION + " Successfully Disabled.");

    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

        Player p = (Player) sender;


        if (cmdLabel.equalsIgnoreCase("create")) {//Spawns mob. Will allow for sender to choose mob of choice

            if (args.length == 1) {
                if (reader.mobExists(args[0], mobLoc) == false) {
                Location loc = p.getLocation();
                World world = loc.getWorld();
                Villager v = world.spawn(loc, Villager.class);

                v.setCustomName(args[0]);
                reader.writeMobMessage(args[0], "No message set", mobLoc);
                reader.writeMobLoc(args[0], loc, mobLoc);

                v.addPotionEffect(new PotionEffect((PotionEffectType.JUMP), 99999999, 128));
                v.addPotionEffect(new PotionEffect((PotionEffectType.SLOW), 99999999, 6));
                } else {

                    p.sendMessage(ChatColor.RED + "A Villager with that name already exists.");

                }
            } else {

                p.sendMessage(ChatColor.RED + "The correct usage is /create <name>");

            }

        } else if (cmdLabel.equalsIgnoreCase("message")) {

            if (VTListener.getMobName() != null) {
                if (args.length >= 1) {
                    String msg = args[0];

                    for (int i = 1; i < args.length; i++) {

                        msg += " " + args[i];

                    }

                    reader.writeMobMessage(VTListener.getMobName(), msg, mobLoc);

                }  else {

                    p.sendMessage(ChatColor.RED + "The correct usage is /message <message>");

                }
            } else {

                p.sendMessage(ChatColor.RED + "Shift Right Click on the villager you want to edit first.");

            }

        } else if (cmdLabel.equalsIgnoreCase("item")) {
            if (VTListener.getMobName() != null) {
                if (args.length == 1) {

                    reader.writeMobItem(VTListener.getMobName(), Material.getMaterial(args[0]), mobLoc);

                }  else {

                    p.sendMessage(ChatColor.RED + "The correct usage is /item <item>.");

                }
            } else {

                p.sendMessage(ChatColor.RED + "Shift Right Click on the villager you want to edit first.");

            }

        } else if (cmdLabel.equalsIgnoreCase("remove")) {

            if (args.length == 1) {

                Location loc;

                if (reader.mobExists(args[0], mobLoc) == true) {

                    loc = reader.readMobLoc(args[0], mobLoc);
                    List<Entity> entityList = loc.getWorld().getEntities();

                    for (int i = 0; i < entityList.size(); i++) {

                        if (entityList.get(i) instanceof Villager) {

                            Villager v = (Villager) entityList.get(i);

                            if (v.getCustomName().equalsIgnoreCase(args[0])) {

                                if (reader.mobExists(args[0], mobLoc)) {

                                    v.remove();
                                    reader.removeMob(args[0], mobLoc);

                                } else {

                                    p.sendMessage(ChatColor.RED + "That Villager does not exist.");

                                }

                            }

                        }

                    }

                } else {

                    loc = null;
                    p.sendMessage(ChatColor.RED + "That Villager does not exist.");

                }

            }  else {

                p.sendMessage(ChatColor.RED + "The correct usage is /remove <name>");

            }



        }

        return true;
    }

    public File getMobLoc() {

        return mobLoc;
    }

}
