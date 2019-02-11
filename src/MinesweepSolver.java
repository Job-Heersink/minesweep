import java.awt.*;
import java.awt.event.InputEvent;
import java.util.LinkedList;

public class MinesweepSolver {
    Robot robot;
    Rectangle playfield;
    int blocksize;
    Pos firstblock;
    int amountBlocksX = 30;
    int amountBlocksY = 15;

    public MinesweepSolver(Rectangle playfield, int blocksize, Pos firstblock) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.playfield = playfield;
        this.blocksize = blocksize;
        this.firstblock = firstblock;
    }

    public Pos getMousePos(int x,int y){
        Pos result = new Pos();
        result.x = firstblock.x+blocksize/2+blocksize*x;
        result.y = firstblock.y+blocksize/2+blocksize*y;
        return result;
    }

    public int getValue(int x, int y){
        Color zero = new Color(0);
        Color one = new Color(0);
        Color two = new Color(0);
        Color three = new Color(0);
        Color four = new Color(0);
        Color five = new Color(0);
        Color six = new Color(0);
        Color seven = new Color(0);
        Color eight = new Color(0);
        Color unknown = new Color(0);
        Color bomb = new Color(0);
        Color flag = new Color(0);

        Pos blockPos = getMousePos(x,y);
        Color pick = robot.getPixelColor(blockPos.x,blockPos.y);
        if(pick.getRGB() == zero.getRGB()){
            return 0;
        }else if(pick.getRGB() == one.getRGB()){
            return 1;
        }else if(pick.getRGB() == two.getRGB()){
            return 2;
        }else if(pick.getRGB() == three.getRGB()){
            return 3;
        }else if(pick.getRGB() == four.getRGB()){
            return 4;
        }else if(pick.getRGB() == five.getRGB()){
            return 5;
        }else if(pick.getRGB() == six.getRGB()){
            return 6;
        }else if(pick.getRGB() == seven.getRGB()){
            return 7;
        }else if(pick.getRGB() == eight.getRGB()){
            return 8;
        }
        return -1;
    }

    public void solve(){
        Pos start = getMousePos(amountBlocksX/2,amountBlocksY/2);
        LinkedList<Pos> queue = new LinkedList<>();
        robot.mouseMove(start.x,start.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
    }
}
