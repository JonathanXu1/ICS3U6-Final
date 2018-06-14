 //Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class CustomMouseListener implements MouseListener {
  //Controls the x and y position of the mouse
  private int[] xy = new int [2];
  //The following booleans control the state of the mouse
  private boolean pressed, hover;
  
  //Methods that are implemented from MouseListener
  public void mouseClicked(MouseEvent e) {
  }
  public void mousePressed(MouseEvent e) {
    xy[0] = e.getX();
    xy[1] = e.getY();
    pressed = true;
  }
  public void mouseReleased(MouseEvent e) {
    pressed = false;
  }
  public void mouseEntered(MouseEvent e) {
    hover = true;
  }
  public void mouseExited(MouseEvent e) {
    hover = false;
  }
  //End of methods that are implemented from MouseListener
  
  //Getters and setters
  //This returns the coords of the mouse as an array to shorten code
  public int[] getMouseXy(){
    return xy;
  }
  //There is no setter for this as it can only be controlled by the mouse
  public boolean getPressed(){
    return pressed;
  }
  public boolean getHover(){
    return hover;
  }
  //There is no setter for this as it can only be controlled by the mouse
  public void resetXY(){
    xy[0] =0;
    xy[1] = 0;
  }
}