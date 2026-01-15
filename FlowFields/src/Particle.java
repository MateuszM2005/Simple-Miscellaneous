import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Particle {
    double xPos;
    double yPos;
    double direction;
    double speed = 5;
    Rectangle rectangle = new Rectangle();
    final int FADING_TIME = 10;
    Rectangle[] fades = new Rectangle[FADING_TIME];
    final double RED = Math.random()*20/256;
    final double GREEN = Math.random()*20/256;
    final double BLUE = (Math.random()*50+206)/256;
    final double OPACITY = 1;

    public Particle(double x, double y){
        xPos = x;
        yPos = y;
        rectangle.setWidth(1);
        rectangle.setHeight(1);
        rectangle.setFill(new Color(RED,GREEN,BLUE,OPACITY));
        rectangle.setX(xPos);
        rectangle.setY(yPos);
        direction = getDir();
        for(int i = 0; i < FADING_TIME; i++){
            fades[i] = new Rectangle();
            fades[i].setFill(new Color(RED,GREEN,BLUE,1));
            fades[i].setWidth(1);
            fades[i].setHeight(1);
            fades[i].setX(xPos);
            fades[i].setY(yPos);
        }
    }

    public void move(){
        Rectangle ref = fades[FADING_TIME-1];
        for(int i = FADING_TIME-1; i > 0; i--){
            fades[i] = fades[i-1];
            fades[i].setOpacity((FADING_TIME-i)/(FADING_TIME+1.0)); //OPACITY*(FADING_TIME-i)/(FADING_TIME+1)
        }
        fades[0] = ref;
        fades[0].setX(xPos);
        fades[0].setY(yPos);
        fades[0].setOpacity((FADING_TIME)/(FADING_TIME+1.0)); //OPACITY*(FADING_TIME)/(FADING_TIME+1)


        direction = getDir();
        double[] movement = Main.dirToVector(direction,speed);
        xPos += movement[0];
        yPos += movement[1];
        if(xPos > Main.WIDTH){
            xPos -= Main.WIDTH;
        }
        if(xPos < 0){
            xPos += Main.WIDTH;
        }
        if(yPos > Main.HEIGHT){
            yPos -= Main.HEIGHT;
        }
        if(yPos < 0){
            yPos += Main.HEIGHT;
        }
        rectangle.setX(xPos);
        rectangle.setY(yPos);
    }

    public double getDir(){
        return getAvgDir(Main.background[(int)(yPos/ Main.SECTOR_SIZE)][(int)(xPos/ Main.SECTOR_SIZE)],2,direction,0, Math.random(),0);
    }

    public double getAvgDir(double d1, double w1, double d2, double w2){
        if(w1+w2 == 0) return 0;
        if(Math.abs(d1-d2) > 0.5) if(d1 > d2) d1 += 1; else d2 += 1;
        double out = (d1*w1+d2*w2)/(w1+w2);
        if (out > 1) out -= 1;
        return out;
    }
    public double getAvgDir(double d1, double w1, double d2, int w2, double d3, double w3){
        return getAvgDir(d1,w1,getAvgDir(d2,w2,d3,w3),w2+w3);
    }


}
