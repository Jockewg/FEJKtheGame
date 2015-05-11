/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.mina.client;

import java.io.Serializable;

/**
 *
 * @author Joakim
 */
public abstract class AbstractMessage implements Serializable{
    
    private int sequence;
  
      public int getSequence() {
          return sequence;
      }
  
      public void setSequence(int sequence) {
          this.sequence = sequence;
      }
    
}
