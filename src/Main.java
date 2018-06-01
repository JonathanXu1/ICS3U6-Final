import java.awt.Color;
import java.io.File;
import javax.sound.sampled.*;

class Main{
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    int mb = 1024*1024;
    Runtime runtime = Runtime.getRuntime();
    double maxMem = runtime.maxMemory();
    double usedMem;
    //Music vars
    File audioFile = new File("../res/interstellar.wav");
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
    Clip clip = (Clip) AudioSystem.getLine(info);
    
    //Very messy code, may have to reformat later    
    MapGen2_4 gen = new MapGen2_4();
    
    char[][] charMap = gen.charMap(gen.generateMap(12,12));
    Tile [][] map = new Tile [charMap.length][charMap[0].length];
    int characterX = 0;
    int characterY =0;
    
    for (int i = 0; i < charMap.length; i++){
      for(int j = 0; j < charMap[0].length; j++){
        if (charMap[i][j] == 'X'){
          map[i][j]= new FloorTile(Color.WHITE);
        } else if (charMap[i][j] == 'R') {
          map[i][j]= new FloorTile(Color.GREEN);
        } else if (charMap[i][j] == 'D') {
          map[i][j]= new FloorTile(Color.RED);
        } else if (charMap[i][j] == '~') {
          map[i][j]= new WallTile(Color.DARK_GRAY);
        } else if (charMap[i][j] == '|') {
          map[i][j]= new WallTile(Color.LIGHT_GRAY);
        } else if (charMap[i][j] == 'A') {
          map[i][j]= new FloorTile(Color.ORANGE);
        } else if (charMap[i][j] == '@'){
          map[i][j]= new FloorTile(Color.GREEN);
          characterX = j;
          characterY = i;
        }else{
          map[i][j]= new VoidTile(Color.BLACK);
        }
      }
    }
    //Plays music
    try {      
      clip.open(audioStream);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      }catch (Exception e) {
      e.printStackTrace();
    }
    
    Display disp = new Display ();
    disp.setMap(map);
    disp.setSetPlayerLocation (characterX, characterY);
    long oldTime = System.nanoTime();
    long secondTime = System.nanoTime();
    long currentTime = System.nanoTime();
    int frame=0;
    final long DELTA_LIMIT = 10000000;
    final long SECOND_LIMIT = 1000000000;
    while (true){
      currentTime= System.nanoTime();
      //Finds memory usage after code execution      
      if ((currentTime-oldTime)>=DELTA_LIMIT){
        usedMem = runtime.totalMemory() - runtime.freeMemory();
        disp.setMem(maxMem/mb, usedMem/mb);
        disp.getListen();
        disp.refreshAll();
        oldTime = currentTime;
        frame++;
      }
      if ((currentTime-secondTime)>=SECOND_LIMIT){
        secondTime = currentTime;
        //System.out.println (frame);
        disp.setFps(frame);
        frame = 0;
      }
      
      if(clip.getMicrosecondLength() == clip.getMicrosecondPosition()){
        //clip.close();
        //clip.setMicrosecondPosition(1);
        //System.out.println("aud reset");
      }
    }
  }
}