/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Swartt
 */
public class PropertiesAdapter {

   InputStream inputStream;
   String configFilePath = "config.prop";

    /**
     * Saves the value to the database
     * @param key
     * @param value
     */
   public void save(String key, String value) {
      try {
         File file = new File(configFilePath);
         Properties properties = new Properties();
         properties.load(new FileInputStream(file));
         properties.setProperty(key, value);

         OutputStream out = new FileOutputStream(file);
         properties.store(out, null);
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

    /**
     * Loads a value from the database tied to the given string
     * @param key
     * @return
     */
   public String load(String key){
      String value = "null";
              try {
            File file = new File(configFilePath);
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            if (properties.getProperty(key) != null) {
                value = properties.getProperty(key);
            } 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
              return value;
   }

}
