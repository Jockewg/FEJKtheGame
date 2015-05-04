/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Joakim
 */
class ClientSessionHandler extends IoHandlerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientSessionHandler.class);

    private final int[] values;

    private boolean finished;

    public ClientSessionHandler(int[] values) {
        this.values = values;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // send summation requests
        for (int i = 0; i < values.length; i++) {
            AddMessage m = new AddMessage();
            m.setSequence(i);
            m.setValue(values[i]);
            session.write(m);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        // server only sends ResultMessage. otherwise, we will have to identify
        // its type using instanceof operator.
        ResultMessage rm = (ResultMessage) message;
        if (rm.isOk()) {
            // server returned OK code.
            // if received the result message which has the last sequence
            // number,
            // it is time to disconnect.
            if (rm.getSequence() == values.length - 1) {
                // print the sum and disconnect.
                LOGGER.info("The sum: " + rm.getValue());
                session.close(true);
                finished = true;
            }
        } else {
            // server returned error code because of overflow, etc.
            LOGGER.warn("Server error, disconnecting...");
            session.close(true);
            finished = true;
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.close(true);
    }

}
