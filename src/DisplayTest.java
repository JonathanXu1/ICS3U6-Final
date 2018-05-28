import java.io.*;
import java.util.Scanner;
import java.awt.Color;

class DisplayTest{
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    int mb = 1024*1024;
    Runtime runtime = Runtime.getRuntime();
    double maxMem = runtime.maxMemory();
    double usedMem;
    //Very messy code, may have to reformat later
    File fileMap = new File("../res/testMap.txt");
    Scanner input = new Scanner (fileMap);
    int arrayRow =0;
    int arrayColumn = 0;
    while (input.hasNext()){
      arrayColumn=(input.nextLine()).length();
      arrayRow++;
    }
    Tile [][] map = new Tile [arrayRow][arrayColumn];
    String tempLine;
    input = new Scanner (fileMap);
    for (int i = 0;i<arrayRow;i++){
      tempLine = input.nextLine();
      for(int j = 0;j<arrayColumn;j++){
        if (tempLine.charAt (j)=='X'){
          map[i][j]= new FloorTile(Color.WHITE);
        }else{
          map[i][j]= new WallTile(Color.BLACK);
        }
      }
    }
    input.close();
    
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