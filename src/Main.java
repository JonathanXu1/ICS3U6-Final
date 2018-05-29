import java.awt.Color;

class Main{
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    int mb = 1024*1024;
    Runtime runtime = Runtime.getRuntime();
    double maxMem = runtime.maxMemory();
    double usedMem;
    
    //Very messy code, may have to reformat later    
    MapGen2_3 gen = new MapGen2_3();
    
    char[][] charMap = gen.charMap(gen.generateMap(24,6));
    Tile [][] map = new Tile [charMap.length][charMap[0].length];
    String tempLine;
    
    for (int i = 0; i < charMap.length; i++){
      for(int j = 0; j < charMap[0].length; j++){
        if (charMap[i][j] == 'X'){
          map[i][j]= new FloorTile(Color.WHITE);
        } else if (charMap[i][j] == 'R') {
          map[i][j]= new FloorTile(Color.GREEN);
        } else if (charMap[i][j] == 'D') {
          map[i][j]= new FloorTile(Color.BLUE);
        } else if (charMap[i][j] == '~') {
          map[i][j]= new WallTile(Color.DARK_GRAY);
        } else if (charMap[i][j] == '|') {
          map[i][j]= new WallTile(Color.LIGHT_GRAY);
        }else if (charMap[i][j] == '@'){
          map[i][j]= new FloorTile(Color.GREEN);
          Character.setArrayX (j);
          Character.setArrayY (i);
        }else{
          map[i][j]= new VoidTile(Color.BLACK);
        }
      }
    }

    
    Display disp = new Display ();
    disp.setMap(map);
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
        disp.setFps(frame);
        frame =0;
      }
    }
  }
}