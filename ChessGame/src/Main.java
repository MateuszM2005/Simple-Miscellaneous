import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    static int[][] board = {
            {14,12,13,15,16,13,12,14},
            {11,11,11,11,11,11,11,11},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1},
            {4,2,3,5,6,3,2,4}
    };
    static final int SIZE = 100;
    static boolean isWhiteMove = true;
    static int xStart = -1;
    static int yStart = -1;
    static int xEnd = -1;
    static int yEnd = -1;


    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,8*SIZE,8*SIZE);
        stage.setScene(scene);
        stage.show();
        paint(root);
        scene.setOnMousePressed(mouseEvent -> {
            xStart = (int)(mouseEvent.getSceneX()/SIZE);
            yStart = (int)(mouseEvent.getSceneY()/SIZE);
        });
        scene.setOnMouseReleased(mouseEvent -> {
            xEnd = (int)(mouseEvent.getSceneX()/SIZE);
            yEnd = (int)(mouseEvent.getSceneY()/SIZE);
            move(xStart,yStart,xEnd,yEnd);
            paint(root);
        });

    }

    public static boolean contains(ArrayList<int[]> list,int y, int x){
        for(int[] i : list){
            if(i[0] == y && i[1] == x)return true;
        }
        return false;
    }

    public static ArrayList<int[]> sumLists(ArrayList<int[]> list1,ArrayList<int[]> list2){
        ArrayList<int[]> list = new ArrayList<>();
        for (int[] i : list1){
            list.add(i);
        }
        for (int[] i : list2){
            list.add(i);
        }
        return list;
    }

    public static void paint(AnchorPane root){
        for(int x = 0; x < 8 ; x++){
            for(int y = 0;y < 8 ; y++) {
                Color color = Color.WHITE;
                if((x+y) % 2 == 1){
                    color = Color.BLUE;
                }
                Rectangle tangle = new Rectangle(SIZE,SIZE,color);
                tangle.setX(SIZE*x);
                tangle.setY(SIZE*y);
                root.getChildren().add(tangle);
                if(board[y][x] != 0){
                    ImageView image = new ImageView("chess" + board[y][x]+".png");
                    image.setX(SIZE*x);
                    image.setFitWidth(SIZE);
                    image.setY(SIZE*y);
                    image.setFitHeight(SIZE);
                    root.getChildren().add(image);
                }
            }
        }
    }

    public static void move(int xStart,int yStart,int xEnd, int yEnd){
        if(board[yStart][xStart] == 1 && isWhiteMove){
            if(contains(moveWhitePawn(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 1;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }
        if(board[yStart][xStart] == 2 && isWhiteMove){
            if(contains(moveWhiteKnight(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 2;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }
        if(board[yStart][xStart] == 3 && isWhiteMove){
            if(contains(moveWhiteBishop(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 3;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }
        if(board[yStart][xStart] == 4 && isWhiteMove){
            if(contains(moveWhiteRook(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 4;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }
        if(board[yStart][xStart] == 5 && isWhiteMove){
            if(contains(moveWhiteQueen(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 5;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }
        if(board[yStart][xStart] == 6 && isWhiteMove){
            if(contains(moveWhiteKing(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 6;
                    board[yStart][xStart] = 0;
                    isWhiteMove = false;
                }
            }
        }

        if(board[yStart][xStart] == 11 && !isWhiteMove){
            if(contains(moveBlackPawn(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 11;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
        if(board[yStart][xStart] == 12 && !isWhiteMove){
            if(contains(moveBlackKnight(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 12;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
        if(board[yStart][xStart] == 13 && !isWhiteMove){
            if(contains(moveBlackBishop(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 13;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
        if(board[yStart][xStart] == 14 && !isWhiteMove){
            if(contains(moveBlackRook(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 14;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
        if(board[yStart][xStart] == 15 && !isWhiteMove){
            if(contains(moveBlackQueen(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 15;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
        if(board[yStart][xStart] == 16 && !isWhiteMove){
            if(contains(moveBlackKing(xStart,yStart),yEnd,xEnd)){
                if(checkForChecks(xStart,yStart,xEnd,yEnd,isWhiteMove)){
                    board[yEnd][xEnd] = 16;
                    board[yStart][xStart] = 0;
                    isWhiteMove = true;
                }
            }
        }
    }

    public static boolean checkForChecks(int xStart, int yStart, int xEnd,int yEnd, boolean didWhiteMove){
        int[][] board1 = new int[8][8];
        for(int x = 0; x < 8 ; x++) {
            for (int y = 0; y < 8; y++) {
                board1[x][y] = board[x][y];
            }
        }
        int val = board[yStart][xStart];
        board[yEnd][xEnd] = val;
        board[yStart][xStart] = 0;

        int kingX = -1;
        int kingY = -1;
        ArrayList<int[]> list = new ArrayList<>();

        if(didWhiteMove){
            for(int x = 0; x < 8 ; x++){
                for(int y = 0;y < 8 ; y++) {
                    if(board[y][x] == 11){
                        list.addAll(moveBlackPawn(x,y));
                    }else if(board[y][x] == 12){
                        list.addAll(moveBlackKnight(x,y));
                    }else if(board[y][x] == 13){
                        list.addAll(moveBlackBishop(x,y));
                    }else if(board[y][x] == 14){
                        list.addAll(moveBlackRook(x,y));
                    }else if(board[y][x] == 15){
                        list.addAll(moveBlackQueen(x,y));
                    }else if(board[y][x] == 16){
                        list.addAll(moveBlackKing(x,y));
                    }else if(board[y][x] == 6){
                        kingX = x;
                        kingY = y;
                    }
                }
            }
        }
        if(!didWhiteMove){
            for(int x = 0; x < 8 ; x++){
                for(int y = 0;y < 8 ; y++) {
                    if(board[y][x] == 1){
                        list.addAll(moveWhitePawn(x,y));
                    }else if(board[y][x] == 2){
                        list.addAll(moveWhiteKnight(x,y));
                    }else if(board[y][x] == 3){
                        list.addAll(moveWhiteBishop(x,y));
                    }else if(board[y][x] == 4){
                        list.addAll(moveWhiteRook(x,y));
                    }else if(board[y][x] == 5){
                        list.addAll(moveWhiteQueen(x,y));
                    }else if(board[y][x] == 6){
                        list.addAll(moveWhiteKing(x,y));
                    }else if(board[y][x] == 16){
                        kingX = x;
                        kingY = y;
                    }
                }
            }
        }
        board = board1;
        return !contains(list, kingY, kingX);
    }

    public static ArrayList<int[]> moveWhitePawn(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(y == 6 && board[y-1][x] == 0 && board[y-2][x] == 0){
            list.add(new int[]{y-2,x});
        }
        if(y > 0 && board[y-1][x] == 0 ){
            list.add(new int[]{y-1,x});
        }
        if(y > 0 && (x < 7 && board[y-1][x+1] > 10)){
            list.add(new int[]{y-1,x+1});
        }
        if(y > 0 && (x > 0 && board[y-1][x-1] > 10)){
            list.add(new int[]{y-1,x-1});
        }
        return list;
    }

    public static ArrayList<int[]> moveWhiteBishop(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            ymod++;
            if(xmod > 7 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod++;
            if(xmod < 0 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod++;
            ymod--;
            if(xmod > 7 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod--;
            if(xmod < 0 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveWhiteKnight(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(x+2 < 8 && y + 1 < 8 && (board[y+1][x+2] == 0 || board[y+1][x+2] > 10))list.add(new int[]{y+1,x+2});
        if(x+2 < 8 && y - 1 >= 0 && (board[y-1][x+2] == 0 || board[y-1][x+2] > 10))list.add(new int[]{y-1,x+2});
        if(x+1 < 8 && y + 2 < 8 && (board[y+2][x+1] == 0 || board[y+2][x+1] > 10))list.add(new int[]{y+2,x+1});
        if(x+1 < 8 && y - 2 >= 0 && (board[y-2][x+1] == 0 || board[y-2][x+1] > 10))list.add(new int[]{y-2,x+1});
        if(x-1 >= 0 && y + 2 < 8 && (board[y+2][x-1] == 0 || board[y+2][x-1] > 10))list.add(new int[]{y+2,x-1});
        if(x-1 >= 0 && y - 2 >= 0 && (board[y-2][x-1] == 0 || board[y-2][x-1] > 10))list.add(new int[]{y-2,x-1});
        if(x-2 >= 0 && y + 1 < 8 && (board[y+1][x-2] == 0 || board[y+1][x-2] > 10))list.add(new int[]{y+1,x-2});
        if(x-2 >= 0 && y - 1 >= 0 && (board[y-1][x-2] == 0 || board[y-1][x-2] > 10))list.add(new int[]{y-1,x-2});
        return list;
    }

    public static ArrayList<int[]> moveWhiteRook(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            if(xmod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            xmod--;
            if(xmod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            ymod--;
            if(ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            ymod++;
            if(ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveWhiteQueen(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            if(xmod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            xmod--;
            if(xmod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            ymod--;
            if(ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            ymod++;
            if(ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            xmod++;
            ymod++;
            if(xmod > 7 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod++;
            if(xmod < 0 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod++;
            ymod--;
            if(xmod > 7 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod--;
            if(xmod < 0 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] > 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveWhiteKing(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(y+1 < 8 && (board[y+1][x] == 0 || board[y+1][x] > 10))list.add(new int[]{y+1,x});
        if(y+1 < 8 && x+1 < 8 && (board[y+1][x+1] == 0 || board[y+1][x+1] > 10))list.add(new int[]{y+1,x+1});
        if(y+1 < 8 && x-1 >= 0 && (board[y+1][x-1] == 0 || board[y+1][x-1] > 10))list.add(new int[]{y+1,x-1});
        if(x+1 < 8 && (board[y][x+1] == 0 || board[y][x+1] > 10))list.add(new int[]{y,x+1});
        if(x-1 >= 0 && (board[y][x-1] == 0 || board[y][x-1] > 10))list.add(new int[]{y,x-1});
        if(x+1 < 8 && y - 1 >= 0 && (board[y-1][x+1] == 0 || board[y-1][x+1] > 10))list.add(new int[]{y-1,x+1});
        if(y - 1 >= 0 && (board[y-1][x] == 0 || board[y-1][x] > 10))list.add(new int[]{y-1,x});
        if(x-1 >= 0 && y - 1 >= 0 && (board[y-1][x-1] == 0 || board[y-1][x-1] > 10))list.add(new int[]{y-1,x-1});
        return list;
    }


    public static ArrayList<int[]> moveBlackPawn(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(y == 1 && board[y+1][x] == 0 && board[y+2][x] == 0){
            list.add(new int[]{y+2,x});
        }
        if(y > 0 && board[y+1][x] == 0 ){
            list.add(new int[]{y+1,x});
        }
        if(y < 7 && (x < 7 && board[y+1][x+1] > 0 && board[y+1][x+1] < 10)){
            list.add(new int[]{y+1,x+1});
        }
        if(y < 7 && (x > 0 && board[y+1][x-1] > 0 && board[y+1][x-1] < 10)){
            list.add(new int[]{y+1,x-1});
        }
        return list;
    }

    public static ArrayList<int[]> moveBlackBishop(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            ymod++;
            if(xmod > 7 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod++;
            if(xmod < 0 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod++;
            ymod--;
            if(xmod > 7 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod--;
            if(xmod < 0 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveBlackKnight(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(x+2 < 8 && y + 1 < 8 && (board[y+1][x+2] < 10))list.add(new int[]{y+1,x+2});
        if(x+2 < 8 && y - 1 >= 0 && (board[y-1][x+2] < 10))list.add(new int[]{y-1,x+2});
        if(x+1 < 8 && y + 2 < 8 && (board[y+2][x+1] < 10))list.add(new int[]{y+2,x+1});
        if(x+1 < 8 && y - 2 >= 0 && (board[y-2][x+1] < 10))list.add(new int[]{y-2,x+1});
        if(x-1 >= 0 && y + 2 < 8 && (board[y+2][x-1] < 10))list.add(new int[]{y+2,x-1});
        if(x-1 >= 0 && y - 2 >= 0 && (board[y-2][x-1] < 10))list.add(new int[]{y-2,x-1});
        if(x-2 >= 0 && y + 1 < 8 && (board[y+1][x-2] < 10))list.add(new int[]{y+1,x-2});
        if(x-2 >= 0 && y - 1 >= 0 && (board[y-1][x-2] < 10))list.add(new int[]{y-1,x-2});
        return list;
    }

    public static ArrayList<int[]> moveBlackRook(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            if(xmod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            xmod--;
            if(xmod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            ymod--;
            if(ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            ymod++;
            if(ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveBlackQueen(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        boolean stopper = true;
        int xmod = x;
        int ymod = y;
        while (stopper){
            xmod++;
            if(xmod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        while (stopper){
            xmod--;
            if(xmod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;

        while (stopper){
            ymod--;
            if(ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            ymod++;
            if(ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        ymod = y;
        while (stopper){
            xmod++;
            ymod++;
            if(xmod > 7 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod++;
            if(xmod < 0 || ymod > 7){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }

        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod++;
            ymod--;
            if(xmod > 7 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        stopper = true;
        xmod = x;
        ymod = y;
        while (stopper){
            xmod--;
            ymod--;
            if(xmod < 0 || ymod < 0){
                stopper = false;
            }else
            if(board[ymod][xmod] == 0){
                list.add(new int[]{ymod,xmod});
            }else
            if(board[ymod][xmod] < 10){
                list.add(new int[]{ymod,xmod});
                stopper = false;
            }else stopper = false;
        }
        return list;
    }

    public static ArrayList<int[]> moveBlackKing(int x,int y){
        ArrayList<int[]> list = new ArrayList<>();
        if(y+1 < 8 && (board[y+1][x] < 10))list.add(new int[]{y+1,x});
        if(y+1 < 8 && x+1 < 8 && ( board[y+1][x+1] < 10))list.add(new int[]{y+1,x+1});
        if(y+1 < 8 && x-1 >= 0 && ( board[y+1][x-1] < 10))list.add(new int[]{y+1,x-1});
        if(x+1 < 8 && (board[y][x+1] < 10))list.add(new int[]{y,x+1});
        if(x-1 >= 0 && (board[y][x-1] < 10))list.add(new int[]{y,x-1});
        if(x+1 < 8 && y - 1 >= 0 && ( board[y-1][x+1] < 10))list.add(new int[]{y-1,x+1});
        if(y-1 >= 0 && (board[y-1][x] < 10))list.add(new int[]{y-1,x});
        if(x-1 >= 0 && y - 1 >= 0 && ( board[y-1][x-1] < 10))list.add(new int[]{y-1,x-1});
        return list;
    }



}