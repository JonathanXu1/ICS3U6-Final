import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;
import javax.sound.sampled.*;

class Main{
  //These variables are required to be able to set starting position of the character
  public static void main (String[] args) throws Exception{
    //Finds memory usage before program starts
    int mb = 1024*1024;
    Runtime runtime = Runtime.getRuntime();
    double maxMem = runtime.maxMemory();
    double usedMem;
    //Music vars
 //   File mappo = new File ("map.txt");
  //  PrintWriter output = new PrintWriter (mappo);
    File audioFile = new File("../res/spacebackround.wav");
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
    Clip clip = (Clip) AudioSystem.getLine(info);
    //Creates the map generator object
    MapGen2_5 gen = new MapGen2_5();
    //A tile map will be created based off the tile map
    char[][] charMap = gen.charMap(gen.generateMap(12,12));
    //Converts the map into a tile map
    int playerStartingX=0, playerStartingY =0;
    Tile [][] map = new Tile [charMap.length][charMap[0].length];
      for (int i = 0; i < charMap.length; i++){
//        output.println ("");
        for(int j = 0; j < charMap[0].length; j++){
 //         output.print (charMap[i][j]);
          if (charMap[i][j] == 'X'){
            map[i][j]= new HallwayTile(Color.WHITE);
          } else if (charMap[i][j] == 'R') {
            map[i][j]= new FloorTile(Color.GREEN);
          } else if (charMap[i][j] == 'D') {
            map[i][j]= new DoorTile(Color.RED);
          } else if (charMap[i][j] == '~') {
            map[i][j]= new WallTile(Color.DARK_GRAY);
          } else if (charMap[i][j] == '|') {
            map[i][j]= new WallTile(Color.LIGHT_GRAY);
          } else if (charMap[i][j] == 'A') {
            map[i][j]= new FloorTile(Color.ORANGE);
          } else if (charMap[i][j] == '@'){
            map[i][j]= new FloorTile(Color.GREEN);
            playerStartingX = j;
            playerStartingY = i;
          }
      }
      }
   //   output.close();
      //Plays music
    try {
      clip.open(audioStream);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }catch (Exception e) {
      e.printStackTrace();
    }
    //The display frame is created, and the player x and y are found
    Display disp = new Display ();
    //The Clock time keeps track of the fps
    Clock time = new Clock ();
    disp.setMap(map);
    disp.setPlayerLocation (playerStartingX, playerStartingY);
    while (true){
      time.setTime();  
      if (time.getFramePassed()){
        //Finds memory usage after code execution    
        usedMem = runtime.totalMemory() - runtime.freeMemory();
        disp.setMem(maxMem/mb, usedMem/mb);
        disp.getListen();
        disp.refreshAll();
      }
      if (time.getSecondPassed()){
        disp.setFps(time.getFrame());
        time.setFrame(0);
      }
      /*
      if(clip.getMicrosecondLength() == clip.getMicrosecondPosition()){
        clip.close();
      }
      */
    }
  }
}