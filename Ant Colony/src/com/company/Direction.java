package com.company;

public enum Direction {
    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

    int x;
    int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static  int index(Direction direction){
        for (int i = 0 ; i < values().length;i++){
            if(values()[i] == direction){
                return i;
            }
        }
        return -1;
    }
    public static Direction getBackTurn(Direction current){
        return values()[(index(current) + 2)%values().length];
    }
    public static Direction getLeftTurn(Direction current){
        return values()[(index(current)-1+values().length)%values().length];
    }
    public static Direction getRightTurn(Direction current){
        return values()[(index(current)+1)%values().length];
    }
    public static Direction getRandomDirection() {
        return values()[(int) (Math.random() * values().length)];
    }
}
