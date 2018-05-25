class DisplayTest{
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    double beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    double afterUsedMem, actualMemUsed;
    
    Display disp = new Display ();
    
    long oldTime = System.nanoTime();
    long secondTime = System.nanoTime();
    long currentTime = System.nanoTime();
    int frame=0;
    final long DELTA_LIMIT = 10000000;
    final long SECOND_LIMIT = 1000000000;
    while (true){
      currentTime= System.nanoTime();
      //Finds memory usage after code execution
      afterUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
      actualMemUsed = afterUsedMem - beforeUsedMem;
      disp.setMem(Runtime.getRuntime().totalMemory(), actualMemUsed);
      
      if ((currentTime-oldTime)>=DELTA_LIMIT){
        disp.getListen();
        disp.refreshAll();
        oldTime = currentTime;
        frame++;
      }
      if ((currentTime-secondTime)>=SECOND_LIMIT){
        secondTime = currentTime;
        //System.out.println (frame);
        disp.setfps(frame);
        frame =0;
      }
    }
  }
}