/*
 * [MapGen2_2.java]
 * 
 * Generates a mazelike map, with rooms that are radomly sized, equally dispersed. Also, unecessary dead ends are removed,
 * doors are generated, along with stairs going up and down. 
 * 
 * Version cleaned up and commented, now with imporved OOP, fixed rooms, and unique room ID. 
 * 
 * NOTE: Dynamic room scaling does not work, and needs to be adjusted within the generateMao method to work for different map sizes
 * expirementation has yielded no better method of getting at the optimal room size than doing it by hand. 
 * 
 * Author: Artem Sotnikov
 * Date: May 27th, 2018
 * 
 */ 


class mapGen2_2{    
  // Generates a random true/false depending on the percentage entered as argument, out of 1000
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
  public static int[][] genTemplate(int sizeW, int sizeD) {
    int[][] retArray = new int[sizeD + 10][sizeW + 10];
    
    for (int i = 5; i < sizeD + 5; i++) {
      for (int j = 5; j < sizeW + 5; j++) {
        retArray[i][j] = 1;
      }
    }
    
    return retArray;
  }
    
  // printing method for visualization    
  public static void printArrayCharComp(char[][] input) {
    for (int i = 5; i < input.length - 5; i++) {
      for (int j = 5; j < input[0].length - 5; j++) {
        System.out.print(input[i][j]);
      }
      System.out.println("");
    }
  }
  
  // main recursive method for carving initial paths through the template
  public static int[][] carvePath(int[][] map, int dPos, int rPos, int dirState[], int probTurn) {
    map[dPos][rPos] = 0; // Sets the current spot to zero 
    int[][] returnMap  = new int[map.length][map.length]; // Initializes return map
    int[] newDirState = new int[2];   // Initializes future movement array
    
    
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
    
    
    if (numOpt == 0) { // if no ways to move are present, a base case is hit and the map is returned.
      map[dPos][rPos] = -2; // The final tile is set as a dead end, for future consideration
      return map;
    } 
    
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
    returnMap = carvePath(map, dPos + newDirState[0], rPos + newDirState[1], newDirState, probTurn);            
    return returnMap;
  }
  
  // Takes the map and randomly spawns rooms in it, based on the number of rooms specified as argument
  public static int[][] genRooms(int[][] map, int numRooms) {
    int[][] returnArray = new int[map.length][map[0].length]; 
    int randR,randD; // initilizes integers for random spawn
    // Creates a matrix of rooms to prevent room clustering and keep track of where rooms are
    int[][] roomMatrix = new int[(map.length-3)/6][(map[0].length-3)/6]; 
    
    returnArray = map; 
    
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
      
      int randDReal = randD*6; // random numbers are ocnverted to real coordinates on the map
      int randRReal = randR*6;
      
      int randWMod1 = getRand(-1,3); // Random modifiers for room size are generated
      int randWMod2 = getRand(-1,3);
      int randDMod1 = getRand(-1,3);
      int randDMod2 = getRand(-1,3);
      
      // Room is carved based on the random size modifiers
      for (int i = randDMod1; i < 9 - randDMod2; i++){
        for (int j = randWMod1; j < 9 - randWMod2; j++) {
          returnArray[randDReal - 4 + i][randRReal - 4 + j] = -1;
        }
      }
      
      // All surrounding spaces are converted into room spaces to ease tile and door placement
      for (int i = randDMod1 - 1; i < 9 - randDMod2 + 1; i++){ // can be optimized
        for (int j = randWMod1 - 1; j < 9 - randWMod2 + 1; j++) {          
          if (returnArray[randDReal - 4 + i][randRReal - 4 + j] == 0) {
            returnArray[randDReal - 4 + i][randRReal - 4 + j] = -1;
          } 
        }
      }
    }
    
    return returnArray;
  }
  
  // Recursive method employed to remove unecessary dead ends from the map, taking in xy coordinates and the map
  // currntly being worked on as travel.
  public static int[][] pathPruner(int dPos, int rPos, int[][] map) {
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
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[0].length; j++) {
          if (map[i][j] == 3) {
            map[i][j] = 1;
          }
        }
      } 
      return map;
    } else { // Otherwise, the current square is blotted out and the recursion moves onto the next square
      // The next step is set above, and we know it was only set once
      map[dPos][rPos] = 1;
      map = pathPruner(dPos + nextStep[0],rPos + nextStep[1],map); // the next step is worked into the argument of the recursive call
    }
    
    return map;    
  }
  
  // Scanes the map for dead ends and uses the pathPruner to remove them
  public static int[][] prunePaths(int[][] map) {
    int[][] retArray = map;
    
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] == -2) {
          map[i][j] = -1; // For fringe cases where removal is unecessary
          retArray = pathPruner(i,j,retArray);
        }
      }      
    }
    
    
    return retArray;
  }
  
  // Generates doors based on the work of previous methods
  public static int[][] genDoors(int[][] map) {
    int numAdj; // Initilizes the number of adjacent room tiles
    
    for (int i = 6; i < map.length - 6; i++) {
      for (int j = 6; j < map[0].length - 6; j++) {
        if (map[i][j] == -1) {
          numAdj = 0; // number is reset for every new tile
          
          // number is determined
          if (map[i + 1][j]  == -1) {
            numAdj++;
          }
          if (map[i - 1][j]  == -1) {
            numAdj++;
          }
          if (map[i][j + 1]  == -1) {
            numAdj++;
          }
          if (map[i][j - 1]  == -1) {
            numAdj++;
          }
          
          if (numAdj == 1) { // if there is only one adjacent room tile, the currnt tile is set to be a door tile
            map[i][j] = -2;
          }        
        }
      }
    }
    
    for (int i = 6; i < map.length - 6; i++) {
      for (int j = 6; j < map[0].length - 6; j++) {
        if (map[i][j] == 0) {
          numAdj = 0; // number is reset for every new tile
          
          // number is determined
          if (map[i + 1][j]  == -1) {
            numAdj++;
          }
          if (map[i - 1][j]  == -1) {
            numAdj++;
          }
          if (map[i][j + 1]  == -1) {
            numAdj++;
          }
          if (map[i][j - 1]  == -1) {
            numAdj++;
          }
          
          if (numAdj == 1) { // if there is only one adjacent room tile, the currnt tile is set to be a door tile
            map[i][j] = -2;
          }        
        }
      }
    }
    
    return map;
  }
  
  // Takes in a map, coordinates, and a target tile type
  // determines if the coordinates are fully surrounded by the target tile, and returns a boolean
  public static boolean fullSurround(int[][] map, int i, int j,  int target) {
    int numAdj = 0;
    
    if (map[i + 1][j]  == target) {
      numAdj++;
    }
    if (map[i - 1][j]  == target) {
      numAdj++;
    }
    if (map[i][j + 1]  == target) {
      numAdj++;
    }
    if (map[i][j - 1]  == target) {
      numAdj++;
    }
    
    if (numAdj == 4) {
      return true;
    }
    return false;
  }
  
  // Inserts a spawn and progression point into the map.
  public static int[][] insertStairs(int[][] map) {
    int randD,randR; // Initalzes integers for random generation
    
    // Generates two sets of random coordinates for putting the points on
    do {
      randD = getRand(2,(map.length-3)/6);
      randR = getRand(2,(map[0].length-3)/6);
    } while (!fullSurround(map,randD*6,randR*6,-1)); // While loop ensures that the points are in the middle of a room
    
    map[randD*6][randR*6] = -3;
    
    do {
      randD = getRand(2,(map.length-3)/6);
      randR = getRand(2,(map[0].length-3)/6);
    } while (!fullSurround(map,randD*6,randR*6,-1) && map[randD*6][randR*6] != 3);
    
    map[randD*6][randR*6] = -4;
    
    return map;
  }
  
  public static int[][] designateWalls(int[][] map) {
    int numAdj;        
    
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        if (map[i][j] == 1) {
          numAdj = 0; // number is reset for every new tile
          
          // number is determined
          if (map[i + 1][j]  == 0) {
            numAdj++;
          }
          if (map[i - 1][j]  == 0) {
            numAdj++;
          }
          if (map[i][j + 1]  == 0) {
            numAdj++;
          }
          if (map[i][j - 1]  == 0) {
            numAdj++;
          }    
          
          if (j == 5 || j == map[0].length - 6) {
            numAdj--;
          }
          
          if (i == 5 || i == map.length - 6) {
            numAdj--;
          } 
          
          if (numAdj >= 1) { // if there is only one adjacent room tile, the currnt tile is set to be a a hallway wall
            map[i][j] = 2;
          }        
        }
      }
    }
    
    for (int i = 5; i < map.length - 5; i++) {
      for (int j = 5; j < map[0].length - 5; j++) {
        if (map[i][j] == 1 || map[i][j] == 2) {
          numAdj = 0; // number is reset for every new tile
          
          // number is determined
          if (map[i + 1][j]  == -1 || map[i + 1][j]  == -2) {
            numAdj++;
          }
          if (map[i - 1][j]  == -1 || map[i - 1][j]  == -2) {
            numAdj++;
          }
          if (map[i][j + 1]  == -1 || map[i][j + 1]  == -2) {
            numAdj++;
          }
          if (map[i][j - 1]  == -1 || map[i][j - 1]  == -2) {
            numAdj++;
          }
          
          if (numAdj >= 1) { // if there is only one adjacent room tile, the currnt tile is set to be a room wall
            map[i][j] = 3;
          }        
        }
      }
    }
    
    
    return map;
  }
  
  // Main method that uses the the previously created methods in conjuction to generate a complete map
  public static int[][] generateMap(int effMapSizeW, int effMapSizeD) {   
    int mapSizeW = (effMapSizeW-1)*6 + 3; // Initalizes map size
    int mapSizeD = (effMapSizeD-1)*6 + 3;
    int[][] subject = genTemplate(mapSizeW,mapSizeD); // Generates an empty template
    // Sets off the initial recursion
    int[] startDir = {0,6};
    int[][] result = carvePath(subject,6,6,startDir,500);
    
    // Does more recursive calls on already-carved points to branch out the paths
    for (int i = 1; i < (Math.pow(mapSizeW,2)); i++) {
      int randD,randR;      
      do {
        randD = getRand(1,(mapSizeD-3)/6);
        randR = getRand(1,(mapSizeW-3)/6);
      } while (result[randD*6][randR*6] != 0);
      result = carvePath(result,randD*6,randR*6,startDir,500);
      result[randD*6][randR*6] = 0; // contigency to remove unecessary dead-end markers
      
    }
    
    if (result[7][6] == 1) { // cleans up the start of the recursion
      result[6][6] = -2;
    }
    
    // Does post-processing through other methods
    
    result = genRooms(result,15);
    
    result = prunePaths(result);
    
    result = genDoors(result);
    
    result = insertStairs(result);
    
    result = designateWalls(result);       
    
    return result;             
  }
  
  // Method for visualizing the generated map onto console
  public static void visMap(int[][] result) {
    char[][] resultProc = new char[result.length][result[0].length];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        if (result[i][j] == 3) {
          resultProc[i][j] = '|';
        } else if (result[i][j] == 2) {
          resultProc[i][j] = '~';
        } else if (result[i][j] == 1) {
          resultProc[i][j] = '-';
        } else if (result[i][j] == -2) {
          resultProc[i][j] = 'D';
        } else if (result[i][j] == -1) {
          resultProc[i][j] = 'R';
        } else if (result[i][j] == -3) {
          resultProc[i][j] = '@';
        } else if (result[i][j] == -4) {
          resultProc[i][j] = '#';
        } else {
          resultProc[i][j] = 'X';
        }
      }
    }
    
    printArrayCharComp(resultProc);   
  }
  
  public static void main(String[] args) {
    visMap(generateMap(24,6)); // width-depth generates a map of integers
  }
}