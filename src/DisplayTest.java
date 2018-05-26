class DisplayTest{
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    int mb = 1024*1024;
    Runtime runtime = Runtime.getRuntime();
    double maxMem = runtime.maxMemory();
    double usedMem;
    
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
      usedMem = runtime.totalMemory() - runtime.freeMemory();
      disp.setMem(maxMem/mb, usedMem/mb);
      
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