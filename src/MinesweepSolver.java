import java.awt.*;
import java.awt.event.InputEvent;
import java.util.LinkedList;

/*
for d
-2: bomb
-1: unvisited
0-8 ya know
 */
public class MinesweepSolver {
    Robot robot;
    Rectangle playfield;
    int blocksize;
    Pos firstblock;
    int amountBlocksX = 30;
    int amountBlocksY = 15;
    int d[][];

    public MinesweepSolver(Rectangle playfield, int blocksize, Pos firstblock) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.playfield = playfield;
        this.blocksize = blocksize;
        this.firstblock = firstblock;
        d = new int[amountBlocksY][amountBlocksX];
        for(int i=0; i<amountBlocksY;i++){
            for(int j=0; j<amountBlocksX; j++){
                d[i][j] =-1;
            }
        }
    }

    public Pos getMousePos(int x,int y){
        Pos result = new Pos();
        result.x = firstblock.x+blocksize/2+blocksize*x;
        result.y = firstblock.y+blocksize/2+blocksize*y;
        return result;
    }

    public int getValue(Pos pos){
        return getValue(pos.x,pos.y);
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
        Pos start = new Pos(amountBlocksX/2,amountBlocksY/2)
        Pos startM = getMousePos(start.x,start.y);
        LinkedList<Pos> queue = new LinkedList<>();
        robot.mouseMove(startM.x,startM.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        d[start.y][start.x] = 0;
        queue.addAll(getAllNeighbours(start));
        while(!queue.isEmpty()){
            Pos p = queue.pop();
            if(getValue(p))
        }
    }

    public LinkedList<Pos> getAllNeighbours(Pos pos){
        int i = pos.x-1 < 0 ? 0 : pos.x-1;
        int j = pos.y-1 < 0 ? 0 : pos.y-1;
        int imax = pos.x+1 >= amountBlocksX ? amountBlocksX-1 : pos.x+1;
        int jmax = pos.y+1 >= amountBlocksY ? amountBlocksY-1 : pos.y+1;
        LinkedList<Pos> result = new LinkedList<>();
        for(;i<=imax;i++){
            for(;j<=jmax;j++){
                if(i!= pos.x && j!= pos.y)
                    result.push(new Pos(i,j));
            }
        }
    }
}
