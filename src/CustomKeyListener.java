//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class CustomKeyListener implements KeyListener {
  boolean debugState = false;
  
  public void keyTyped(KeyEvent e) {
  }
  
  public void keyPressed(KeyEvent e) {
    //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
    
    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1")) {  //If 'F1' is pressed
      this.debugState = true;
    } 
    //Add keylisteners for other things here
    /*else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
      System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
    }*/
  }   
  
  public void keyReleased(KeyEvent e) {
    this.debugState = false;
  }
  
  public boolean getDebugState(){
    return debugState;
  }
}