//Imports
//Keyboard imports
/////////////////////
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/////////////////////
class CustomKeyListener implements KeyListener {
  
//Movement conditions
  private boolean moveUp = false;
  private boolean moveDown = false;
  private boolean moveLeft = false;
  private boolean moveRight = false;
  private boolean tileUp = false;
  private boolean tileDown = false;
  private boolean tileLeft = false;
  private boolean tileRight = false;
//Debug conditions
  private boolean debugState = false;
//Movement responses
  private int [] xyDirection = new int [2];
  private final int TILE_MOVED =10; //This is 1/10th of the tile size, but as long as it is a multiple of 100, anything goes
  
//Methods that are implemented from KeyListener
  /**
   *keyTyped
   *This is not used
   *@param: KeyEvent e
   *@return: 
   */
  public void keyTyped(KeyEvent e) {
  }
  /**
   *keyPressed
   *This activates debug state and movement
   *@param: KeyEvent e
   *@return: 
   */
  public void keyPressed(KeyEvent e) {
    if (KeyEvent.getKeyText(e.getKeyCode()).equals(("F1") )&&( !debugState)) {  //If 'F1' is pressed
      debugState = true;
    } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("F1") && debugState) {  //If 'F1' is pressed again
      debugState = false;
    }
//End of methods that are implemented from KeyListener
    
    /*
     When the key is pressed, a movement variable is set to be true, and these variables can override other directions
     at any given time. However, it is tile___ that controls the actual movement of the background. Tiles cannot 
     override each other, unlike the move___. Without the move__'s however, there would be a small delay when
     switching between keys.
     */
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
  /**
   *keyReleased
   *This deactivates movement
   *@param: KeyEvent e
   *@return: 
   */
  public void keyReleased(KeyEvent e) {
//When key is released, the corresponding movement variable is immediately set as false
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
  
//Getters and setters
  /**
   *getDebugState
   *This returns whether or not the game is in debug
   *@param: 
   *@return: A boolean
   */
  public boolean getDebugState(){
    return (debugState);
  }
//No setter, as it can only be modified by the key F1
  /**
   * getAllDirection
   *Returns the direction for movement
   *@param: 
   *@return: An int[]
   */
  public int[] getAllDirection(){
    xyDirection[0]=0;
    xyDirection[1]=0;
    if (tileUp){
      xyDirection[1]=-TILE_MOVED;
    }else if (tileDown){
      xyDirection[1]=TILE_MOVED;
    }else if (tileLeft){
      xyDirection[0]=-TILE_MOVED;
    }else if (tileRight){
      xyDirection[0]=TILE_MOVED;
    }
    return (xyDirection);
  }
  /**
   *setAllDirection 
   *Sets all the directions
   *@param: 
   *@return: 
   */
  public void setAllDirection (){
    tileUp = false;
    tileDown = false;
    tileLeft = false;
    tileRight = false;
    if (moveUp){
      tileUp = true;
    }else if (moveDown){
      tileDown = true;
    }else if (moveLeft){
      tileLeft = true;
    }else if (moveRight){
      tileRight = true;
    }
  }
  /**
   *setToZero 
   *Sets the directions to zero
   *@param: 
   *@return: 
   */
  public void setToZero (){
    xyDirection[0]=0;
    xyDirection[1]=0;
  }
}
