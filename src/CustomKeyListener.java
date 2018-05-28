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
  private boolean tiling = false;
  private int tileMoved;
  public void keyTyped(KeyEvent e) {
  }
  
  public void keyPressed(KeyEvent e) {
    //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

    if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && !debugState) {  //If 'F1' is pressed
      debugState = true;
    } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && debugState) {  //If 'F1' is pressed again
      debugState = false;
    }
    if (!tiling){
      if (e.getKeyChar() =='w'&&((moveUp)||!(getMoving()))){
        moveUp =true;
        moveDown =false;
        moveLeft =false;
        moveRight =false;
      }
      if (e.getKeyChar() =='s'&&((moveDown)||!(getMoving()))){
        moveUp =false;
        moveDown =true;
        moveLeft =false;
        moveRight =false;
      }
      if (e.getKeyChar() =='a'&&((moveLeft)||!(getMoving()))){
        moveUp =false;
        moveDown =false;
        moveLeft =true;
        moveRight =false;
      }
      if (e.getKeyChar() =='d'&&((moveRight)||!(getMoving()))){
        moveUp =false;
        moveDown =false;
        moveLeft =false;
        moveRight =true;
      }
    }
  }
  
  public void keyReleased(KeyEvent e) {
    //if ((KeyEvent.getKeyText(e.getKeyCode()).equals("W"))||(e.getKeyChar() =='s')||(e.getKeyChar() =='a')|| (e.getKeyChar() =='d')){ 
    // direction = 0;
    //}
    if (!tiling){
      if ((e.getKeyChar() =='w')&&(moveUp)){
        moveUp =false;
        tileUp = true;
        tiling = true;
      }
      if ((e.getKeyChar() =='s')&&(moveDown)) { 
        moveDown =false;
        tileDown= true;
        tiling = true;
      }
      if ((e.getKeyChar() =='a')&&(moveLeft)) { 
        moveLeft =false;
        tileLeft= true;
        tiling = true;
      }
      if ((e.getKeyChar() =='d')&&(moveRight)) {
        moveRight =false;
        tileRight =true;
        tiling = true;
      }
    }
  }
  public boolean getDebugState(){
    return debugState;
  }
  public void setAllDirection (){
    tileMoved=  (int)(Background.getTileSize()/10.0);
    Background.setOnTile();
    if (moveUp||(tileUp)){
      Background.setYDirection (-tileMoved);
      Background.setXDirection (0);
    }else if (moveDown||(tileDown)){
      Background.setYDirection (tileMoved);
      Background.setXDirection (0);
    }else if (moveLeft||(tileLeft)){
      Background.setXDirection (-tileMoved);
      Background.setYDirection (0);
    }else if (moveRight||(tileRight)){
      Background.setXDirection (tileMoved);
      Background.setYDirection (0);
    }else{
      Background.setYDirection (0);
      Background.setXDirection (0);
    }
    if ((Background.getOnTile())&&((tileUp)||(tileDown)||(tileLeft)||(tileRight))){
      moveUp = false;
      moveDown = false;
      moveLeft = false;
      moveRight = false;
      tileUp = false;
      tileDown = false;
      tileLeft = false;
      tileRight = false;
      tiling = false;
      Background.setYDirection (0);
      Background.setXDirection (0);
    }
  }
  public boolean getMoving(){
    if (!(moveUp)&&!(moveDown)&&!(moveLeft)&&!(moveRight)&&!(tileUp)&&!(tileDown)&&!(tileLeft)&&!(tileRight)){
      return (false);
    }else{
      return (true);
    }
  }
}