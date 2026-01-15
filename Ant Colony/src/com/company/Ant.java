package com.company;

import static com.company.Direction.*;
import static com.company.State.*;

public class Ant{
    int x, y;
    Direction direction;
    State state;
    int maxToFoodPheromone = 2000;
    int maxToAnthillPheromone = 2000;
    int toFoodPheromone;
    int toAnthillPheromone;

    public Ant() {
        x = Main.WIDTH / 2;
        y = Main.HEIGHT / 2;
        direction = getRandomDirection();
        state = RANDOM_WALK;
        toFoodPheromone = 2000;
        toAnthillPheromone = 2000;
    }

    public void move() {
        if(Main.food[y][x] > 0){
            Main.food[y][x]-= 1;
            state = TO_ANTHILL;
            direction = Direction.getBackTurn(direction);
        }
        if (state == RANDOM_WALK) {
            if (Math.random() > 0.95) {
                direction = getRandomDirection();
            }
            Main.pheromons[y][x][1] += toAnthillPheromone;
            toAnthillPheromone -=5;
            x = (x + direction.x + Main.WIDTH) % Main.WIDTH;
            y = (y + direction.y + Main.HEIGHT) % Main.HEIGHT;
            if(Main.pheromons[y][x][0] > 0){
                state = TO_FOOD;
            }
        }
        if(state == TO_FOOD){
            int newX = (x + direction.x + Main.WIDTH) % Main.WIDTH;
            int newY = (y + direction.y + Main.HEIGHT) % Main.HEIGHT;
            int maxPheromone = Main.pheromons[newY][newX][0];
            Direction turnLeft = Direction.getLeftTurn(direction);
            int leftX = (x + turnLeft.x + Main.WIDTH) % Main.WIDTH;
            int leftY = (y + turnLeft.y + Main.HEIGHT) % Main.HEIGHT;
            int leftPheromone = Main.pheromons[leftY][leftX][0];
            if(leftPheromone > maxPheromone) {
                newX = leftX;
                newY = leftY;
                maxPheromone = leftPheromone;
            }
            Direction turnRight = Direction.getRightTurn(direction);
            int rightX = (x + turnRight.x + Main.WIDTH) % Main.WIDTH;
            int rightY = (y + turnRight.y + Main.HEIGHT) % Main.HEIGHT;
            int rightPheromone = Main.pheromons[rightY][rightX][0];
            if(rightPheromone > maxPheromone) {
                newX = rightX;
                newY = rightY;
                maxPheromone = rightPheromone;
            }
            x = newX;
            y = newY;
        }
        if(state == TO_ANTHILL){
            Main.pheromons[y][x][0] += toFoodPheromone;
            toFoodPheromone-=5;
            int newX = (x + direction.x + Main.WIDTH) % Main.WIDTH;
            int newY = (y + direction.y + Main.HEIGHT) % Main.HEIGHT;
            int maxPheromone = Main.pheromons[newY][newX][1];
            Direction turnLeft = Direction.getLeftTurn(direction);
            int leftX = (x + turnLeft.x + Main.WIDTH) % Main.WIDTH;
            int leftY = (y + turnLeft.y + Main.HEIGHT) % Main.HEIGHT;
            int leftPheromone = Main.pheromons[leftY][leftX][1];
            if(leftPheromone > maxPheromone) {
                newX = leftX;
                newY = leftY;
                maxPheromone = leftPheromone;
            }
            Direction turnRight = Direction.getRightTurn(direction);
            int rightX = (x + turnRight.x + Main.WIDTH) % Main.WIDTH;
            int rightY = (y + turnRight.y + Main.HEIGHT) % Main.HEIGHT;
            int rightPheromone = Main.pheromons[rightY][rightX][1];
            if(rightPheromone > maxPheromone) {
                newX = rightX;
                newY = rightY;
                maxPheromone = rightPheromone;
            }
            x = newX;
            y = newY;
        }
    }
}
