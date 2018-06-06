class FireController {
  private int currentD, currentR;
  private int counter;
  private int endCounter;
  private int ratio;
  private int trailD, trailR;
  private int moveD, moveR;
  private int maxX, maxY;
  private int targetD, targetR;
  private int startD, startR;
  private double angle;
  private int projectileLength;
  
  FireController(int mX, int mY) {
    this.maxX = mX;
    this.maxY = mY;
    
    startD = maxY/2;
    startR = maxX/2;
  }  
  
  public void setupProjectile(int targetD, int targetR) {    
    counter = 0;
    endCounter = 0;  
    
    double deltaD = targetD - startD;
    double deltaR = targetR - startR;
    
    int dSign = 1;
    int rSign = 1;
    
    if (deltaD < 0) {
      dSign = -1;
    }        
    if (deltaR < 0) {
      rSign = -1;      
    }
    
    double slope = (deltaD/deltaR);
    
    double distance = Math.sqrt(Math.pow(deltaD,2) + Math.pow(deltaR,2));
    
    
    angle = Math.atan((Math.abs(deltaD)/Math.abs(deltaR)));
    
       
    if (deltaR < 0) {
      angle = angle + (Math.PI)/2;
    } 
    
    if (deltaD > 0) {
      angle = angle*(-1);
    }                

    
    int moveD, moveR;
    
    if (deltaR == 0) {
      moveD = 32;
      moveR = 0;
    } else {
      
      double moveRTemp = Math.sqrt(1024/(Math.pow(slope,2) + 1));
      moveD = (int) Math.round(moveRTemp * slope);
      moveR = (int) Math.round(moveRTemp);   
      
      moveD = Math.abs(moveD)*dSign;
      moveR = Math.abs(moveR)*rSign;
    }
  }
  
  public void calculate() {    
    if (counter <= 6 ) {
      trailR = currentR - (counter)*moveR;
      trailD = currentD - (counter)*moveD;
    } else {
      trailD = currentR - moveD*6;
      trailR = currentR - moveR*6;
    }         
    counter++;    
    
    if (endCounter == 0) {
      currentD = currentD + moveD;
      currentR = currentR + moveR;
      
    }
  } 
  
  public double[] cycle() {
    double[] returnArray = {currentD,currentR,trailD,trailR, angle};
    this.calculate();    
    return returnArray;
  }
  
  public double returnAngle() {
    return this.angle;
  }
  
  
}