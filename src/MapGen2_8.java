/*
 * [MapGen2_8.java]
 * 
 * Generates a mazelike map, with rooms that are radomly sized, equally dispersed. Also, unecessary dead ends are removed,
 * doors are generated, along with stairs going up and down. 
 * 
 * Version cleaned up and commented, now with improved OOP, fixed rooms, and designated rooms with corners.
 * 
 * 2.6 also spawn chests on appropriate tiles. 
 * 2.7 has more custom rooms to spawn, and is fully OOP
 * 2.8 is revamped for more multi custom room generation, and cleans up the code
 * 
 * NOTE: Dynamic room scaling does not work, and needs to be adjusted within the generateMao method to work for different map sizes
 * expirementation has yielded no better method of getting at the optimal room size than doing it by hand. 
 * 
 * Author: Artem Sotnikov
 * Date: May 29th, 2018
 * 
 */ 


class MapGen2_8{
  // Generates a random true/false depending on the percentage entered as argument, out of 1000
  private int[][] map;
  private boolean capQPlaced;
  
  MapGen2_8() {
    boolean capQPlaced = false; 
  }
  
  public static boolean randomRoll(int chance) {
    if (Math.random()*1000 < chance) {
      return true;
    } else {
      return false;
    }
  }
  
  // Gets a random number from the lower bound to the upper bound
  public static int getRand(int lowerBound, int upperBound) {    
    return ((int) (Math.random()*(upperBound - lowerBound))) + lowerBound; 
  }
  
  
  // Generates a template for the map, with borders capped as paths to contain the algorithms 
  public void genTemplate(int sizeW, int sizeD) {
    map = new int[sizeD + 10][sizeW + 10];
    
    for (int i = 5; i < sizeD + 5; i++) {
      for (int j = 5; j < sizeW + 5; j++) {
        map[i][j] = 1;
      }
    }
    
  }  
  
  // main recursive method for carving initial paths through the template
  public void carvePath(int dPos, int rPos, int dirState[], int probTurn) {
    map[dPos][rPos] = 0; // Sets the current spot to zero     
    int[] newDirState = new int[2];   // Initializes future movement array
    boolean pathDead = false;
    
    
    int numOpt = 0; // Determines the number of options for movement on the generation grid
    if (map[dPos + 6][rPos]  == 1) {
      numOpt++;
    }
    if (map[dPos - 6][rPos]  == 1) {
      numOpt++;
    }
    if (map[dPos][rPos + 6]  == 1) {
      numOpt++;
    }
    if (map[dPos][rPos - 6]  == 1) {
      numOpt++;
    }
    
    
    if (numOpt == 0) { // if no ways to move are present, a base case is hit.
      map[dPos][rPos] = -2; // The final tile is set as a dead end, for future consideration
      pathDead = true;
    } 
    
    else {
      
      if (map[dPos + dirState[0]][rPos + dirState[1]] == 1 && (randomRoll(1000 - probTurn) || numOpt == 1)) {  // Moves forward    
        newDirState = dirState; // Displacement remains same as last iteration
      } else {
        newDirState[0] = dirState[0];
        newDirState[1] = dirState[1];
        
        int[] memoryState = new int[2];
        
        do { // Displacement is randomly scramble until an avalible spot is hit. 
          memoryState[0] = newDirState[0];      
          memoryState[1] = newDirState[1];
          
          if (randomRoll(500)) {
            newDirState[0] = memoryState[1];
            newDirState[1] = memoryState[0];
          }
          
          if (randomRoll(500)) {
            newDirState[0] = newDirState[0]*(-1);
            newDirState[1] = newDirState[1]*(-1);
          }
        } while (map[dPos + newDirState[0]][rPos + newDirState[1]] < 1); 
      }
      
      // All the tiles between the current and future spot are carved out
      map[dPos + ((newDirState[0])/6)*5][rPos + ((newDirState[1])/6)*5] = 0;
      map[dPos + ((newDirState[0])/6)*4][rPos + ((newDirState[1])/6)*4] = 0;
      map[dPos + ((newDirState[0])/2)][rPos + ((newDirState[1])/2)] = 0;
      map[dPos + ((newDirState[0])/3)][rPos + ((newDirState[1])/3)] = 0;
      map[dPos + ((newDirState[0])/6)][rPos + ((newDirState[1])/6)] = 0;
      
      // The next recursive call is sent out. 
      carvePath(dPos + newDirState[0], rPos + newDirState[1], newDirState, probTurn);
    }
  }
  
  // Takes the map and randomly spawns rooms in it, based on the number of rooms specified as argument
  public void genRooms(int numRooms) {      
    int randR,randD; // initilizes integers for random spawn
    // Creates a matrix of rooms to prevent room clustering and keep track of where rooms are
    int[][] roomMatrix = new int[(map.length-3)/6][(map[0].length-3)/6];     
    
    for (int c = 0; c < numRooms; c++) { // for the amount of rooms specified, a for loop goes      
      // random integers are continously generated until they line up to a spot where there are no adjacent rooms,
      // using the roomMatrix
      do {
        randD = getRand(2,(map.length-3)/6);
        randR = getRand(2,(map[0].length-3)/6);
      } while (roomMatrix[randD - 1][randR - 1] == 1);     
      // Once appropriate coordinates have been picked, the coordinate and all adjacent spaces for generation are
      // blacked out in the room matrix
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          roomMatrix[randD - 2 + i][randR - 2 + j] = 1;
        }
      }                  
      
      int randDReal = randD*6;
      int randRReal = randR*6;
      
      if (!capQPlaced) {        
        for (int i = -2; i < 3; i++){
          for (int j = -3; j < 4; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        capQPlaced = true;     
        
        map[randDReal][randRReal] = 600;   
        
      } else if (randomRoll(150)) {
        for (int i = -3; i < 4; i++){
          for (int j = -3; j < 4; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        map[randDReal][randRReal] = 100;        
        
      } else if (randomRoll(100)) {        
        for (int i = -4; i < 5; i++){
          for (int j = -4; j < 5; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        map[randDReal][randRReal] = 200;
        
        
      } else if (randomRoll(0)) {        
        for (int i = -4; i < 5; i++){
          for (int j = -4; j < 5; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        map[randDReal][randRReal] = 300;
        
        
      } else if (randomRoll(0)) {        
        for (int i = -4; i < 5; i++){
          for (int j = -4; j < 5; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        map[randDReal][randRReal] = 400;
        
        
      } else if (randomRoll(0)) {        
        for (int i = -4; i < 5; i++){
          for (int j = -4; j < 5; j++) {
            map[randDReal + i][randRReal + j] = -1;
          }
        }
        
        map[randDReal][randRReal] = 500;
        
        
      } else {
        
        int randWMod1 = getRand(-1,3); // Random modifiers for room size are generated
        int randWMod2 = getRand(-1,3);
        int randDMod1 = getRand(-1,3);
        int randDMod2 = getRand(-1,3);
        
        // Room is carved based on the random size modifiers
        for (int i = randDMod1; i < 9 - randDMod2; i++){
          for (int j = randWMod1; j < 9 - randWMod2; j++) {
            map[randDReal - 4 + i][randRReal - 4 + j] = -1;
          }
        }
        
        // All surrounding spaces are converted into room spaces to ease tile and door placement
        for (int i = randDMod1 - 1; i < 9 - randDMod2 + 1; i++){ // can be optimized
          for (int j = randWMod1 - 1; j < 9 - randWMod2 + 1; j++) {          
            if (map[randDReal - 4 + i][randRReal - 4 + j] == 0) {
              map[randDReal - 4 + i][randRReal - 4 + j] = -1;
            } 
          }
        }
        
      }
    }    
  }
  
  // Recursive method employed to remove unecessary dead ends from the map, taking in xy coordinates and the map
  // currntly being worked on as travel.
  public void pathPruner(int dPos, int rPos) {
    int numOpt = 0; // initializes the number of travel options the recursion has
    int[] nextStep = {0,0}; // The displacment array for the next recursion, to be set later
    
    // The number of options is determined
    if (map[dPos + 1][rPos]  < 1) {
      numOpt++;
      nextStep[0] = 1;
      nextStep[1] = 0;
    }
    if (map[dPos - 1][rPos]  < 1) {
      numOpt++;
      nextStep[0] = -1;
      nextStep[1] = 0;
    }
    if (map[dPos][rPos + 1]  < 1) {
      numOpt++;
      nextStep[0] = 0;
      nextStep[1] = 1;
    }
    if (map[dPos][rPos - 1]  < 1) {
      numOpt++;
      nextStep[0] = 0;
      nextStep[1] = -1;
    }
    
    if (numOpt > 1) { // If there is more than one option, the dead end is removed alredy and a base case is hit
      /*
       for (int i = 0; i < map.length; i++) {
       for (int j = 0; j < map[0].length; j++) {
       if (map[i][j] == 3) {
       map[i][j] = 1;
       }
       }
       } 
       */
    } else { // Otherwise, the current square is blotted out and the recursion moves onto the next square
      // The next step is set above, and we know it was only set once
      map[dPos][rPos] = 1;
      this.pathPruner(dPos + nextStep[0],rPos + nextStep[1]); // the next step is worked into the argument of the recursive call
    }      
  }
  
  // Scanes the map for dead ends and uses the pathPruner to remove them
  public void prunePaths() {    
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == -2) {
          map[i][j] = -1; // For fringe cases where removal is unecessary
          this.pathPruner(i,j);
        }
      }      
    }
    
    
  }
  
  // Generates doors based on the work of previous methods
  public void genDoors() {
    int numAdj; // Initilizes the number of adjacent room tiles
    
    for (int i = 6; i < map.length - 6; i++) {
      for (int j = 6; j < map[0].length - 6; j++) {
        if (map[i][j] == -1) {
          
          if (adjMatrixFlower(map,-1,i,j) == 1) { // if there is only one adjacent room tile, the currnt tile is set to be a door tile
            map[i][j] = -2;
          }        
        }
      }
    }
    
    for (int i = 6; i < map.length - 6; i++) {
      for (int j = 6; j < map[0].length - 6; j++) {
        if (map[i][j] == 0) {          
          if (adjMatrixFlower(map,-1,i,j) == 1) { // if there is only one adjacent room tile, the current tile is set to be a door tile
            map[i][j] = -2;
          }        
        }
      }
    }
  }
  
  // Takes in a map, coordinates, and a target tile type
  // determines if the coordinates are fully surrounded by the target tile, and returns a boolean
  
  public static int adjMatrixFlower(int[][] map, int target, int dPos, int rPos) {
    int numAdj = 0;
    
    if (map[dPos + 1][rPos]  == target) {
      numAdj++;
    }
    if (map[dPos - 1][rPos]  == target) {
      numAdj++;
    }
    if (map[dPos][rPos + 1]  == target) {
      numAdj++;
    }
    if (map[dPos][rPos - 1]  == target) {
      numAdj++;
    }
    
    return numAdj;
  }
  
  public static int adjMatrixSquare(int[][] map, int target, int dPos, int rPos) {
    int numAdj = 0;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (map[dPos + i][rPos + j] == target) {
          numAdj++;
        }
      }
    }
    return numAdj;
  }
  
  // Inserts a spawn and progression point into the map.
  public void insertStairs() {
    int randD,randR; // Initalzes integers for random generation
    
    // Generates two sets of random coordinates for putting the points on
    do {
      randD = getRand(2,(map.length-3)/6);
      randR = getRand(2,(map[0].length-3)/6);
    } while (adjMatrixSquare(map, -1, randD*6,randR*6) < 9); // While loop ensures that the points are in the middle of a room
    
    map[randD*6][randR*6] = -3;
    
    do {
      randD = getRand(2,(map.length-3)/6);
      randR = getRand(2,(map[0].length-3)/6);
    } while (adjMatrixSquare(map, -1, randD*6,randR*6) < 9);
    
    map[randD*6][randR*6] = -4;
  }
  
  public void designateWalls() {
    int numAdj;        
    
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        if (map[i][j] == 0) {
          for (int i2 = -1; i2 < 2; i2++) {
            for (int j2  = -1; j2 < 2; j2++) {
              if (map[i + i2][j + j2] == 1) {
                map[i + i2][j + j2] = 2;
              }
            }
          }    
        }
      }
    }
    
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        if (map[i][j] == -1) {
          for (int i2 = -1; i2 < 2; i2++) {
            for (int j2  = -1; j2 < 2; j2++) {
              if (map[i + i2][j + j2] == 1 || map[i + i2][j + j2] == 2) {
                map[i + i2][j + j2] = 3;
              }
            }
          }    
        }      
      }
    }
  }
  
  public void removeDudRooms() {
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        
        if (adjMatrixFlower(map,-1,i,j) == 0 && map[i][j] == -1) {
          map[i][j] = 0;
        }        
      }      
    }   
  }
  
  public void finalizeDesignations() {
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        if (map[i][j] == -1) {
          if (map[i + 1][j] == -1 && map[i - 1][j] == -1 && map[i][j + 1] > 0 && map[i][j - 1] > 0) {
            map[i][j] = -2;
          } 
          if (map[i + 1][j] > 0 && map[i - 1][j] > 0 && map[i][j + 1] == -1 && map[i][j - 1]  == -1) {
            map[i][j] = -2;
          } 
        }         
        
        if (adjMatrixFlower(map,-2,i,j) == 1 && map[i][j] == -2) {
          map[i][j] = -1;
        }
        
        if (adjMatrixFlower(map,-1,i,j) == 0 && map[i][j] == -1) {
          map[i][j] = 0;
        }
        
        if (map[i][j] == 0) {
          if (adjMatrixFlower(map,-2,i,j) == 2 && adjMatrixFlower(map,0,i,j) == 0) {
            map[i][j] = -5;
          }    
        }
      }      
    }
  }
  
  public void spawnChests() {
    int randD,randR;                    
    int wallChests = 0;
    
    do {
      randD = getRand(2,(map.length-3));
      randR = getRand(2,(map[0].length-3));
      
      if (map[randD][randR] == 3) {
        if (adjMatrixSquare(map,3,randD,randR) == 3 && adjMatrixSquare(map,1,randD,randR) == 3 && adjMatrixSquare(map,-1,randD,randR) == 3) {
          map[randD][randR] = 5;
          wallChests++;
        }
      }
    } while (wallChests < 10);
  }
  
  public void furnishRooms() {    
    for (int i = 1; i < (map.length-3)/6; i++) {
      for (int j = 1; j < (map[0].length - 3)/6; j++) {
        
        if (map[i*6][j*6] == 100) {
          
          for (int i2 = -3; i2 < 4; i2++) {
            for (int j2 = -3; j2 < 4; j2++) {
              map[i*6 + i2][j*6 + j2] = -101;
            }
          }
          
          map[i*6][j*6] = 101;
          
        }
        
        else if (map[i*6][j*6] == 200) {
          int[][] template = 
          {
            {-201,-201,-201,-201,-201,-201,-201,-201,-201},
            {-201,-201,-201,-201,-201,-201,-201,-201,-201},
            {-201,-201,-207,-202,-202,-202,-204,-201,-201},
            {-201,-201,-203, 208, 208, 208,-203,-201,-201},
            {-201,-201,-203, 208, 209, 208,-203,-201,-201},
            {-201,-201,-203, 208, 208, 208,-203,-201,-201},
            {-201,-201,-206,-202,-202,-202,-205,-201,-201},
            {-201,-201,-201,-201,-201,-201,-201,-201,-201},
            {-201,-201,-201,-201,-201,-201,-201,-201,-201},
          };
          
          
          for (int i2 = -4; i2 < 5; i2++) {
            for (int j2 = -4; j2 < 5; j2++) {
              map[i*6 + i2][j*6 + j2] = template[i2 + 4][j2 + 4];
            }
          } 
          
          if (map[i*6][j*6 + 5] == -2) {
            map[i*6][j*6 + 4] = -202;
            map[i*6][j*6 + 3] = -202;
          }
          if (map[i*6][j*6 - 5] == -2) {
            map[i*6][j*6 - 4] = -202;
            map[i*6][j*6 - 3] = -202;
          }
          if (map[i*6 + 5][j*6] == -2) {
            map[i*6 + 4][j*6] = -203;
            map[i*6 + 3][j*6] = -203;
          }
          if (map[i*6 - 5][j*6] == -2) {
            map[i*6 - 4][j*6] = -203;
            map[i*6 - 3][j*6] = -203;
          }
          
        }
        
        
      }
    }
  }
  
  public void spawnDebris() {
    int numDebris = 0;
    int randD,randR;
    
    do{
      randD = getRand(2,(map.length-3));
      randR = getRand(2,(map[0].length-3));
      
      if (map[randD][randR] == -1) {
        if (adjMatrixSquare(map,-1,randD,randR) == 9) {
          map[randD][randR] = 6;
          numDebris++;
        }
      }     
    } while (numDebris < 10);
  }
  // Main method that uses the the previously created methods in conjuction to generate a complete map
  public int[][] generateMap(int effMapSizeW, int effMapSizeD) {   
    int mapSizeW = (effMapSizeW-1)*6 + 3; // Initalizes map size
    int mapSizeD = (effMapSizeD-1)*6 + 3;
    this.genTemplate(mapSizeW,mapSizeD); // Generates an empty template
    
    //System.out.println("Initial gen pass");
    //visMap2(this.charMap(map)); 
    
    // Sets off the initial recursion
    int[] startDir = {0,6};    
    this.carvePath(6,6,startDir,500);
    
    //System.out.println("Primary path pass");
    
    // Does more recursive calls on already-carved points to branch out the paths
    for (int i = 1; i < (Math.pow(mapSizeW,2)); i++) {
      int randD,randR;      
      do {
        randD = getRand(1,(mapSizeD-3)/6);
        randR = getRand(1,(mapSizeW-3)/6);
      } while (map[randD*6][randR*6] != 0);
      this.carvePath(randD*6,randR*6,startDir,500);
      map[randD*6][randR*6] = 0; // contigency to remove unecessary dead-end markers
      
    }
    
    
    //System.out.println("Secondary path pass");
    
    if (map[7][6] == 1 || map[6][7] == 1) { // cleans up the start of the recursion
      map[6][6] = -2;
    }
    
    // Does post-processing through other methods
    
    this.genRooms(15);    
    //System.out.println("room pass");    
    this.prunePaths();
    //System.out.println("Prune pass");
    this.removeDudRooms();
    //System.out.println("Dud removal pass");
    this.genDoors();
    //System.out.println("Door pass");
    this.insertStairs();
    //System.out.println("Stair pass");
    this.designateWalls();           
    //System.out.println("Wall designation pass");
    this.finalizeDesignations();
    //System.out.println("Designation pass");
    this.furnishRooms();
    this.spawnChests();
    //System.out.println("Chest pass");
    this.spawnDebris();
    
    this.trimMap();
    return map;             
  }
  
  public void trimMap() {
    int[][] trimmedArray = new int[map.length - 10][map[0].length - 10];
    
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        trimmedArray[i - 5][j - 5] = map[i][j];
      }
    }
  
    map = trimmedArray;
  }
  
  public char[][] charMap(int[][] result) {
    char[][] resultProc = new char[result.length][result[0].length];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        if (result[i][j] == 208) { // reactor stabilizers
          resultProc[i][j] = 'z';
        } else if (result[i][j] == 209) { // reactor core
          resultProc[i][j] = 'r';
        } else if (result[i][j] == 101) { // chest room chest
          resultProc[i][j] = 'c';
        } else if (result[i][j] == 6) { // junk spawn
          resultProc[i][j] = 'J';
        } else if (result[i][j] == 5) { // wall safe
          resultProc[i][j] = 'S';
        } else if (result[i][j] == 4) { // chest
          resultProc[i][j] = 'C';
        } else if (result[i][j] == 3) { // room wall
          resultProc[i][j] = '|';
        } else if (result[i][j] == 2) { // hallway wall
          resultProc[i][j] = '~';
        } else if (result[i][j] == 1) { // wall
          resultProc[i][j] = '-';
        } else if (result[i][j] == 0) { // hallway
          resultProc[i][j] = 'X';        
        } else if (result[i][j] == -2) { // door
          resultProc[i][j] = 'D';
        } else if (result[i][j] == -1) { // room tile
          resultProc[i][j] = 'R';
        } else if (result[i][j] == -3) { // spawn
          resultProc[i][j] = '@';
        } else if (result[i][j] == -4) { // stair
          resultProc[i][j] = '#';
        } else if (result[i][j] == -5) { // airlock
          resultProc[i][j] = 'A';
        } else if (result[i][j] == -101) { // chest room floor
          resultProc[i][j] = '=';
        } else if (result[i][j] == -201) { // chasm
          resultProc[i][j] = '%';
        } else if (result[i][j] == -202) { // horizontal walkway
          resultProc[i][j] = 'h';
        } else if (result[i][j] == -203) { // veritcal walkway
          resultProc[i][j] = 'v';
        } else if (result[i][j] == -204) { // corner 1
          resultProc[i][j] = 'a';
        } else if (result[i][j] == -205) { // corner 2
          resultProc[i][j] = 'b';
        } else if (result[i][j] == -206) { // corner 3
          resultProc[i][j] = 'c';
        } else if (result[i][j] == -207) { // corner 4
          resultProc[i][j] = 'd';
        } else {
          if ((i<=5)||(i>=result.length-5)||(j<=5)||(j>=result[0].length-5)){
            resultProc[i][j] = '-';
          }else{
            resultProc[i][j] = 'X';
          }
        }
      }
    }
    
    return resultProc;
  }
  
  public static void visMap2(char[][] map) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        System.out.print(map[i][j]);
      }
      System.out.println("");
    }
  }
  
  public static void main(String[] args) {
    MapGen2_8 tester = new MapGen2_8();
    visMap2(tester.charMap(tester.generateMap(24,6))); // width-depth generates a map of integers
    //tester.generateMap(24,6);
  }  
}