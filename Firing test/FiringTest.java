class FiringTest{
  public static void main(String[] args) {
    int[][] seed = new int[10][10];
    
    DisplayGridC disp = new DisplayGridC(seed);
    disp.refresh();
    disp.seeRatio();       
    
    int counter = 0;
    int start = 0;
    int end = 70;
    
    try{ Thread.sleep(5000); }catch(Exception e) {};
    
    do{
      start++;
      end++;
      
      disp.setLine(start,start,end,end);
      disp.refresh();
      
      try{ Thread.sleep(20); }catch(Exception e) {};
      
    } while (end < disp.returnLim());
  }
  
  
  
}