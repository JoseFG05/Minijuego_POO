package model;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;
    
    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
        direction = Direction.RIGHT;
    }
    
    public LinkedList<Point> getBody() {
        return body;
    }
    
    public Point getHead() {
        return body.getFirst();
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            this.direction = newDirection;
        }
    }
    
    private boolean isOppositeDirection(Direction newDirection) {
        return (direction == Direction.UP && newDirection == Direction.DOWN) ||
               (direction == Direction.DOWN && newDirection == Direction.UP) ||
               (direction == Direction.LEFT && newDirection == Direction.RIGHT) ||
               (direction == Direction.RIGHT && newDirection == Direction.LEFT);
    }
    
    public void grow() {
        body.addLast(new Point(body.getLast()));
    }
    
    public int getLength() {
        return body.size();
    }
    
    public void move(Direction dir) {
        this.direction = dir;
    }
}
