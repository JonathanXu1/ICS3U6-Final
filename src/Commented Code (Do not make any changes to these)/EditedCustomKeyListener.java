//Keyboard imports
/////////////////////
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/////////////////////
class CustomKeyListener implements KeyListener {
  boolean debugState = false;
  boolean moveUp = false;
  boolean moveDown = false;
  boolean moveLeft = false;
  boolean moveRight = false;
  int direction = 0;
  
  /**
   *keyTyped
   *
   *@param: KeyEvent e
   *@return: 
   */
  public void keyTyped(KeyEvent e) {
  }
  
  /**
   *keyPressed
   *
   *@param: KeyEvent e
   *@return: 
   */
  public void keyPressed(KeyEvent e) {
//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
    
    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1")) {  //If 'F1' is pressed
      debugState = true;
    } 
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("W"))&&((direction==0)||(direction==1))) {
      direction =1;
    }
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("S"))&&((direction==0)||(direction==2))) { 
      direction =2;
    }
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("A"))&&((direction==0)||(direction==3))) { 
      direction =3;
    }
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("D"))&&((direction==0)||(direction==4))) {
      direction =4;
    }
  }   
  
  /**
   *keyReleased
   *
   *@param: KeyEvent e
   *@return: 
   */
  public void keyReleased(KeyEvent e) {
    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1")) {
      debugState = false;
    }
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("W"))||(KeyEvent.getKeyText(e.getKeyCode()).equals("S"))||(KeyEvent.getKeyText(e.getKeyCode()).equals("A"))||( (KeyEvent.getKeyText(e.getKeyCode())).equals("D"))){ 
      direction = 0;
    }
  }
  /**
   *getDirection
   *
   *@param: 
   *@return: An int
   */
  public int getDirection(){
    return (direction);
  }
  /**
   *getDebugState
   *
   *@param: 
   *@return: A boolean
   */
  public boolean getDebugState(){
    return debugState;
  }
}
