import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.awt.Color;

public class GameSaver {
  private int fileCode;  
  //private String tester;
  
  GameSaver() throws Exception{
    Scanner calibrator = new Scanner(new File("SaveCalibrator.txt")); // Sets the fileCode to make sure it doesn't overwrite other saves
    //this.tester = calibrator.nextLine();
    this.fileCode = calibrator.nextInt();
    //System.out.println(tester);
  }
  
  public void saveGame(char[][] charMap, Entity[][] entityMap, Item[][] itemMap, Item[][] inventory) throws Exception{
    
    File saveFile = new File("concordia_savfile_" + fileCode + ".txt");
    
    PrintWriter writer = new PrintWriter(new File("concordia_savfile_" + fileCode + ".txt"));
    PrintWriter calibrationUpdater = new PrintWriter(new File("SaveCalibrator"));
    calibrationUpdater.println(fileCode + 1);        
    
    writer.println("%map save:");
    writer.println(charMap.length + " " + charMap[0].length);
    for (int i = 0; i < charMap.length; i++) {
      for (int j = 0; j < charMap[0].length; j++) {
        writer.print(charMap[i][j]);
        //writer.print(",");     
      }
      writer.println("");
    }
    
    int type;
    int recHealth;
    boolean[] states = new boolean[3];
    int xCoord;
    int yCoord;
    
    int playerHunger = 0;
    int playerXP = 0;
    
    writer.println("%entity save:");    
    writer.println(entityMap.length + " " + entityMap[0].length);
    
    System.out.println("entity start");
    
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        if (entityMap[i][j] instanceof Entity) {
          type = -1;
          if (entityMap[i][j] instanceof Brute) {
            type = 1;            
          } else if (entityMap[i][j] instanceof Character) {
            type = 0;
            playerHunger = ((Character)entityMap[i][j]).getHunger();
            playerXP = ((Character)entityMap[i][j]).getXp();
          } else if (entityMap[i][j] instanceof FlameRoomba) {
            type = 2;
          } else if (entityMap[i][j] instanceof LightningRoomba) {
            type = 3;
          } else if (entityMap[i][j] instanceof FreezeRoomba) {
            type = 4;
          }
            
          recHealth = entityMap[i][j].getHealth();
          states[0] = entityMap[i][j].getFlame();
          states[1] = entityMap[i][j].getFreeze();
          states[2] = entityMap[i][j].getLightning();
          yCoord = i;
          xCoord = j;                                    
          
          writer.print(type + " ");
          writer.print(recHealth + " ");
          writer.print(states[0] + " " + states[1] + " " + states[2] + " ");
          writer.print(xCoord + " " + yCoord);   
          writer.println("");
        }
      } 
    }
    
    writer.println("%player values");
    writer.println(playerHunger + " " + playerXP);
    
    writer.println("%item mapping save:");
    writer.println(itemMap.length + " " + itemMap[0].length);
    
    String itemType;
    int durability;
    int damage = 0;
    
    System.out.println("item start");
    
    for (int i = 0; i < entityMap.length; i++) {
      for (int j = 0; j < entityMap[0].length; j++) {
        if (itemMap[i][j] instanceof Item) {
          
          itemType = itemMap[i][j].getName();
          writer.print(itemType + "] ");
          
          if (itemMap[i][j] instanceof Equipment) {                                  
            durability = ((Equipment)itemMap[i][j]).getDurability();
            
            if (itemMap[i][j] instanceof Weapon) { 
              damage = (((Weapon)itemMap[i][j]).getDamage());   
              writer.print(1);
            } else {
              writer.print(0);
            }
            
            writer.print(" " + i + " " + j  + " ");          
            writer.print(durability + " ");          
            writer.print(((Equipment)itemMap[i][j]).getDurabilityCap());
            
            if (itemMap[i][j] instanceof Weapon) { 
              writer.print(" " + damage);
            }                              
            
            writer.println("");
          } else {
            writer.println(-1);
          }
        } 
      }
    }        
    
    System.out.println("Inventory start");
    
    writer.println("%inventory save:");
    for (int i = 0; i < inventory.length; i++) {
      for (int j = 0; j < inventory[0].length; j++) {
       
        if (itemMap[i][j] instanceof Item) {
          
          itemType = itemMap[i][j].getName();
          writer.print(itemType + " ");
          
          if (itemMap[i][j] instanceof Equipment) {                                  
            durability = ((Equipment)itemMap[i][j]).getDurability();
            
            if (itemMap[i][j] instanceof Weapon) { 
              damage = (((Weapon)itemMap[i][j]).getDamage());   
              writer.print(1);
            } else {
              writer.print(0);
            }
            
            writer.print(" " + i + " " + j  + " ");          
            writer.print(durability + " ");          
            writer.print(((Equipment)itemMap[i][j]).getDurabilityCap());
            
            if (itemMap[i][j] instanceof Weapon) { 
              writer.print(" " + damage);
            }                              
            
            writer.println("");
          } else {
            writer.println(-1);
          }
        } 
        
      }
    }  
    
    System.out.println("save close");
    writer.close();
  }
  
  
  public LoadFile loadGame(String fileName) throws Exception {
    File loadSource = new File(fileName + ".txt");
    Scanner reader = new Scanner(loadSource);
    String lineReader;
    
    char[][] loadedCharMap;
    Entity[][] loadedEntityMap;
    Item[][] loadedItemMap;
    Item[][] loadedInventory;
    int[] loadedExtras = new int[2];
    
    int sizeX, sizeY;
    
    lineReader = reader.nextLine();
    //System.out.println(lineReader);
    sizeY = reader.nextInt();
    sizeX = reader.nextInt();
    
    //System.out.println(sizeY + " " + sizeX);
    
    lineReader = reader.nextLine();    
    lineReader = reader.nextLine();  
    
    loadedCharMap = new char[sizeY][sizeX];    
    
    for (int i = 0; i < sizeY; i++) {
      for (int j = 0; j < lineReader.length(); j++) {
        loadedCharMap[i][j] = lineReader.charAt(j);
      }
      lineReader = reader.nextLine(); 
    }
    
    
    //lineReader = reader.nextLine();
    //lineReader = reader.nextLine(); 
    //System.out.println(lineReader);
    sizeY = reader.nextInt();
    sizeX = reader.nextInt();
    lineReader = reader.nextLine();    
    //lineReader = reader.nextLine();  
    
    loadedEntityMap = new Entity[sizeY][sizeX];
    int playerX, playerY;
    
    int type;
    int recHealth;
    int xCoord = 0;
    int yCoord = 0;
    
    boolean burn;
    
    //System.out.println(lineReader);
    //System.out.println(reader.next());
    while((reader.next().charAt(0)) != '%') {
      type = reader.nextInt();
      recHealth = reader.nextInt();
      burn = reader.nextBoolean();
      burn = reader.nextBoolean();
      burn = reader.nextBoolean();
      xCoord = reader.nextInt();
      yCoord = reader.nextInt();
      
      if (type == 4) {
        FreezeRoomba loadedEntity= new FreezeRoomba(recHealth,100,100,1,false,false,false,Color.PINK,false);
        loadedEntityMap[yCoord][xCoord] = loadedEntity;
      } else if (type == 3) {
        LightningRoomba loadedEntity= new LightningRoomba(recHealth,100,100,1,false,false,false,Color.PINK,false);
        loadedEntityMap[yCoord][xCoord] = loadedEntity;
      } else if (type == 2) {
        FlameRoomba loadedEntity= new FlameRoomba(recHealth,100,100,1,false,false,false,Color.PINK,false);
        loadedEntityMap[yCoord][xCoord] = loadedEntity;
      } else if (type == 1) {
        Brute loadedEntity = new Brute(recHealth,100,100,1,false,false,false,Color.PINK,false);
        loadedEntityMap[yCoord][xCoord] = loadedEntity;
      } else if (type == 0){
        playerX = xCoord;
        playerY = yCoord;
        
        Character loadedCharacter = new Character(recHealth,100,100,1,false,false,false,Color.BLUE);
        loadedEntityMap[yCoord][xCoord] = loadedCharacter;
      }
      
      lineReader = reader.nextLine();
    }
    
    //lineReader = reader.nextLine();
    
    System.out.println(reader.next());
    loadedExtras[0] = reader.nextInt();
    loadedExtras[1] = reader.nextInt();
    System.out.println(loadedExtras[0] + " " + loadedExtras[1]);
    
    
    lineReader = reader.nextLine(); 
    lineReader = reader.nextLine();
    
    sizeY = reader.nextInt();
    sizeX = reader.nextInt();
    lineReader = reader.nextLine();    
    lineReader = reader.nextLine();
    
    loadedItemMap = new Item[sizeY][sizeX];
    String loadedItemType; 
    int loadedType;
    int loadedDurability = 0;
    int loadedDurCap = 0;
    int loadedDamage = 0;    
    
    while ((reader.next().charAt(0)) != '%') {
      int nameCapture = 0;      
      
      System.out.println("yay");
      System.out.println(" ir " + lineReader);            
      
      loadedType = reader.nextInt();             
      
      
      xCoord = reader.nextInt();
      yCoord = reader.nextInt();
      
      if (loadedType != -1) {        
        loadedDurability = reader.nextInt();
        loadedDurCap = reader.nextInt();        
      }
      
      if (loadedType == 1)  {  
        //System.out.println(reader.next());
        loadedDamage = reader.nextInt();   
        
        lineReader = reader.nextLine();
        nameCapture = lineReader.length();
        do {
          nameCapture--;
        } while(lineReader.charAt(nameCapture) != '[');      
        loadedItemType = lineReader.substring(nameCapture + 1,lineReader.length());
        System.out.println(loadedItemType);
        
        if (loadedItemType.equals("Gamma Hammer")) {
          GammaHammer loadedItem = new GammaHammer(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Wrench")) {
          Wrench loadedItem = new Wrench(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Energy Sword")) {
          EnergySword loadedItem = new EnergySword(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Plasma Rapier")) {
          PlasmaRapier loadedItem = new PlasmaRapier(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Kinetic Mace")) {
          KineticMace loadedItem = new KineticMace(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } 
        
        else if (loadedItemType.equals("Laser Pistol")) {
          LaserPistol loadedItem = new LaserPistol(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Pulse Railgun")) {
          PulseRailgun loadedItem = new PulseRailgun(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Solar Scorcher")) {
          SolarScorcher loadedItem = new SolarScorcher(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }                        
        
        System.out.println(loadedItemMap.length + " " + loadedItemMap[0].length);
        ((Weapon)loadedItemMap[yCoord][xCoord]).setDamage(loadedDamage);
        ((Weapon)loadedItemMap[yCoord][xCoord]).setDurabilityCap(loadedDurCap);
        
      } else if (loadedType == 0) { 
        
        
        lineReader = reader.nextLine();
        nameCapture = lineReader.length();
        do {
          nameCapture--;
        } while(lineReader.charAt(nameCapture) != '[');      
        loadedItemType = lineReader.substring(nameCapture + 1,lineReader.length());
        
        
        if (loadedItemType.equals("Space Suit")) {
          SpaceSuit loadedItem = new SpaceSuit(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Assault Vest")) {
          AssaultVest loadedItem = new AssaultVest(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Energy Suit")) {
          EnergySuit loadedItem = new EnergySuit(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Iridium Exoskeleton")) {
          IridiumExoskeleton loadedItem = new IridiumExoskeleton(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Proximity Armor")) {
          ProximityArmor loadedItem = new ProximityArmor(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }                
      } else if (loadedType == -1){
        
        lineReader = reader.nextLine();
        nameCapture = lineReader.length();
        do {
          nameCapture--;
        } while(lineReader.charAt(nameCapture) != '[');      
        loadedItemType = lineReader.substring(nameCapture + 1,lineReader.length());
                
        if (loadedItemType.equals("Med Kit")) {
          MedKit loadedItem = new MedKit();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Flame Drive")) {
          FlameDrive loadedItem = new FlameDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Steel Drive")) {
          SteelDrive loadedItem = new SteelDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Lightning Drive")) {
          LightningDrive loadedItem = new LightningDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Freeze Drive")) {
          FreezeDrive loadedItem = new FreezeDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Power Drive")) {
          PowerDrive loadedItem = new PowerDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Flame Drive")) {
          Food loadedItem = new Food();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }
      }
      
      //lineReader = reader.nextLine();
    }
    
    lineReader = reader.nextLine();    
    lineReader = reader.nextLine();    
    
    
    loadedInventory = new Item[4][6];
    loadedItemType = "";
    loadedDurability = 0;
    
    do {
      int nameCapture = 0;
      
      do {
        nameCapture++;
      } while(lineReader.charAt(nameCapture) != ']');
      
      loadedItemType = lineReader.substring(0,nameCapture);
      loadedType = reader.nextInt();             
      
      System.out.println(loadedItemType);
      xCoord = reader.nextInt();
      yCoord = reader.nextInt();
      
      if (loadedType != -1) {        
        loadedDurability = reader.nextInt();
        loadedDurCap = reader.nextInt();        
      }
      
      if (loadedType == 1)  {    
        loadedDamage = reader.nextInt();   
        
        if (loadedItemType.equals("Gamma Hammer")) {
          GammaHammer loadedItem = new GammaHammer(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Wrench")) {
          Wrench loadedItem = new Wrench(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Energy Sword")) {
          EnergySword loadedItem = new EnergySword(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Plasma Rapier")) {
          PlasmaRapier loadedItem = new PlasmaRapier(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Kinetic Mace")) {
          KineticMace loadedItem = new KineticMace(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } 
        
        else if (loadedItemType.equals("Laser Pistol")) {
          LaserPistol loadedItem = new LaserPistol(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Pulse Railgun")) {
          PulseRailgun loadedItem = new PulseRailgun(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Solar Scorcher")) {
          SolarScorcher loadedItem = new SolarScorcher(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }                        
        
        ((Weapon)loadedItemMap[yCoord][xCoord]).setDamage(loadedDamage);
        ((Weapon)loadedItemMap[yCoord][xCoord]).setDurabilityCap(loadedDurCap);
        
      } else if (loadedType == 0) {        
        
        if (loadedItemType.equals("Space Suit")) {
          SpaceSuit loadedItem = new SpaceSuit(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Assault Vest")) {
          AssaultVest loadedItem = new AssaultVest(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Energy Suit")) {
          EnergySuit loadedItem = new EnergySuit(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Iridium Exoskeleton")) {
          IridiumExoskeleton loadedItem = new IridiumExoskeleton(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Proximity Armor")) {
          ProximityArmor loadedItem = new ProximityArmor(loadedDurability);
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }
        
      } else if (loadedType == -1){
        if (loadedItemType.equals("Med Kit")) {
          MedKit loadedItem = new MedKit();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Flame Drive")) {
          FlameDrive loadedItem = new FlameDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Steel Drive")) {
          SteelDrive loadedItem = new SteelDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Lightning Drive")) {
          LightningDrive loadedItem = new LightningDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Freeze Drive")) {
          FreezeDrive loadedItem = new FreezeDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Power Drive")) {
          PowerDrive loadedItem = new PowerDrive();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        } else if (loadedItemType.equals("Flame Drive")) {
          Food loadedItem = new Food();
          loadedItemMap[yCoord][xCoord] = loadedItem;
        }
      }
      
      lineReader = reader.nextLine();
    } while (reader.hasNext());
    
    LoadFile loadFile = new LoadFile(loadedCharMap,loadedEntityMap,loadedItemMap,loadedInventory,loadedExtras);
    
    return loadFile;    
  }
}