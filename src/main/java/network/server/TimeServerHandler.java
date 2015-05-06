/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import java.util.Date;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Joakim
 */
public class TimeServerHandler extends IoHandlerAdapter{

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
    
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        
        String str = message.toString();
        if(str.trim().equalsIgnoreCase("quit")) {
            session.close(true);
        }
        Date date = new Date();
        session.write("Jockes Server ger dig här dagens datum!\n" + date.toString());
        //session.write(date.toString());
        System.out.println("Message written...");
        System.out.println(session.getRemoteAddress());
        
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        
        System.out.println("IDLE " + session.getIdleCount(status));
        
    }
    
    
    
}
