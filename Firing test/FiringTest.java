class FiringTest{
  public static void main(String[] args) {
    int[][] seed = new int[11][11];
    
    DisplayGridC disp = new DisplayGridC(seed);
    
    int ratio = disp.getRatio();     
    
    int counter = 0;
    int start = 0;
    int end = 70;
    
    int currentTileD = 6;
    int currentTileR = 6;
    
    int targetTileD = 8;
    int targetTileR = 10;
    
    
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
    
    double moveRTemp = Math.sqrt(1024/(Math.pow(slope,2) + 1));
    int moveD = (int) Math.round(moveRTemp * slope);
    int moveR = (int) Math.round(moveRTemp);   
    
    moveD = Math.abs(moveD)*dSign;
    moveR = Math.abs(moveR)*rSign;
    
    
    
    System.out.println(deltaD);
    System.out.println(deltaR);
    System.out.println(slope);
    System.out.println(moveD);
    System.out.println(moveR);
    
    try{ Thread.sleep(4000); }catch(Exception e) {};
    disp.refresh();
    do{
      disp.setLine(currentR,currentD,currentR + 5*moveR,currentD + 5*moveD);
      disp.refresh();
      currentD = currentD + moveD;
      currentR = currentR + moveR;
      
      
      
      try{ Thread.sleep(200); }catch(Exception e) {};
    } while (end < disp.returnLim());
  }
  
  
  
}