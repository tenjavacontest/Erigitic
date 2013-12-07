package com.erigitic.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReader {

    private MobTalk plugin;

    public FileReader(MobTalk plugin) {

        this.plugin = plugin;

    }

    public void writeMobMessage(String mobName, String msg, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            prop.setProperty(mobName, msg);
            prop.store(new FileOutputStream(file), null);

        } catch (IOException e) {


        }

    }

    public String readMobMessage(String mobName, File file) {

        Properties prop = new Properties();

        try {

            FileInputStream in = new FileInputStream(file);
            prop.load(in);
            String msg = prop.getProperty(mobName);

            return msg;
        } catch (IOException e) {


        }

        return null;
    }

}
