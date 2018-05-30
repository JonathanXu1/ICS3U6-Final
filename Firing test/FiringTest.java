class FiringTest{
  public static void main(String[] args) {
    int[][] seed = new int[11][11];
    
    DisplayGridC disp = new DisplayGridC(seed);
    
    int ratio = disp.getRatio();     
    
    int counter = 0;
    
    int currentTileD = 6;
    int currentTileR = 6;
    
    int targetTileD = 10;
    int targetTileR = 11;
    
    
    double targetD = (targetTileD - 1)*ratio +  (int) (ratio/2) ;
    double targetR = (targetTileR - 1)*ratio +  (int) (ratio/2) ;
    
    disp.mark((int)targetR,(int)targetD);
    
    int currentD = (currentTileD - 1)*ratio +  (int) (ratio/2) ;
    int currentR = (currentTileR - 1)*ratio +  (int) (ratio/2) ;
    
    disp.setPos((int) currentR, (int) currentD);
    double deltaD = targetD - currentD;
    double deltaR = targetR - currentR;
    
    int dSign = 1;
    int rSign = 1;
    
    if (deltaD < 0) {
      dSign = -1;
    }        
    if (deltaR < 0) {
      rSign = -1;      
    }
    
    double slope = (deltaD/deltaR);
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
    
    
    System.out.println(deltaD);
    System.out.println(deltaR);
    System.out.println(slope);
    System.out.println(moveD);
    System.out.println(moveR);
    
    try{ Thread.sleep(4000); }catch(Exception e) {};
    disp.refresh();
    
    int affectedTileD, affectedTileR;
    int endCounter = 1;
    
    do{
      disp.refresh();
      if (counter <= 6 ) {
        disp.setLine(currentR,currentD,currentR - (counter)*moveR,currentD - (counter)*moveD);
      } else if (currentD > ratio*11 || currentR > ratio*11) {
        //System.out.println("End");
        
        disp.setLine(currentR - moveR*endCounter,currentD - moveD*endCounter,currentR - 6*moveR,currentD - 6*moveD);
        endCounter++;
      }  else {
        disp.setLine(currentR,currentD,currentR - 6*moveR,currentD - 6*moveD);
      }         
      counter++;
        
      affectedTileD = (int) (currentD/ratio);
      affectedTileR = (int) (currentR/ratio);
      
      currentD = currentD + moveD;
      currentR = currentR + moveR;
      
      if (endCounter == 1) {
        disp.setAffected(affectedTileD,affectedTileR);
      }
      
      try{ Thread.sleep(200); }catch(Exception e) {};
    } while(endCounter < 8);
  }
  
  
  
}