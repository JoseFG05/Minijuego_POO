package controller;

import model.Direction;
import model.GameModel;
import view.GameFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private GameModel model;
    private GameFrame view;
    private Timer timer;
    private static final int DELAY = 100;
    
    public GameController(GameModel model, GameFrame view) {
        this.model = model;
        this.view = view;
        
        initController();
    }
    
    private void initController() {
        view.getGamePanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        view.getGamePanel().setFocusable(true);
        view.getGamePanel().requestFocusInWindow();
        
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!model.isGameOver()) {
                    model.move();
                    model.checkCollision();
                    view.getGamePanel().repaint();
                }
            }
        });
        
        timer.start();
    }
    
    private void handleKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_UP:
                model.getSnake().setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                model.getSnake().setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                model.getSnake().setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                model.getSnake().setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                if (model.isGameOver()) {
                    restartGame();
                }
                break;
        }
    }
    
    private void restartGame() {
        model.initGame();
        view.getGamePanel().repaint();
    }
    
    public void start() {
        view.setVisible(true);
    }
}