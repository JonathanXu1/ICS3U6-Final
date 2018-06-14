/*
 * [CustomMouseListener.java];
 * 
 * This senses the mouse motion
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 
//Imports
/////////////////////
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/////////////////////
class CustomMouseListener implements MouseListener {
//Controls the x and y position of the mouse
  private int[] xy = new int [2];
//The following booleans control the state of the mouse
  private boolean pressed, hover;
  
//Methods that are implemented from MouseListener
  /**
   *mouseClicked
   *Not used
   *@param: MouseEvent e
   *@return: 
   */
  public void mouseClicked(MouseEvent e) {
  }
  /**
   *mousePressed
   *Determines if pressed is true, gets the position
   *@param: MouseEvent e
   *@return: 
   */
  public void mousePressed(MouseEvent e) {
    xy[0] = e.getX();
    xy[1] = e.getY();
    pressed = true;
  }
  /**
   *mouseReleased
   *Determines if pressed is false
   *@param: MouseEvent e
   *@return: 
   */
  public void mouseReleased(MouseEvent e) {
    pressed = false;
  }
  /**
   *mouseEntered
   *Determines hovering to be true
   *@param: MouseEvent e
   *@return: 
   */
  public void mouseEntered(MouseEvent e) {
    hover = true;
  }
  /**
   *mouseExited
   *Determines hovering to be false
   *@param: MouseEvent e
   *@return: 
   */
  public void mouseExited(MouseEvent e) {
    hover = false;
  }
//End of methods that are implemented from MouseListener
  
//Getters and setters
//This returns the coords of the mouse as an array to shorten code
  /**
   * getMouseXy
   *Returns the mouse coordinates
   *@param: 
   *@return: An int[]
   */
  public int[] getMouseXy(){
    return xy;
  }
//There is no setter for this as it can only be controlled by the mouse
  /**
   *getPressed
   *Returns the pressed state
   *@param: 
   *@return: A boolean
   */
  public boolean getPressed(){
    return pressed;
  }
  //There is no setter for this as it can only be controlled by the mouse
  /**
   *getHover
   *Returns the hover state
   *@param: 
   *@return: A boolean
   */
  public boolean getHover(){
    return hover;
  }
//There is no setter for this as it can only be controlled by the mouse
  /**
   *resetXY
   *Sets the x and y to zero
   *@param: 
   *@return: 
   */
  public void resetXY(){
    xy[0] =0;
    xy[1] = 0;
  }
}
