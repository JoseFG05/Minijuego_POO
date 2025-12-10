package view;

import model.GameModel;
import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    
    public GameFrame(GameModel model) {
        setTitle("Snake Game - MVC Architecture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel(model);
        add(gamePanel);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
