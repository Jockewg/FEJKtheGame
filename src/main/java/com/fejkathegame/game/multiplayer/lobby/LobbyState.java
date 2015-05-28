package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketNamePlayer;
import com.fejkathegame.client.PacketReadyPlayer;
import com.fejkathegame.client.PacketServerIsPlaying;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.menu.HostScreenState;
import com.fejkathegame.menu.JoinScreenState;
import com.fejkathegame.menu.Menu;
import com.fejkathegame.server.ServerProgram;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khamekaze
 */
public class LobbyState extends State {

    private ClientProgram client;

    private JoinScreenState js;
    private HostScreenState hs;

    private Font font;
    private TrueTypeFont ttf;
    boolean comingfromHS = false;

    private Character localPlayer;
    private int numPlayersReady;

    private String name, playerName;
    private Lobby lobby;
    private ArrayList<Image> heads;
    private ArrayList<Character> characters;
    private boolean[] checkReady;
    private int arrayIndex = 1;

    private int increase = 2;
    private boolean allReady = false;
    private ServerProgram server;

    @Override
    public int getID() {
        return Main.LOBBYSTATE;
    }

    public LobbyState(String name, ClientProgram client, String pName) {
        this.name = name;
        this.client = client;
        playerName = pName;
    }

    public LobbyState(JoinScreenState js) {
        this.js = js;
        if (js != null) {
            if (js.getIp() != null) {
                client = new ClientProgram();
                client.network(js.getIp());
            } else {
                client = new ClientProgram();
                client.network("localhost");
            }
            playerName = js.getPlayerName();
        }
    }

    public LobbyState(HostScreenState hs, ServerProgram server) {
        this.server = server;
        this.hs = hs;
        comingfromHS = true;
        if (hs != null) {
            if (hs.getIp() != null) {
                client = new ClientProgram();
                client.network(hs.getIp());
            } else {
                client = new ClientProgram();
                client.network("localhost");
            }
            playerName = hs.getPlayerName();
        }
    }

    /**
     * @return the local IP of the user in string format
     * @throws Exception
     */
    public String getIP() throws Exception {

        for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            for (InetAddress address : Collections.list(iface.getInetAddresses())) {
                if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                    return address.getHostAddress().trim();
                }
            }
        }

        return "unknown";
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lobby = new Lobby(name);
        heads = lobby.getImages();
        characters = lobby.getCharacters();

        localPlayer = null;
        try {
            localPlayer = new Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }

        localPlayer.setName(playerName);
        localPlayer.setReady(false);
        
        characters.add(localPlayer);

        checkReady = new boolean[1];

        font = new java.awt.Font("Verdana", java.awt.Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);
    }

    /**
     * Checks if a new player is connected
     */
    public void checkIfNewPlayerConnected() {
        for (MPPlayer mpPlayer : client.getPlayers().values()) { //other player render here.
            if (mpPlayer.character == null) {
                try {
                    mpPlayer.character = new Character(300, 40);
                } catch (SlickException | IOException ex) {
                    Logger.getLogger(VersusState.class.getName()).log(Level.SEVERE, null, ex);
                }
                mpPlayer.character.setHealth(5);
                mpPlayer.hp = 5;
                mpPlayer.isAttacking = false;
                mpPlayer.character.setIsAttacking(false);
                mpPlayer.character.setName(mpPlayer.name);
                mpPlayer.character.setReady(false);
                mpPlayer.connected = true;
                characters.add(mpPlayer.character);
            }
            if (mpPlayer.connected == false) {
                characters.remove(mpPlayer.character);
            }
        }
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void sendNameToServer() {
        PacketNamePlayer packet = new PacketNamePlayer();
        packet.name = localPlayer.getName();
        client.getClient().sendTCP(packet);
    }

    public void sendReadyToServer() {
        PacketReadyPlayer packet = new PacketReadyPlayer();
        packet.ready = localPlayer.getReady();
        client.getClient().sendTCP(packet);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        lobby.render();

        g.setColor(Color.white);
        renderPlayersInLobby(g);

        if (comingfromHS) {
            try {
                ttf.drawString(550, 20, "IP to server: " + getIP(), Color.white);
            } catch (Exception ex) {
                Logger.getLogger(LobbyState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (localPlayer.getReady()) {
            g.drawImage(lobby.getReadyButton(), (Main.WINDOW_WIDTH / 2) - 100, 350);
        } else {
            g.drawImage(lobby.getUnReadyButton(), (Main.WINDOW_WIDTH / 2) - 100, 350);
        }
    }

    public void renderPlayersInLobby(Graphics g) {
        if (localPlayer.getReady()) {
            g.drawString("READY", 128, 68);
        } else if (!localPlayer.getReady()) {
            g.drawString("NOT READY", 128, 68);
        }

        g.drawString(localPlayer.getName(), 128, 132);

        for (MPPlayer mp : client.getPlayers().values()) {
            if (mp.connected) {
                g.drawString(mp.name, increase * 128, 132);
                if (mp.ready) {
                    g.drawString("READY", increase * 128, 68);
                } else {
                    g.drawString("NOT READY", increase * 128, 68);
                }
                increase += 1;
            }
        }
        increase = 2;
    }

    /**
     * Checks if a the local player has declared them self ready
     * @param i
     */
    public void checkIfReadyIsPressed(Input i) {
        if (i.getMouseX() > (Main.WINDOW_WIDTH / 2) - 100 && i.getMouseX() < (Main.WINDOW_WIDTH / 2) + 100
                && i.getMouseY() > 350 && i.getMouseY() < 425) {
            if (i.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                if (localPlayer.getReady()) {
                    localPlayer.setReady(false);
                } else {
                    localPlayer.setReady(true);
                }
            }
        }
    }

    /**
     * Checks if all players are ready
     */
    public void checkIfAllIsReady() {
        ArrayList<Boolean> playersReady = new ArrayList<>();
        for (MPPlayer mp : client.getPlayers().values()) {
            playersReady.add(mp.ready);
        }
        playersReady.add(localPlayer.getReady());
        allReady = !Arrays.asList(playersReady).toString().contains("f");
        playersReady.clear();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if (client.serverIsPlaying()) {
            client.getClient().close();
            sbg.enterState(Main.MENU);
        } else {
            checkIfNewPlayerConnected();
            checkIfReadyIsPressed(gc.getInput());
            sendNameToServer();
            sendReadyToServer();
            checkIfAllIsReady();
            if (allReady && characters.size() >= 2) {
                Main.oldLobby = this;
                if (ServerProgram.getServer() != null) {
                    PacketServerIsPlaying packet = new PacketServerIsPlaying();
                    packet.serverIsPlaying = true;
                    client.getClient().sendTCP(packet);
                    sbg.addState(new VersusState("01versus", client, localPlayer, characters, server));
                } else {
                    sbg.addState(new VersusState("01versus", client, localPlayer, characters));
                }
                sbg.getState(Main.VERSUSSTATE).init(gc, sbg);
                sbg.enterState(Main.VERSUSSTATE);
            }
        }
    }
}
