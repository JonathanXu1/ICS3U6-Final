/////////////////////
import java.awt.event.*;

/////////////////////
class StartListener implements ActionListener{
  private boolean startCondition;
  StartListener(){
    this.startCondition = false;
  }
  /**
   *actionPerformed
   *
   *@param: ActionEvent e
   *@return: 
   */
  public void actionPerformed(ActionEvent e){
    this.startCondition = true;
  }
  /**
   *getStart
   *
   *@param: 
   *@return: A boolean
   */
  public boolean getStart(){
    return (startCondition);
  }
  /**
   *setStart
   *
   *@param: boolean startCondition
   *@return: 
   */
  public void setStart(boolean startCondition){
    this.startCondition = startCondition;
  }
}
