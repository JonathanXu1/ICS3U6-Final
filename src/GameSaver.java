import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class GameSaver {
  private int fileCode;
  
  GameSaver() {
    Scanner calibrator = new Scanner(("SaveCalibrator.txt"));
    this.fileCode = calibrator.nextInt();
  }
  
  public void saveGame(char[][] charMap, Entity[][] entityMap, Item[][] itemMap, Item[][] inventory) throws Exception{
    
    File saveFile = new File("concordia_savfile_" + fileCode + ".txt");
    
    PrintWriter writer = new PrintWriter(new File("concordia_savfile_" + fileCode + ".txt"));
    PrintWriter calibrationUpdater = new PrintWriter( new File("SaveCalibrator"));
    calibrationUpdater.println(fileCode + 1);
    
    writer.println("map save: \n");
    for (int i = 0; i < charMap.length; i++) {
      for (int j = 0; j < charMap[0].length; j++) {
        writer.print(charMap[i][j]);
        writer.print(",");     
      }
      writer.println("");
    }
    
    int type;
    int recHealth;
    boolean[] states = new boolean[3];
    int xCoord;
    int yCoord;
    
    writer.println("entity save: \n");
    
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        if (entityMap[i][j] instanceof Entity) {
          type = -1;
          if (entityMap[i][j] instanceof Enemy) {
            type = 1;            
          } else if (entityMap[i][j] instanceof Character) {
            type = 0;
          } 
          recHealth = entityMap[i][j].getHealth();
          states[0] = entityMap[i][j].getFlame();
          states[1] = entityMap[i][j].getFreeze();
          states[2] = entityMap[i][j].getLightning();
          xCoord = i;
          yCoord = j;                                    
          
          writer.print(type + ",");
          writer.print(recHealth + ",");
          writer.print(states[0] + "," + states[1] + "," + states[2]);
          writer.print(xCoord + "," + yCoord);   
          writer.println("");
        }
      } 
    }
    
    
    writer.println("item mapping save:");
    
    String itemType;
    int durability;
    
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        if (itemMap[i][j] instanceof Item) {
          itemType = itemMap[i][j].getName();
          
          if (itemMap[i][j] instanceof Equipment) { 
            durability = ((Equipment)itemMap[i][j]).getDurability();
          } else {
            durability = -1;
          }
          
          writer.print(itemType + ",");
          writer.print(durability + ",");
          writer.println("");
        }
      }
    }        
    
    
    writer.println("inventory save:");
    for (int i = 0; i < inventory.length; i++) {
      for (int j = 0; j < inventory[0].length; j++) {
        if (itemMap[i][j] instanceof Item) {
          itemType = itemMap[i][j].getName();
          
          if (itemMap[i][j] instanceof Equipment) { 
            durability = ((Equipment)itemMap[i][j]).getDurability();
          } else {
            durability = -1;
          }
          
          writer.print(itemType + ",");
          writer.print(durability + ",");
          writer.println("");
        }
      }
    }        
  }   
}