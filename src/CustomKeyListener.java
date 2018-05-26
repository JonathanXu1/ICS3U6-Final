//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class CustomKeyListener implements KeyListener {
  boolean debugState = false;
  boolean moveUp = false;
  boolean moveDown = false;
  boolean moveLeft = false;
  boolean moveRight = false;
  int direction = 0;
  
  public void keyTyped(KeyEvent e) {
  }
  
  public void keyPressed(KeyEvent e) {
    //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
    
    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && !debugState) {  //If 'F1' is pressed
      debugState = true;
    } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && debugState) {  //If 'F1' is pressed again
      debugState = false;
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
  
  public void keyReleased(KeyEvent e) {
    if ((KeyEvent.getKeyText(e.getKeyCode()).equals("W"))||(KeyEvent.getKeyText(e.getKeyCode()).equals("S"))||(KeyEvent.getKeyText(e.getKeyCode()).equals("A"))|| (KeyEvent.getKeyText(e.getKeyCode()).equals("D"))){ 
      direction = 0;
    }
  }
  public int getDirection(){
    return (direction);
  }
  public boolean getDebugState(){
    return debugState;
  }
}