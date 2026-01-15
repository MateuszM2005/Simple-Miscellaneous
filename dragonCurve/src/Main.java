import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,1920,1080);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnMousePressed(event -> {primaryStage.close();});


        dragonCurve(root, 500 ,640,1420,640,17);
    }


    public static void dragonCurve(AnchorPane root, double x1,double y1,double x2,double y2, int repetitions){
        double xoutput;
        double youtput;
        if(repetitions > 0){
            if(x1==x2 || y1==y2){ //linia nie pod kątem
                if(x1==x2){
                    if(y1<y2){
                        xoutput = x1 + (Math.abs((y1-y2)/2));
                        youtput = Math.abs((y1+y2)/2);
                    }else{
                        xoutput = x1 - (Math.abs((y1-y2)/2));
                        youtput = Math.abs((y1+y2)/2);
                    }
                }else{
                    if(x1<x2){
                        xoutput =  Math.abs((x1+x2)/2);
                        youtput = y1 -  Math.abs((x1-x2)/2);
                    }else{
                        xoutput =  Math.abs((x1+x2)/2);
                        youtput = y1 +  Math.abs((x1-x2)/2);
                    }
                }
            }else {
                if (x1 < x2) {
                    if (y1 < y2) {
                        xoutput = x2;
                        youtput = y1;
                    } else {
                        xoutput = x1;
                        youtput = y2;
                    }
                } else {
                    if (y1 < y2) {
                        xoutput = x1;
                        youtput = y2;
                    } else {
                        xoutput = x2;
                        youtput = y1;
                    }
                }
            }
            dragonCurve(root, x1,y1,xoutput,youtput,repetitions-1);
            dragonCurve(root, x2,y2,xoutput,youtput,repetitions-1);
        }else{
            Line line = new Line(x1,y1,x2,y2);
            line.setFill(Color.BLACK);
            root.getChildren().add(line);
        }
    }
}