package com.company;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    static final int WIDTH = 300;
    static final int HEIGHT = 300;
    static final int FOOD_X = WIDTH - 50;
    static final int FOOD_Y = HEIGHT - 50;
    static final int ANT_COUNT = 200;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    static int[][][] pheromons = new int[HEIGHT][WIDTH][2];
    static int[][] food = new int[HEIGHT][WIDTH];

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        root.getChildren().add(canvas);

        List<Ant> ants = new ArrayList<>(200);
        for (int i = 0; i < ANT_COUNT; i++) {
            Ant ant = new Ant();
            ants.add(ant);
        }
        for(int y = FOOD_Y; y < HEIGHT;y++){
            for(int x = FOOD_X; x < WIDTH;x++){
                food[y][x] = 10;
            }
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, WIDTH, HEIGHT);
            gc.setFill(Color.GREEN);
            for(int y = 0; y < HEIGHT;y++){
                for(int x = 0; x < WIDTH;x++){
                    pheromons[y][x][0] = Math.max(pheromons[y][x][0] - 1,0);
                    pheromons[y][x][1] = Math.max(pheromons[y][x][1] - 1,0);
                    if(food[y][x] > 0){
                        gc.fillRect(x,y,2,2);
                    }
                }
            }
            gc.setFill(Color.VIOLET);
            for(int y = 0; y < HEIGHT;y++){
                for(int x = 0; x < WIDTH;x++){
                    gc.setFill(new Color(0.5,0.5,0.5,Math.min(1, 0.0001*pheromons[y][x][0])));
                    gc.fillRect(x,y,1,1);
                }
            }
            for (int i = 0; i < 200; i++) {
                gc.setFill(ants.get(i).state.color);
                ants.get(i).move();
                gc.fillRect(ants.get(i).x, ants.get(i).y, 2, 2);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
