package com.company;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class Main extends Application {

    static double[][] sobelX = {
            {1, 0, -1},
            {2, 0, -2},
            {1, 0, -1},
    };
    static double[][] sobelY = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1},
    };
    static double[][] test = {
            {0,   0.8, 0.4},
            {0.1, 0.5, 0.1},
            {0,   0.3, 0.1},
    };

    public static void main(String[] args) {
        launch(args);
    }

    public static int[] getSeam(double[][] energy) {
        final int WIDTH = energy[0].length;
        final int HEIGHT = energy.length;

        double[][] temp = new double[HEIGHT][WIDTH];
        for (int c = 0; c < WIDTH; c++) {
            temp[HEIGHT - 1][c] = energy[HEIGHT - 1][c];
        }
        for (int r = HEIGHT - 2; r >= 0; r--) {
            for (int c = 0; c < WIDTH; c++) {
                double min = temp[r + 1][c];
                if (c - 1 >= 0 && temp[r + 1][c - 1] < min) {
                    min = temp[r + 1][c - 1];
                }
                if (c + 1 < WIDTH && temp[r + 1][c + 1] < min) {
                    min = temp[r + 1][c + 1];
                }
                temp[r][c] = energy[r][c] + min;
            }
        }
        int[] seam = new int[HEIGHT];
        int minIndex = 0;
        for (int c = 1; c < WIDTH; c++) {
            if (temp[0][c] < temp[0][minIndex]) {
                minIndex = c;
            }
        }
        seam[0] = minIndex;
        int c = minIndex;
        for (int r = 1; r < HEIGHT; r++) {
            int minColumnIndex = c;
            if (c - 1 >= 0 && temp[r][c - 1] < temp[r][minColumnIndex]) {
                minColumnIndex = c - 1;
            }
            if (c + 1 < WIDTH && temp[r][c + 1] < temp[r][minColumnIndex]) {
                minColumnIndex = c + 1;
            }
            seam[r] = minColumnIndex;
            c = minColumnIndex;
        }

        return seam;
    }

    public static double[][] getEnergy(double[][] picture) {
        double[][] sobelx = getEnergy(picture, sobelX);
        double[][] sobely = getEnergy(picture, sobelY);
        double[][] energy = new double[picture.length][picture[0].length];

        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[0].length; c++) {
                energy[r][c] = Math.sqrt(Math.pow(sobelx[r][c], 2) + Math.pow(sobely[r][c], 2));
            }
        }
        return energy;
    }

    public static double[][] getEnergy(double[][] picture, double[][] filter) {
        double[][] result = new double[picture.length][picture[0].length];

        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[0].length; c++) {
                for (int ri = 0; ri < filter.length; ri++) {
                    for (int ci = 0; ci < filter.length; ci++) {
                        if (r + ri < picture.length && c + ci < picture[0].length) {
                            result[r][c] += picture[r + ri][c + ci] * filter[ri][ci];
                        }
                    }
                }
            }
        }

        return result;
    }

    private static void print(double[][] picture) {
        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[0].length; c++) {
                System.out.printf("%1.4f ", picture[r][c]);
            }
            System.out.println();
        }
    }

    ImageView firstImage;
    ImageView secondImage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();

        Button button = new Button("Get image");
        button.setTranslateX(100);
        button.setTranslateY(100);
        button.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                firstImage = new ImageView(image);
                firstImage.setX(100);
                firstImage.setY(150);

                firstImage.setFitHeight(400);
                root.getChildren().add(firstImage);

                Image newImage = image;
                for (int i = 0; i < 150; i++) {
                    double[][] picture = getPicture(newImage);
                    double[][] energy = getEnergy(picture);
                    int[] seam = getSeam(energy);
                    newImage = removeSeamFromImage(newImage, seam);
                }

                //saveInFile(newImage);
                secondImage = new ImageView(newImage);
                secondImage.setX(800);
                secondImage.setY(150);

                secondImage.setFitHeight(400);
                root.getChildren().add(secondImage);
            }
        });
        root.getChildren().add(button);

        Scene scene = new Scene(root, 1900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Seam carving");
        primaryStage.show();
    }

    private WritableImage getImage(double[][] energy) {
        final int WIDTH = energy[0].length;
        final int HEIGHT = energy.length;

        WritableImage newImage = new WritableImage(WIDTH, HEIGHT);
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                energy[r][c] = Math.max(energy[r][c], 0);
                energy[r][c] = Math.min(energy[r][c], 1);
                pixelWriter.setColor(c, r, new Color(energy[r][c], energy[r][c], energy[r][c], 1));
            }
        }

        return newImage;
    }

    public WritableImage removeSeamFromImage(Image image, int[] seam) {
        final int WIDTH = (int) image.getWidth();
        final int HEIGHT = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        WritableImage newImage = new WritableImage(WIDTH - 1, HEIGHT);
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0, c2 = 0; c < WIDTH; c++) {
                if (seam[r] != c) {
                    pixelWriter.setColor(c2, r, pixelReader.getColor(c, r));
                    c2++;
                }
//                else {
//                    pixelWriter.setColor(c2, r, Color.RED);
//                    c2++;
//                }
            }
        }

        return newImage;
    }

    public void saveInFile(WritableImage newImage) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(newImage, null),
                    "png", new File(UUID.randomUUID().toString().substring(0, 6) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double[][] getPicture(Image image) {
        final int WIDTH = (int) image.getWidth();
        final int HEIGHT = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        double[][] picture = new double[HEIGHT][WIDTH];

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                Color color = pixelReader.getColor(c, r);
                picture[r][c] = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
            }
        }

        return picture;
    }
}
