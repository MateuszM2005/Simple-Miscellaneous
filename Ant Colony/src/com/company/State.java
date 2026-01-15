package com.company;


import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.*;

public enum State {
    RANDOM_WALK(BLACK), TO_FOOD(RED), TO_ANTHILL(BLUE);
    public Color color;

    State(Color color) {
        this.color = color;
    }
}
