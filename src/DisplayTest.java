//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class DisplayTest{
  public static void main (String[] args) throws Exception{
    Display disp = new Display ();
    
    long oldTime = System.nanoTime();
    long secondTime = System.nanoTime();
    long currentTime = System.nanoTime();
    int frame=0;
    final long DELTA_LIMIT = 10000000;
    final long SECOND_LIMIT = 1000000000;
    while (true){
      currentTime= System.nanoTime();
      if ((currentTime-oldTime)>=DELTA_LIMIT){
        disp.getListen();
        disp.refreshAll();
        oldTime = currentTime;
        frame++;
      }
      if ((currentTime-secondTime)>=SECOND_LIMIT){
        secondTime = currentTime;
        System.out.println (frame);
        disp.setfps(frame);
        frame =0;
      }
    }
  }
}