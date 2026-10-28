package logic;

import graphic.Lobby;
import graphic.Lobby2;
import java.awt.EventQueue;

public class Principal {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Lobby().setVisible(true);
            new Lobby().setResizable(false);
            new Lobby2().setResizable(false);
        });
    }
    
}
