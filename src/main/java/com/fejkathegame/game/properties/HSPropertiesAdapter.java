/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fejkathegame.game.properties;

/**
 *
 * @author Swartt
 */
public class HSPropertiesAdapter {
      
   PropertiesAdapter adapter = new PropertiesAdapter();

    /**
     * Saves the score to the database, if no value exists it saves it as "null"
     * @param key
     * @param value
     */
   public void save (String key, String value) {
      if (!value.equals("null")) {
         adapter.save(key, value);
      } else {
         adapter.save(key, "null");
      }
   }

    /**
     * Loads the score from a the database, checks if the value is null
     * @param key
     * @return
     */
   public String load (String key) {
      String result = adapter.load(key);
      
      if (!"null".equals(adapter.load(key))) {
           result = (adapter.load(key));
        } else {
           result = "null";
        }
        return result;
   }
}


