/*
 * [WalkableTile.java];
 * 
 * These can be stepped on, and are a superclass for floors
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 
//Imports
/////////////////////
import java.awt.Color;

/////////////////////
abstract class WalkableTile extends Tile{
  private boolean steppedOn = false;
  WalkableTile(Color minimapColor, String info){
    super(minimapColor, info);
  }
  
  /**
   *stepped
   *Sets the stepped on as true (for the door)
   *@param: 
   *@return: 
   */
  public void stepped(){
    steppedOn = true;
  }
  
  //Getters and setters
  /**
   *checkStepped
   *Returns the stepped
   *@param: 
   *@return: A boolean
   */
  public boolean checkStepped(){
    return steppedOn;
  }
}
