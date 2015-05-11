
package network.jgn.server;

import com.captiveimagination.jgn.JGN;
import com.captiveimagination.jgn.clientserver.JGNConnection;
import com.captiveimagination.jgn.clientserver.JGNConnectionListener;
import com.captiveimagination.jgn.clientserver.JGNServer;
import com.captiveimagination.jgn.message.ChatMessage;
import com.captiveimagination.jgn.message.Message;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 *
 * @author Joakim
 */
public class BasicServer extends Thread implements JGNConnectionListener{

    private JGNServer server;
    
    private static final String HOST_IP = "127.0.0.1";
    private static final int PORT_TCP = 2000;
    private static final int PORT_UDP = 2100;
    private ChatMessage timemsg;    
    public BasicServer(){
        
        try{
            server = new JGNServer(new InetSocketAddress(InetAddress.getByName(HOST_IP), PORT_TCP), new InetSocketAddress(InetAddress.getByName(HOST_IP), PORT_UDP));
            server.addClientConnectionListener(this);
            
            JGN.createThread(server).start();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        while(server.isAlive()){
            try{
                Thread.sleep(1000);
                System.out.println("BroadCasting Date...");
                timemsg.setText(new Date().toString());
                server.sendToAll(timemsg);
                //server.sendToAll(message);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void connected(JGNConnection jgnc) {
        System.out.println(jgnc + " connected to the server");
    }

    @Override
    public void disconnected(JGNConnection jgnc) {
        System.out.println(jgnc + " disconnected from the server");
    }
    
    public static void main(String[] args) {
        new BasicServer();
        
    }
    
}
