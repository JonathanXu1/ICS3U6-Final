//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
class CustomMouseListener implements MouseListener {
  int[] xy = new int [3];
  public void mouseClicked(MouseEvent e) {
    //Have to manually change the coordinates to sync up with windows, must realign to mouse listener to fix.
  }
  
  public void mousePressed(MouseEvent e) {
    xy[0] = e.getX();
    xy[1] = e.getY();
    xy[2] = 1;
  }
  
  public void mouseReleased(MouseEvent e) {
    xy[2] = 0;
  }
  
  public void mouseEntered(MouseEvent e) {
  }
  
  public void mouseExited(MouseEvent e) {
  }
  
  public int[] getMouseXy(){
    return xy;
  }
}