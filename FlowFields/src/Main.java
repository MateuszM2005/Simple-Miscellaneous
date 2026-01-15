import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final int SECTOR_SIZE = 20;
    public static final int AMMOUNT = 1000;
    public static Particle[] particles = new Particle[AMMOUNT];
    public static Line[][] lines = new Line[HEIGHT/SECTOR_SIZE][WIDTH/SECTOR_SIZE];
    public static double[][] background = new double[HEIGHT/SECTOR_SIZE][WIDTH/SECTOR_SIZE];
    public static PerlinNoiseGenerator generator = new PerlinNoiseGenerator();
    public static void main(String[] args) {
        background = polarNoiseGetter(WIDTH/SECTOR_SIZE,HEIGHT/SECTOR_SIZE,0,0,0);
        launch();
    }

    public static double[][] noiseGetter(int w, int h, double shiftX, double shiftY){ // w,h array dimensions, increment - pixel gap between noise readings, shift movement of pattern rel to 0
        double[][] output = new double[h][w];
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                output[y][x] = generator.noise(x + shiftX, y+shiftY);
            }
        }
        return output;
    }

    public static double[][] polarNoiseGetter(int w, int h, double shiftX, double shiftY, double shiftZ){ // w,h array dimensions, increment - pixel gap between noise readings, shift movement of pattern rel to 0
        double[][] output = new double[h][w];
        int halfingHeight = h/2;
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                double Y, Z;
                if(y <= halfingHeight){
                    int xDistance = Math.min(x,w -x);
                    int yDistance = Math.min(y, halfingHeight-y);
                    Z = -Math.min(xDistance, yDistance);
                    Y = y;
                } else {
                    int xDistance = Math.min(x,w -x);
                    int yDistance = Math.min(y - halfingHeight, h-y);
                    Z = Math.min(xDistance,yDistance);
                    Y =  h - y;
                }
                output[y][x] = generator.noise(x+shiftX,Y+shiftY,Z+shiftZ+(h-1)/4);
            }
        }
        return output;
    }


    static double phase;
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);
        stage.show();

        Rectangle r = new Rectangle(WIDTH,HEIGHT);
        r.setFill(Color.BLACK);
        root.getChildren().add(r);

        for(int x = 0; x < WIDTH/SECTOR_SIZE; x++){
            for(int y = 0; y < HEIGHT/SECTOR_SIZE; y++){
                double[] d = dirToVector(background[y][x],SECTOR_SIZE);
                Line l = new Line((x + 1/2.0)*SECTOR_SIZE,(y + 1/2.0)*SECTOR_SIZE,(x + 1/2.0)*SECTOR_SIZE+d[0],(y + 1/2.0)*SECTOR_SIZE+d[1]);
                l.setStroke(Color.GREEN);
                lines[y][x] = l;
                root.getChildren().add(l);
            }
        }

        for(int i = 0;i < AMMOUNT; i++){
            particles[i] = new Particle(Math.random()*WIDTH,Math.random()*HEIGHT);
            root.getChildren().add(particles[i].rectangle);
            for(Rectangle rt : particles[i].fades){
                root.getChildren().add(rt);
            }
        }


        Timeline timeline = new Timeline(new KeyFrame(new Duration(100), event -> {
            phase = phase+0.1;
            double changeX = phase;
            double changeY = phase;
            double changeZ = phase;
            for(Particle p : particles){
                p.move();
            }
            background = polarNoiseGetter(WIDTH/SECTOR_SIZE,HEIGHT/SECTOR_SIZE,changeX, changeY,0);
//            for(int x = 0; x < WIDTH/SECTOR_SIZE; x++){
//                for(int y = 0; y < HEIGHT/SECTOR_SIZE; y++){
//                    double[] d = dirToVector(background[y][x],SECTOR_SIZE);
//                    lines[y][x].setEndX((x + 1/2.0)*SECTOR_SIZE+d[0]);
//                    lines[y][x].setEndY((y + 1/2.0)*SECTOR_SIZE+d[1]);
//                }
//            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();






//        Canvas canvas = new Canvas(WIDTH,HEIGHT);
//        root.getChildren().add(canvas);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        for(int x = 0; x < WIDTH; x++){
//            for (int y = 0; y < HEIGHT; y++){
//                gc.setFill(new Color(0,0,0,background[y][x]));
//                gc.fillRect(x,y,1,1);
//            }
//        }

    }

    public static double[] dirToVector(double dir, double lenght){
        double[] output = new double[2];   //0 - x ; 1 - y
        output[0] = Math.sin(dir*2*Math.PI)*lenght;
        output[1] = Math.cos(dir*2*Math.PI)*lenght;
        return output;

    }
}