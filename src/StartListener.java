/*
 * [StartListener.java];
 * 
 * This is the player's character
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 
//Imports
/////////////////////
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/////////////////////
class StartListener implements ActionListener{
  private boolean startCondition;
  StartListener(){
    this.startCondition = false;
  }
  /**
   *actionPerformed
   *This modifies the start condition
   *@param: ActionEvent e
   *@return: 
   */
  public void actionPerformed(ActionEvent e){
    this.startCondition = true;
  }
  
  //Getters and Setters
  /**
   *getStart
   *This returns the start condition
   *@param: 
   *@return: A boolean
   */
  public boolean getStart(){
    return (startCondition);
  }
  /**
   *setStart
   *This sets the start condition
   *@param: boolean startCondition
   *@return: 
   */
  public void setStart(boolean startCondition){
    this.startCondition = startCondition;
  }
}
