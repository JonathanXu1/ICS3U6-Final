import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class GameSaver {
  private int fileCode;
  
  GameSaver() {
    Scanner calibrator = new Scanner(("SaveCalibrator.txt"));
    this.fileCode = calibrator.nextInt();
  }
  
  public void saveGame(int[][] map, int[][] entityMap, int[][] inventory, int[] playerStats) throws Exception{
    
    File saveFile = new File("concordia_savfile_" + fileCode + ".txt");
    
    PrintWriter writer = new PrintWriter(new File("concordia_savfile_" + fileCode + ".txt"));
    PrintWriter calibrationUpdater = new PrintWriter( new File("SaveCalibrator"));
    calibrationUpdater.println(fileCode + 1);
    
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        writer.print('[');
        writer.print(map[i][j]);        
        writer.print(']');
        writer.print(',');
      }
      writer.println("");
    }
  }
  
  
}