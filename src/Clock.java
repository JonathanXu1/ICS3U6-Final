//Imports
/////////////////////
class Clock{
//The following variables are used to periodically measure the time
  private long oldTime;
  private long secondTime;
  private long currentTime;
//The default for this game is capped at 100 fps
  private int frame;
//The following static variables inidicate for many frames will run in a second
  private final long DELTA_LIMIT = 10000000;
  private final long SECOND_LIMIT = 1000000000;
  
//Constructor
  Clock(){
//Takes in the initial values for time
    this.oldTime = System.nanoTime();
    this.secondTime = System.nanoTime();
    this.currentTime = System.nanoTime();
    this.frame = 0;
  }
  
//Getters and setters
//Does not have a setter as it is not required
  /**
   *getFramePassed
   *Get if the frame has passed
   *@param: 
   *@return: A boolean
   */
  public boolean getFramePassed(){
    if ((currentTime-oldTime)>=DELTA_LIMIT){
      oldTime = currentTime;
      frame++;
      return (true);
    }else{
      return (false);
    }
  }
  /**
   *getSecondPassed
   *Returns if the second has passed
   *@param: 
   *@return: A boolean
   */
  public boolean getSecondPassed(){
    if ((currentTime-secondTime)>=SECOND_LIMIT){
      secondTime = currentTime;
      return (true);
    }else{
      return (false);
    }
  }
  /**
   *getTime
   *Gets the time
   *@param: 
   *@return: An long which is the curent time
   */
  public long getTime(){
    return (currentTime);
  }
  /**
   *setTime
   *Sets the time
   *@param: 
   *@return: 
   */
  public void setTime(){
    currentTime= System.nanoTime();
  }
//The following set and get the frame after 1 second has passed to find the fps
  /**
   *getFrame
   *Returns the frame number
   *@param: 
   *@return: An int
   */
  public int getFrame(){
    return (frame);
  }
  /**
   *setFrame
   *Sets the frame number
   *@param: int frame
   *@return: 
   */
  public void setFrame(int frame){
    this.frame = frame;
  }
}
