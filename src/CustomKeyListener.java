//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class CustomKeyListener implements KeyListener {
  private boolean debugState = false;
  private boolean moveUp = false;
  private boolean moveDown = false;
  private boolean moveLeft = false;
  private boolean moveRight = false;
  private boolean tileUp = false;
  private boolean tileDown = false;
  private boolean tileLeft = false;
  private boolean tileRight = false;
  private int tileMoved;
  public void keyTyped(KeyEvent e) {
  }
  
  public void keyPressed(KeyEvent e) {
    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && !debugState) {  //If 'F1' is pressed
      debugState = true;
    } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && debugState) {  //If 'F1' is pressed again
      debugState = false;
    }
    if ((e.getKeyChar() =='w')||(e.getKeyChar() =='W')){
      moveUp = true;
      moveDown = false;
      moveLeft = false;
      moveRight = false;
    }
    if ((e.getKeyChar() =='s')||(e.getKeyChar() =='S')){
      moveUp = false;
      moveDown = true;
      moveLeft = false;
      moveRight = false;
    }
    if ((e.getKeyChar() =='a')||(e.getKeyChar() =='A')){
      moveUp = false;
      moveDown = false;
      moveLeft = true;
      moveRight = false;
    }
    if ((e.getKeyChar() =='d')||(e.getKeyChar() =='D')){
      moveUp = false;
      moveDown = false;
      moveLeft = false;
      moveRight = true;
    }
  }
  public void keyReleased(KeyEvent e) {
    if ((e.getKeyChar() =='w')||(e.getKeyChar() =='W')){
      moveUp = false;
    }
    if ((e.getKeyChar() =='s')||(e.getKeyChar() =='S')) { 
      moveDown = false;
    }
    if ((e.getKeyChar() =='a')||(e.getKeyChar() =='A')){ 
      moveLeft = false;
    }
    if ((e.getKeyChar() =='d')||(e.getKeyChar() =='D')){
      moveRight = false;
    }
  }
  public boolean getDebugState(){
    return debugState;
  }
  public void setAllDirection (){
    tileMoved=  (int)(Background.getTileSize()/10.0);
    Background.setOnTile();
    if (Background.getOnTile()){
      tileUp = false;
      tileDown = false;
      tileLeft = false;
      tileRight = false;
      if (moveUp){
        tileUp = true;
        tileDown = false;
        tileLeft = false;
        tileRight = false;
      }else if (moveDown){
        tileUp = false;
        tileDown = true;
        tileLeft = false;
        tileRight = false;
      }else if (moveLeft){
        tileUp = false;
        tileDown = false;
        tileLeft = true;
        tileRight = false;
      }else if (moveRight){
        tileUp = false;
        tileDown = false;
        tileLeft = false;
        tileRight = true;
      }else{
      }
    }
    if (tileUp){
      Background.setYDirection (-tileMoved);
      Background.setXDirection (0);
    }else if (tileDown){
      Background.setYDirection (tileMoved);
      Background.setXDirection (0);
    }else if (tileLeft){
      Background.setXDirection (-tileMoved);
      Background.setYDirection (0);
    }else if (tileRight){
      Background.setXDirection (tileMoved);
      Background.setYDirection (0);
    }else{
      Background.setYDirection (0);
      Background.setXDirection (0);
    }
  }
}