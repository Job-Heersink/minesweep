import java.awt.*;

import static java.awt.MouseInfo.getPointerInfo;

public class Main {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // The the pixel color information at 20,
            robot.mouseMove(20000,1000);

            System.out.println("w: "+screenSize.getHeight()+" ,h: "+screenSize.getWidth());
            int count =0;
            while(count < 5000){
                robot.mouseMove(count*10,count);
                System.out.println(getPointerInfo().getLocation());
                Thread.sleep(100);
                count++;
            }

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
