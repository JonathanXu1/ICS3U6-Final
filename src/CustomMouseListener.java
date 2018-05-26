//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
class CustomMouseListener implements MouseListener {
  int[] xy = new int [2];
  public void mouseClicked(MouseEvent e) {
    System.out.println("Mouse Clicked");
    System.out.println("X:"+e.getX() + " y:"+e.getY());
    xy[0] = e.getX();
    xy[1] = e.getY();
  }
  
  public void mousePressed(MouseEvent e) {
  }
  
  public void mouseReleased(MouseEvent e) {
  }
  
  public void mouseEntered(MouseEvent e) {
  }
  
  public void mouseExited(MouseEvent e) {
  }
  
  public int[] getMouseXy(){
    return xy;
  }
}