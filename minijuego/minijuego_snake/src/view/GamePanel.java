package view;

import model.GameModel;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameModel model;
    
    public GamePanel(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(
            GameModel.getBoardWidth() * GameModel.getGridSize(),
            GameModel.getBoardHeight() * GameModel.getGridSize()
        ));
        setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    private void draw(Graphics g) {
        int gridSize = GameModel.getGridSize();
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        Point head = model.getSnake().getHead();
        g.setColor(new Color(34, 139, 34));
        g.fillRect(head.x * gridSize, head.y * gridSize, 
                  gridSize - 1, gridSize - 1);
        
        g.setColor(new Color(50, 205, 50));
        for (int i = 1; i < model.getSnake().getBody().size(); i++) {
            Point segment = model.getSnake().getBody().get(i);
            g.fillRect(segment.x * gridSize, segment.y * gridSize, 
                      gridSize - 1, gridSize - 1);
        }
        
        g.setColor(new Color(220, 20, 60));
        Point foodPos = model.getFood().getPosition();
        g.fillOval(foodPos.x * gridSize, foodPos.y * gridSize, 
                   gridSize, gridSize);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + model.getScore(), 10, 20);
        g.drawString("High Score: " + model.getHighScore(), 10, 40);
        
        if (model.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics fm = g.getFontMetrics();
            String gameOverText = "GAME OVER";
            int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
            g.drawString(gameOverText, x, 180);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartText = "Press SPACE to restart";
            fm = g.getFontMetrics();
            x = (getWidth() - fm.stringWidth(restartText)) / 2;
            g.drawString(restartText, x, 230);
        }
    }
}
