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
   
   public void save (String key, String value) {
      if (!value.equals("null")) {
         adapter.save(key, value);
      } else {
         adapter.save(key, "null");
      }
   }
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

