/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.mina.client;

/**
 *
 * @author Joakim
 */
public class AddMessage extends AbstractMessage {

    private static final long serialVersionUID = -940833727168119141L;

    private int value;

    public AddMessage() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        // it is a good practice to create toString() method on message classes.
        return getSequence() + ":ADD(" + value + ')';
    }

}
