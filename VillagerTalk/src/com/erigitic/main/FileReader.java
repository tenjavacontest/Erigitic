package com.erigitic.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReader {

    private VillagerTalk plugin;

    public FileReader(VillagerTalk plugin) {

        this.plugin = plugin;

    }

    public void writeMobLoc(String mobName, Location loc, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            prop.setProperty(mobName.toLowerCase() + "l", "" + loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getY());
            prop.store(new FileOutputStream(file), null);

        } catch (IOException e) {


        }

    }

    public Location readMobLoc(String mobName, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            String value = prop.getProperty(mobName.toLowerCase() + "l");

            String[] locS = value.split(";");
            Location loc = new Location(Bukkit.getServer().getWorld(locS[0]), Double.parseDouble(locS[1]), Double.parseDouble(locS[2]), Double.parseDouble(locS[3]));

            return loc;
        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }

    public void writeMobMessage(String mobName, String msg, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            prop.setProperty(mobName.toLowerCase(), msg);
            prop.store(new FileOutputStream(file), null);

        } catch (IOException e) {


        }

    }

    public String readMobMessage(String mobName, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            String msg = prop.getProperty(mobName.toLowerCase());

            return msg;
        } catch (IOException e) {


        }

        return null;
    }

    public void writeMobItem(String mobName, Material material, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            prop.setProperty(mobName.toLowerCase() + "i", "" + material);
            prop.store(new FileOutputStream(file), null);

        } catch (IOException e) {


        }

    }

    public ItemStack readMobItem(String mobName, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            Material m = Material.getMaterial(prop.getProperty(mobName.toLowerCase()));
            ItemStack itemStack = new ItemStack(m);

            return itemStack;
        } catch (IOException e) {


        } catch (NullPointerException e) {


        }

        return null;
    }

    public void removeMob(String mobName, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            prop.remove(mobName.toLowerCase());
            prop.remove(mobName.toLowerCase() + "l");
            prop.remove(mobName.toLowerCase() + "i");
            prop.store(new FileOutputStream(file), null);

        } catch (IOException e) {


        }

    }

}
