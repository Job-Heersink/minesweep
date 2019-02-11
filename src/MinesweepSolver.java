import java.awt.*;
import java.awt.event.InputEvent;
import java.util.LinkedList;

import static java.lang.Math.abs;

/*
for d
-2: bomb
-1: unvisited
0-8 ya know
 */
public class MinesweepSolver {
    private Robot robot;
    private Rectangle playfield;
    private int blocksize;
    private Pos firstblock;
    private int amountBlocksX = 30;
    private int amountBlocksY = 15;
    private int d[][];
    LinkedList<Pos> queue;

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
        queue = new LinkedList<>();
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
        //pos x+7 y+4
        Color zero = new Color(189,189,189);
        Color one = new Color(0,0,255);
        Color two = new Color(0,123,0);
        Color three = new Color(255,0,0);
        Color four = new Color(0,0,128);
        Color five = new Color(128,0,0);
        Color six = new Color(0,128,128);
        Color seven = new Color(0,0,0);
        Color eight = new Color(128,128,128);
        Color unknown = new Color(255,255,255);
        Color bomb = new Color(0);
        Color flag = new Color(0);

        Pos blockPos = getMousePos(x,y);
        Color pick2 = robot.getPixelColor(blockPos.x-8,blockPos.y-8);
        if(isSameColor(pick2,unknown)){
            assert(false);
            return -1;
        }
        Color pick = robot.getPixelColor(blockPos.x-1,blockPos.y-4);
        if(isSameColor(pick,zero)){
            return 0;
        }else if(isSameColor(pick,one)){
            return 1;
        }else if(isSameColor(pick,two)){
            return 2;
        }else if(isSameColor(pick,three)){
            return 3;
        }else if(isSameColor(pick,four)){
            return 4;
        }else if(isSameColor(pick,five)){
            return 5;
        }else if(isSameColor(pick,six)){
            return 6;
        }else if(isSameColor(pick,seven)){
            return 7;
        }else if(isSameColor(pick,eight)){
            return 8;
        }
        return -1;
    }

    public void solve(){
        Pos start = new Pos(amountBlocksX/2,amountBlocksY/2);
        discover(start);
        queue = new LinkedList<>();
        read();
        System.out.println(" null enqueued");
        int cnt = 0;
        while(!queue.isEmpty() && cnt< 100){
            cnt++;
            Pos p = queue.pop();
            Pos temp = getMousePos(p.x,p.y);
            robot.mouseMove(temp.x,temp.y);
            if(d[p.y][p.x] == -1){
                d[p.y][p.x] = getValue(p);
                System.out.println("found value "+d[p.y][p.x]);
            }
            LinkedList<Pos> neighbours = getAllNeighbours(p);
            LinkedList<Pos> bombs = getAllBombs(neighbours);
            LinkedList<Pos> unvisited = getAllUnvisited(neighbours);
            if(d[p.y][p.x] == 0){
                System.out.println("found a null");
                read();
            } else if(d[p.y][p.x] == bombs.size()){
                System.out.println("found free spaces");
                for(Pos px: unvisited){
                    discover(px);
                    queue.push(px);
                }
            }else if(d[p.y][p.x] - bombs.size() == unvisited.size()){
                System.out.println("found bombs");
                for(Pos px: unvisited){
                    mark(px);
                    queue.addAll(getAllVisited(getAllNeighbours(px)));
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<Pos> getAllNeighbours(Pos pos){
        int i = pos.x-1 < 0 ? 0 : pos.x-1;
        int imax = pos.x+1 >= amountBlocksX ? amountBlocksX-1 : pos.x+1;
        int jmax = pos.y+1 >= amountBlocksY ? amountBlocksY-1 : pos.y+1;
        LinkedList<Pos> result = new LinkedList<>();
        for(;i<=imax;i++){
            int j = pos.y-1 < 0 ? 0 : pos.y-1;
            for(;j<=jmax;j++){
                if(i!= pos.x || j!= pos.y)
                    result.push(new Pos(i,j));
            }
        }
        return result;
    }

    public LinkedList<Pos> getAllBombs(LinkedList<Pos> list){
        LinkedList<Pos> result = new LinkedList<>();
        for(Pos p:list){
            if(d[p.y][p.x] == -2){
                result.add(new Pos(p.x,p.y));
            }
        }
        return result;
    }

    public LinkedList<Pos> getAllUnvisited(LinkedList<Pos> list){
        LinkedList<Pos> result = new LinkedList<>();
        for(Pos p:list){
            if(d[p.y][p.x] == -1){
                result.add(new Pos(p.x,p.y));
            }
        }
        return result;
    }

    public LinkedList<Pos> getAllVisited(LinkedList<Pos> list){
        LinkedList<Pos> result = new LinkedList<>();
        for(Pos p:list){
            if(d[p.y][p.x] >= 0){
                result.add(new Pos(p.x,p.y));
            }
        }
        return result;
    }

    public boolean isSameColor(Color c1, Color c2){
        Boolean r = abs(c1.getRed()-c2.getRed()) < 20;
        Boolean g = abs(c1.getGreen()-c2.getGreen()) < 20;
        Boolean b = abs(c1.getBlue()-c2.getBlue()) < 20;
        return r & g & b;
    }

    public void discover(Pos p){
        Pos pm = getMousePos(p.x,p.y);
        robot.mouseMove(pm.x,pm.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        d[p.y][p.x] = getValue(p.x,p.y);
    }

    public void mark(Pos p){
        Pos pm = getMousePos(p.x,p.y);
        robot.mouseMove(pm.x,pm.y);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        d[p.y][p.x] = -2;
    }

    public void read(){
        for(int i=0; i<amountBlocksY; i++){
            for(int j=0; j<amountBlocksX; j++){
                d[i][j] = getValue(new Pos(j,i));
                if(d[i][j]>0){
                    queue.push(new Pos(j,i));
                }
            }
        }
    }

    public void test(){
        Pos random = getMousePos(3,2);
        System.out.println(robot.getPixelColor(random.x-8,random.y-8));
    }

    public void printD(){
        for(int i=0; i<amountBlocksY; i++){
            for(int j=0; j<amountBlocksX; j++){
                String s;
                if(d[i][j]<0){
                    s = " ";
                }else{
                    s="  ";
                }
                System.out.print(d[i][j]+s);
            }
            System.out.println(" ");
        }
    }

    public void printQueue(){
        for(Pos p: queue){
            System.out.println("x: "+p.x+" y: "+p.y+" val: "+d[p.y][p.x]);
        }
    }
}
