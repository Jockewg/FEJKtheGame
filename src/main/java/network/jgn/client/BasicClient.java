
package network.jgn.client;

import com.captiveimagination.jgn.JGN;
import com.captiveimagination.jgn.clientserver.JGNClient;
import com.captiveimagination.jgn.clientserver.JGNConnection;
import com.captiveimagination.jgn.clientserver.JGNConnectionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Joakim
 */
public class BasicClient implements JGNConnectionListener{

    private JGNClient client;
    
    private static final String HOST_IP = "127.0.0.1";
    private static final int PORT_TCP = 2000;
    private static final int PORT_UDP = 2100;
    
    public void init(){
        try{
            client = new JGNClient(new InetSocketAddress(InetAddress.getLocalHost(), 0), new InetSocketAddress(InetAddress.getLocalHost(), 0));
            client.addServerConnectionListener(this);
            JGN.createThread(client).start();
            
            client.connectAndWait(new InetSocketAddress(InetAddress.getByName(HOST_IP), PORT_TCP), new InetSocketAddress(InetAddress.getByName(HOST_IP), PORT_UDP), 5000);
            System.out.println("Connected!");
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void close() throws IOException{
        System.out.println("Disconnecting Client...");
        client.close();
    }
    
    @Override
    public void connected(JGNConnection jgnc) {
        System.out.println("Logged in as Player " + jgnc.getPlayerId());
    }

    @Override
    public void disconnected(JGNConnection jgnc) {
        System.out.println("Logged off");
    }
    
    public static void main(String[] args) throws Exception {
        BasicClient client = new BasicClient();
        client.init();
        
        System.out.println("**** Sleeping");
        Thread.sleep(5000);
        client.close();
    }
    
}
