package model;

import java.awt.Point;

public class Food {
    private Point position;
    private int value;
    
    public Food(int x, int y) {
        this.position = new Point(x, y);
        this.value = 10;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}