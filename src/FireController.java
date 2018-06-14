/////////////////////
class FireController {
  private int currentY, currentX;
  private int counter;
  private int trailY, trailX;
  private int moveY, moveX;
  private int startY, startX;
  private double angle;
  private boolean collision = false;
  private int shotLength;
  
  FireController(int mX, int mY, int sX, int sY) {
    
    this.startY = sY;//maxY/2;
    this.startX = sX;//maxX/2;
  }  
  
  
  /**
   *calculate
   *Calculates the collison
   *@param: 
   *@return: 
   */
  public void calculate() {    
    if (counter <= shotLength) {
      trailX = currentX - (counter)*moveX;
      trailY = currentY - (counter)*moveY;
    } else {
      trailY = currentX - moveY*shotLength;
      trailX = currentX - moveX*shotLength;
    }         
    counter++;    
    
    if (!collision) {
      currentY = currentY + moveY;
      currentX = currentX + moveX;
      
    }
  } 
  //Getters and Setters
  /**
   *setupProjectile
   *Sets the projectile
   *@param: The int targetX, the int targetY, and the int shotLen
   *@return: 
   */
  public void setupProjectile(int targetX, int targetY, int shotLen) {    
    counter = 0;
    
    this.shotLength = shotLen;
    
    double deltaY = targetY - startY;
    double deltaX = targetX - startX;
    
    int dSign = 1;
    int rSign = 1;
    
    if (deltaY < 0) {
      dSign = -1;
    }        
    if (deltaX < 0) {
      rSign = -1;      
    }
    
    double slope = (deltaY/deltaX);
    
    
    angle = Math.atan((Math.abs(deltaY)/Math.abs(deltaX)));
    
    
    if ((deltaX < 0 )&&( deltaY <= 0)) { //Quadrant 2
      angle = Math.PI - angle;
    } else if (deltaX <= 0 && deltaY > 0) { //Quadrant 3
      angle = Math.PI + angle;
    } else if (deltaX >= 0 && deltaY > 0) { //Quadrant 4
      angle = 2*Math.PI - angle;
    }
    
    
    int moveY, moveX;
    
    if (deltaX == 0) {
      moveY = 32;
      moveX = 0;
    } else {
      
      double moveXTemp = Math.sqrt(1024/(Math.pow(slope,2) + 1));
      moveY = (int) Math.round(moveXTemp * slope);
      moveX = (int) Math.round(moveXTemp);   
      
      moveY = Math.abs(moveY)*dSign;
      moveX = Math.abs(moveX)*rSign;
    }
  }
  
  
  /**
   * getInfo
   *Gets the info of the bullet
   *@param: 
   *@return: A double[]
   */
  public double[] getInfo() {
    double[] returnArray = {currentX,currentY,trailX,trailY,angle};
    this.calculate();
    return returnArray;
  }
  
  /**
   *setCollision
   *Sets the collision
   *@param: boolean state
   *@return: 
   */
  public void setCollision(boolean state) {
    this.collision = state;
  }
  
  /**
   *returnAngle
   *Returns the angle
   *@param: 
   *@return: A double
   */
  public double returnAngle() {
    return this.angle;
  }
  
  
}
