//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
class CustomMouseListener implements MouseListener {
  int[] xy = new int [2];
  private boolean pressed, hover;
  public void mouseClicked(MouseEvent e) {
    //Have to manually change the coordinates to sync up with windows, must realign to mouse listener to fix.
  }
  
  public void mousePressed(MouseEvent e) {
    xy[0] = e.getX();
    xy[1] = e.getY();
    pressed = true;
  }
  
  public void mouseReleased(MouseEvent e) {
    pressed = false;
    System.out.print ("t");
  }
  
  public void mouseEntered(MouseEvent e) {
    hover = true;
  }
  
  public void mouseExited(MouseEvent e) {
    hover = false;
  }
  
  public int[] getMouseXy(){
    return xy;
  }
  
  public boolean getPressed(){
    return pressed;
  }
  
  public boolean getHover(){
    return hover;
  }
}