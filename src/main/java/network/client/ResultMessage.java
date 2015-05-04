/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

/**
 *
 * @author Joakim
 */
class ResultMessage extends AbstractMessage {

    private static final long serialVersionUID = 7371210248110219946L;

    private boolean ok;

    private int value;

    public ResultMessage() {
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (ok) {
            return getSequence() + ":RESULT(" + value + ')';
        } else {
            return getSequence() + ":RESULT(ERROR)";
        }
    }
}
