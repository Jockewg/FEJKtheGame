package com.fejkathegame.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server sends data to all clients connected to it.
 *
 * @author Filip
 */
public class ServerProgram extends Listener {

    public static Server server;
    static final int udpPort = 27960, tcpPort = 27960;
    static Map<Integer, Player> players = new HashMap<>();
    public static boolean serverReady = false;
    public static boolean isHost = false;

    /**
     * The server register the packedges binds to the port and starts.
     *
     * @param args
     */
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {

        server = new Server();
        server.getKryo().register(PacketUpdateX.class);
        server.getKryo().register(PacketUpdateY.class);
        server.getKryo().register(PacketAddPlayer.class);
        server.getKryo().register(PacketRemovePlayer.class);
        server.getKryo().register(PacketAttackPlayer.class);
        server.getKryo().register(PacketChargePlayer.class);
        server.getKryo().register(PacketFullyChargedPlayer.class);
        server.getKryo().register(PacketAttackDirectionPlayer.class);
        server.getKryo().register(PacketMoveLeftPlayer.class);
        server.getKryo().register(PacketMoveRightPlayer.class);
        server.getKryo().register(PacketJumpPlayer.class);
        server.getKryo().register(PacketGroundedPlayer.class);
        server.getKryo().register(PacketFallingPlayer.class);
        server.getKryo().register(PacketHpPlayer.class);
        server.getKryo().register(PacketNamePlayer.class);
        server.getKryo().register(PacketReadyPlayer.class);
        server.getKryo().register(PacketMedkitPosition.class);
        server.getKryo().register(PacketMedkitAlive.class);
        server.getKryo().register(PacketMedkitPrerendered.class);

        try {
            server.bind(tcpPort, udpPort);
        } catch (IOException ex) {
            Logger.getLogger(ServerProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        server.start();
        server.addListener(new ServerProgram());
        System.out.println("The server is ready");
        serverReady = true;
    }

    /**
     * creates a new player with uniqe id and sends to all clients.
     *
     * @param c the connection to the client
     */
    @Override
    public void connected(Connection c) {
        Player player = new Player();
        player.name = "" + c.getID();
        player.x = 256;
        player.y = 256;
        player.c = c;
        player.direction = 0.0f;
        player.isAttacking = false;
        player.isChargeing = false;
        player.isFullyCharged = false;
        player.isFalling = false;
        player.isJumping = false;
        player.isGrounded = false;
        player.hp = 5;
        player.ready = false;

        PacketAddPlayer packet = new PacketAddPlayer();
        packet.id = c.getID();
        packet.name = player.name;
        server.sendToAllExceptTCP(c.getID(), packet);

        for (Player p : players.values()) {
            PacketAddPlayer packet2 = new PacketAddPlayer();
            packet2.id = p.c.getID();
            packet2.name = p.name;
            c.sendTCP(packet2);
        }

        players.put(c.getID(), player);
        System.out.println("Connection received.");
    }

    /**
     * for every packet recived the player will be updated and sent to all
     * clients.
     *
     * @param c the connection to client
     * @param o the packet recived
     */
    @Override
    public void received(Connection c, Object o) {
        if (o instanceof PacketUpdateX) {
            PacketUpdateX packet = (PacketUpdateX) o;
            players.get(c.getID()).x = packet.x;
            packet.id = c.getID();
            server.sendToAllExceptUDP(c.getID(), packet);
        } else if (o instanceof PacketUpdateY) {
            PacketUpdateY packet = (PacketUpdateY) o;
            players.get(c.getID()).y = packet.y;
            packet.id = c.getID();
            server.sendToAllExceptUDP(c.getID(), packet);
        } else if (o instanceof PacketAttackPlayer) {
            PacketAttackPlayer packet = (PacketAttackPlayer) o;
            boolean old = players.get(c.getID()).isAttacking;
            players.get(c.getID()).isAttacking = packet.isAttacking;
            packet.id = c.getID();
            if (players.get(c.getID()).isAttacking != old) {
                server.sendToAllExceptTCP(c.getID(), packet);
                if (players.get(c.getID()).isAttacking == true) {
//                    System.out.println("client " + c.getID() + " is attacking");
                }
            }
        } else if (o instanceof PacketAttackDirectionPlayer) {
            PacketAttackDirectionPlayer packet = (PacketAttackDirectionPlayer) o;
            float old = players.get(c.getID()).direction;
            players.get(c.getID()).direction = packet.direction;
            packet.id = c.getID();
            if (players.get(c.getID()).direction != old) {
                server.sendToAllExceptTCP(c.getID(), packet);
                if (players.get(c.getID()).isAttacking == true) {
//                    System.out.println("client " + c.getID() + " degree: " + packet.direction);
                }
            }
        } else if (o instanceof PacketChargePlayer) {
            PacketChargePlayer packet = (PacketChargePlayer) o;
            boolean old = players.get(c.getID()).isChargeing;
            players.get(c.getID()).isChargeing = packet.isChargeing;
            packet.id = c.getID();
            if (players.get(c.getID()).isChargeing != old) {
                server.sendToAllExceptTCP(c.getID(), packet);
                if (players.get(c.getID()).isChargeing == true) {
//                    System.out.println("client " + c.getID() + "  is chrageing");
                }
            }
        } else if (o instanceof PacketFullyChargedPlayer) {
            PacketFullyChargedPlayer packet = (PacketFullyChargedPlayer) o;
            boolean old = players.get(c.getID()).isFullyCharged;
            players.get(c.getID()).isFullyCharged = packet.isFullyCharged;
            packet.id = c.getID();
            if (players.get(c.getID()).isFullyCharged != old) {
                server.sendToAllExceptTCP(c.getID(), packet);
                if (players.get(c.getID()).isFullyCharged == true) {
//                    System.out.println("client " + c.getID() + "  it fully charged");
                }
            }
        } else if (o instanceof PacketMoveLeftPlayer) {
            PacketMoveLeftPlayer packet = (PacketMoveLeftPlayer) o;
            boolean old = players.get(c.getID()).moveingLeft;
            players.get(c.getID()).moveingLeft = packet.moveingLeft;
            packet.id = c.getID();
            if (players.get(c.getID()).moveingLeft != old) {
                server.sendToAllExceptUDP(c.getID(), packet);
                if (players.get(c.getID()).moveingLeft == true) {
//                    System.out.println("client " + c.getID() + " moveing left");
                }
            }
        } else if (o instanceof PacketMoveRightPlayer) {
            PacketMoveRightPlayer packet = (PacketMoveRightPlayer) o;
            boolean old = players.get(c.getID()).moveingRight;
            players.get(c.getID()).moveingRight = packet.moveingRight;
            packet.id = c.getID();
            if (players.get(c.getID()).moveingRight != old) {
                server.sendToAllExceptUDP(c.getID(), packet);
                if (players.get(c.getID()).moveingRight == true) {
//                    System.out.println("client " + c.getID() + " moveing right");
                }
            }
        } else if (o instanceof PacketJumpPlayer) {
            PacketJumpPlayer packet = (PacketJumpPlayer) o;
            boolean old = players.get(c.getID()).isJumping;
            players.get(c.getID()).isJumping = packet.isJumping;
            packet.id = c.getID();
            if (players.get(c.getID()).isJumping != old) {
                server.sendToAllExceptUDP(c.getID(), packet);
                if (players.get(c.getID()).isJumping == true) {
//                    System.out.println("client " + c.getID() + " is jumping");
                }
            }
        } else if (o instanceof PacketFallingPlayer) {
            PacketFallingPlayer packet = (PacketFallingPlayer) o;
            boolean old = players.get(c.getID()).isFalling;
            players.get(c.getID()).isFalling = packet.isFalling;
            packet.id = c.getID();
            if (players.get(c.getID()).isFalling != old) {
                server.sendToAllExceptUDP(c.getID(), packet);
                if (players.get(c.getID()).isFalling == true) {
//                    System.out.println("client " + c.getID() + " is falling");
                }
            }
        } else if (o instanceof PacketGroundedPlayer) {
            PacketGroundedPlayer packet = (PacketGroundedPlayer) o;
            boolean old = players.get(c.getID()).isGrounded;
            players.get(c.getID()).isGrounded = packet.isGrounded;
            packet.id = c.getID();
            if (players.get(c.getID()).isGrounded != old) {
                server.sendToAllExceptUDP(c.getID(), packet);
                if (players.get(c.getID()).isGrounded == true) {
//                    System.out.println("client " + c.getID() + " is grounded");
                }
            }
        } else if (o instanceof PacketHpPlayer) {
            PacketHpPlayer packet = (PacketHpPlayer) o;
            int old = players.get(c.getID()).hp;
            players.get(c.getID()).hp = packet.hp;
            packet.id = c.getID();
            if (players.get(c.getID()).hp != old) {
                server.sendToAllExceptTCP(c.getID(), packet);
//                System.out.println("client " + c.getID() + " hp is: " + players.get(c.getID()).hp);
            }
        } else if (o instanceof PacketNamePlayer) {
            PacketNamePlayer packet = (PacketNamePlayer) o;
            players.get(c.getID()).name = packet.name;
            packet.id = c.getID();
            server.sendToAllExceptTCP(c.getID(), packet);
//            System.out.println("client " + c.getID() + " name is: " + players.get(c.getID()).name);
        } else if (o instanceof PacketReadyPlayer) {
            PacketReadyPlayer packet = (PacketReadyPlayer) o;
            boolean old = players.get(c.getID()).ready;
            players.get(c.getID()).ready = packet.ready;
            packet.id = c.getID();
            server.sendToAllExceptTCP(c.getID(), packet);
            if (players.get(c.getID()).ready != old) {
//                System.out.println("client " + c.getID() + " ready status is: " + players.get(c.getID()).ready);
            }
        } else if (o instanceof PacketMedkitPosition) {
            PacketMedkitPosition packet = (PacketMedkitPosition) o;
            server.sendToAllExceptTCP(c.getID(), packet);
        } else if (o instanceof PacketMedkitAlive) {
            PacketMedkitAlive packet = (PacketMedkitAlive) o;
            server.sendToAllExceptTCP(c.getID(), packet);
        } else if (o instanceof PacketMedkitPrerendered) {
            PacketMedkitPrerendered packet = (PacketMedkitPrerendered) o;
            server.sendToAllExceptTCP(c.getID(), packet);
        }
    }

    /**
     * for every dissconnected client there will be a message sent to all
     * clients
     *
     * @param c the connection to the client
     */
    @Override
    public void disconnected(Connection c
    ) {
        players.remove(c.getID());
        PacketRemovePlayer packet = new PacketRemovePlayer();
        packet.id = c.getID();
        server.sendToAllExceptTCP(c.getID(), packet);
        System.out.println("Connection dropped.");
    }

    public static boolean isServerReady() {
        return serverReady;
    }
}
