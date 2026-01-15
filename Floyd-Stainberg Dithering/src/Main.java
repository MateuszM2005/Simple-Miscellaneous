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


    public static void main(String[] args) {
        launch(args);
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


                Image newImage = getImage(ditherImage(getPicture(image)));

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
        primaryStage.setTitle("I HATE DIGGER$$$");
        primaryStage.show();
    }


    private int[][] getPicture(Image image) {
        final int WIDTH = (int) image.getWidth();
        final int HEIGHT = (int) image.getHeight();

        PixelReader pixelReader = image.getPixelReader();
        int[][] picture = new int[HEIGHT][WIDTH];

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                Color color = pixelReader.getColor(c, r);
                int pixel = (int)(color.getRed()*255);
                pixel *= 256;
                pixel += (int)(color.getGreen()*255);
                pixel *= 256;
                pixel += (int)(color.getBlue()*255);
                picture[r][c] = pixel;
                System.out.println(picture[r][c]);
            }
        }

        return picture;
    }

    final static int COEFICIENT = 32;

    private int[][] ditherImage(int[][] image){
        int[][] output = new int[image.length][image[0].length];
        for(int r = 0; r < output.length; r++){
            for(int c = 0; c < output[0].length; c++){
                int red,green,blue,gapr,gapg,gapb;
                red = (image[r][c] & 0x00ff0000) >> 16;
                green = (image[r][c] & 0x0000ff00) >> 8;
                blue = image[r][c] & 0x000000ff;
                gapr = red % COEFICIENT;
                red -= gapr;
                gapg = green % COEFICIENT;
                green -= gapg;
                gapb = blue % COEFICIENT;
                blue -= gapb;
                output[r][c] = ((red*256)+green)*256+blue;
                if(c + 1 < image[0].length){
                    red = (image[r][c+1] & 0x00ff0000) >> 16;
                    green = (image[r][c+1] & 0x0000ff00) >> 8;
                    blue = image[r][c+1] & 0x000000ff;
                    if(red+gapr>256)
                        image[r][c+1] += ((int)(gapr * 7/16.0))*256*256;
                    if(green+gapg>256)
                        image[r][c+1] += ((int)(gapg * 7/16.0))*256;
                    if(blue+gapb>256)
                        image[r][c+1] += ((int)(gapb * 7/16.0));

                }
                if(r + 1 < image.length && c > 0){
                    red = (image[r+1][c-1] & 0x00ff0000) >> 16;
                    green = (image[r+1][c-1] & 0x0000ff00) >> 8;
                    blue = image[r+1][c-1] & 0x000000ff;
                    if(red+gapr>256)
                        image[r+1][c-1] += ((int)(gapr * 3/16.0))*256*256;
                    if(green+gapg>256)
                        image[r+1][c-1] += ((int)(gapg * 3/16.0))*256;
                    if(blue+gapb>256)
                        image[r+1][c-1] += ((int)(gapb * 3/16.0));
                }
                if(r + 1 < image.length){
                    red = (image[r+1][c] & 0x00ff0000) >> 16;
                    green = (image[r+1][c] & 0x0000ff00) >> 8;
                    blue = image[r+1][c] & 0x000000ff;
                    if(red+gapr>256)
                        image[r+1][c] += ((int)(gapr * 5/16.0))*256*256;
                    if(green+gapg>256)
                        image[r+1][c] += ((int)(gapg * 5/16.0))*256;
                    if(blue+gapb>256)
                        image[r+1][c] += ((int)(gapb * 5/16.0));
                }
                if(c + 1 < image[0].length && r + 1 < image.length){
                    red = (image[r+1][c+1] & 0x00ff0000) >> 16;
                    green = (image[r+1][c+1] & 0x0000ff00) >> 8;
                    blue = image[r+1][c+1] & 0x000000ff;
                    if(red+gapr>256)
                        image[r+1][c+1] += ((int)(gapr / 16.0))*256*256;
                    if(green+gapg>256)
                        image[r+1][c+1] += ((int)(gapg / 16.0))*256;
                    if(blue+gapb>256)
                        image[r+1][c+1] += ((int)(gapb / 16.0));
                }

            }
        }
        return output;
    }

    private WritableImage getImage(int[][] image) {
        final int WIDTH = image[0].length;
        final int HEIGHT = image.length;

        WritableImage newImage = new WritableImage(WIDTH, HEIGHT);
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                int red,green,blue;
                red = (image[r][c] & 0x00ff0000) >> 16;
                green = (image[r][c] & 0x0000ff00) >> 8;
                blue = image[r][c] & 0x000000ff;
                System.out.println(red+""+green+""+blue);
                pixelWriter.setColor(c, r, new Color(red/256.0, green/256.0, blue/256.0, 1));
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

}
