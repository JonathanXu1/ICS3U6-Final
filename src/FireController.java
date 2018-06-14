class FireController {
  private int currentY, currentX; // current (leading end of a bullet)
  private int counter; // for controlling collision
  private int trailY, trailX; //trailing end of butllet
  private int moveY, moveX; // how much the bullet is moved by
  private int startY, startX; // starting coordinates
  private double angle; // angle of travel
  private boolean collision = false; // collision detection
  private int shotLength; // length of shot
  
  FireController(int mX, int mY, int sX, int sY) { // sets the startX and startY of the object    
    this.startY = sY;
    this.startX = sX;
  }  
  
  public void setupProjectile(int targetX, int targetY, int shotLen) {    
    counter = 0; // counter is set to 0
    
    this.shotLength = shotLen; // shot length is set
    
    double deltaY = targetY - startY; // change in coordiantes is calculated
    double deltaX = targetX - startX;
    
    int dSign = 1; // pos/neg nature of deltas determined
    int rSign = 1;
    
    if (deltaY < 0) {
      dSign = -1;
    }        
    if (deltaX < 0) {
      rSign = -1;      
    }
    
    double slope = (deltaY/deltaX); // slope is calculated
    
    
    angle = Math.atan((Math.abs(deltaY)/Math.abs(deltaX))); // angle is calculated using trigonometry
    
       
    if (deltaX < 0 && deltaY <= 0) { //Quadrant 2 // angle is modified based on deltas
      angle = Math.PI - angle;
    } else if (deltaX <= 0 && deltaY > 0) { //Quadrant 3
      angle = Math.PI + angle;
    } else if (deltaX >= 0 && deltaY > 0) { //Quadrant 4
      angle = 2*Math.PI - angle;
    }

    
    int moveY, moveX;
    
    
    // inverse pythagorean theorem is ran to get moves that produce the same shot length in all directions
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
  
  public void calculate() {    // calculations are ran
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
  
  
  // returns for other parts ofthe code to use
  public double[] getInfo() {
    double[] returnArray = {currentX,currentY,trailX,trailY,angle};
    this.calculate();
    return returnArray;
  }
  
  // sets the collision
  public void setCollision(boolean state) {
    this.collision = state;
  }
  
  // returns the angle that was calculated
  public double returnAngle() {
    return this.angle;
  }
  
  
}