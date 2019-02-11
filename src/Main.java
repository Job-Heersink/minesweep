import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static java.awt.MouseInfo.getPointerInfo;

//first block is at x: 30 y: 183
//block size is 16x16
//amount of blocks 30x16
//start button is at 270 160

public class Main {
    public static void main(String[] args) {
            Rectangle playfield = new Rectangle(20,131,519,448);
            MinesweepSolver mss = new MinesweepSolver(playfield,16,new Pos(30,183));
            //mss.solve();
        mss.solve();
        mss.printD();
        mss.printQueue();
       /* try {
            Robot robot = new Robot();
            while(true){
                getPointerInfo().getLocation().getX();
                System.out.println(robot.getPixelColor((int)getPointerInfo().getLocation().getX(),(int)getPointerInfo().getLocation().getY()));
                Thread.sleep(100);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
