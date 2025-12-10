package model;

import java.awt.Point;
import java.util.Random;

public class GameModel {
    private static final int GRID_SIZE = 20;
    private static final int BOARD_WIDTH = 30;
    private static final int BOARD_HEIGHT = 20;
    
    private Snake snake;
    private Food food;
    private int score;
    private int highScore;
    private boolean gameOver;
    private Random random;
    
    public GameModel() {
        random = new Random();
        highScore = 0;
        initGame();
    }
    
    public void initGame() {
        snake = new Snake(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
        generateFood();
        score = 0;
        gameOver = false;
    }
    
    public void move() {
        if (gameOver) return;
        
        Point head = snake.getHead();
        Point newHead = new Point(head);
        
        switch (snake.getDirection()) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        
        snake.getBody().addFirst(newHead);
        
        if (newHead.equals(food.getPosition())) {
            score += food.getValue();
            generateFood();
        } else {
            snake.getBody().removeLast();
        }
    }
    
    public void generateFood() {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (snake.getBody().contains(new Point(x, y)));
        
        food = new Food(x, y);
    }
    
    public boolean checkCollision() {
        Point head = snake.getHead();
        
        if (head.x < 0 || head.x >= BOARD_WIDTH || 
            head.y < 0 || head.y >= BOARD_HEIGHT) {
            gameOver = true;
            updateHighScore();
            return true;
        }
        
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                gameOver = true;
                updateHighScore();
                return true;
            }
        }
        
        return false;
    }
    
    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }
    
    public Snake getSnake() {
        return snake;
    }
    
    public Food getFood() {
        return food;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getHighScore() {
        return highScore;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public static int getGridSize() {
        return GRID_SIZE;
    }
    
    public static int getBoardWidth() {
        return BOARD_WIDTH;
    }
    
    public static int getBoardHeight() {
        return BOARD_HEIGHT;
    }
}