package com.fejkathegame.game.multiplayer.lobby;

import com.fejkathegame.client.ClientProgram;
import com.fejkathegame.client.MPPlayer;
import com.fejkathegame.client.PacketNamePlayer;
import com.fejkathegame.client.PacketReadyPlayer;
import com.fejkathegame.game.Main;
import com.fejkathegame.game.arena.State;
import com.fejkathegame.game.arena.maps.multiplayer.versus01.VersusState;
import com.fejkathegame.game.entities.Character;
import com.fejkathegame.menu.HostScreen;
import com.fejkathegame.menu.HostScreenState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khamekaze
 */
public class LobbyState extends State {

    private ClientProgram client = new ClientProgram();

    private HostScreenState hs;

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

    @Override
    public int getID() {
        return Main.LOBBYSTATE;
    }

    public LobbyState(String name) {
        this.name = name;
    }

    public LobbyState(HostScreenState hs) {
        this.hs = hs;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        lobby = new Lobby(name);
        heads = lobby.getImages();
        characters = lobby.getCharacters();
        if (hs != null) {
            if (hs.getIp() != null) {
                client.network(hs.getIp());
            } else {
                client.network("localhost");
            }
            playerName = hs.getPlayerName();
        } else {
            client.network("localhost");
            playerName = "Player";
        }

        localPlayer = null;
        try {
            localPlayer = new com.fejkathegame.game.entities.Character(800, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }

        localPlayer.setName(playerName);
        localPlayer.setReady(false);
        sendNameToServer();
        sendReadyToServer();

        characters.add(localPlayer);
        sbg.addState(new VersusState("01versus", client, localPlayer, characters));
        checkReady = new boolean[1];
    }

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
                mpPlayer.character.setName(mpPlayer.name);
                mpPlayer.character.setReady(false);
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
        client.getClient().sendUDP(packet);
    }

    public void sendReadyToServer() {
        PacketReadyPlayer packet = new PacketReadyPlayer();
        packet.ready = localPlayer.getReady();
        client.getClient().sendUDP(packet);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        lobby.render();

        renderPlayersInLobby(g);

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

        for (MPPlayer c : client.getPlayers().values()) {
            if (c.connected) {
                g.drawString(c.name, increase * 128, 132);
                if (c.ready) {
                    g.drawString("READY", increase * 128, 68);
                } else {
                    g.drawString("NOT READY", increase * 128, 68);
                }
                increase += 1;
            }
        }
        increase = 2;
    }

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

    private boolean allReadyTrue() {
        if (Arrays.toString(checkReady).contains("f")) {
            return false;
        } else {
            return true;
        }
    }

    public void checkIfAllIsReady() {
        ArrayList<Boolean> playersReady = new ArrayList<>();
        for(MPPlayer mp : client.getPlayers().values()) {
            playersReady.add(mp.ready);
        }
        playersReady.add(localPlayer.getReady());
        allReady = !Arrays.asList(playersReady).toString().contains("f");
        
        playersReady.clear();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        checkIfNewPlayerConnected();
        checkIfReadyIsPressed(gc.getInput());
        sendReadyToServer();
        checkIfAllIsReady();
        if (allReady && characters.size() >= 2) {
            sbg.getState(Main.VERSUSSTATE).init(gc, sbg);
            sbg.enterState(Main.VERSUSSTATE);
        }
    }
}
