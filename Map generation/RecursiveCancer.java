class RecursiveCancer {
  private int[][] map;
  
  RecursiveCancer(int[][] seed) {
    this.map = seed;
  }
  
  public int[][] returnCancer() {
    return this.map;
  }
  
  public void spreadCancer(int dPos, int rPos) {
    int targetTile = map[dPos][rPos];
    map[dPos][rPos] = -99;
    
    if (map[dPos + 1][rPos] == targetTile) {      
      spreadCancer(dPos + 1,rPos);
    }
    if (map[dPos - 1][rPos] == targetTile) {      
      spreadCancer(dPos - 1,rPos);
    }
    if (map[dPos][rPos + 1] == targetTile) {      
      spreadCancer(dPos,rPos + 1);
    }
    if (map[dPos + 1][rPos] == targetTile) {      
      spreadCancer(dPos,rPos - 1);
    }
  }
  
  public void spreadCancer2(int dPos, int rPos, int limit, int counter) {
    int targetTile = map[dPos][rPos];
    map[dPos][rPos] = -99;
    
    if (counter <= limit) {
      if (map[dPos + 1][rPos] == targetTile) {      
        spreadCancer2(dPos + 1,rPos,limit,counter + 1);
      }
      if (map[dPos - 1][rPos] == targetTile) {      
        spreadCancer2(dPos - 1,rPos, limit, counter + 1);
      }
      if (map[dPos][rPos + 1] == targetTile) {      
        spreadCancer2(dPos,rPos + 1, limit, counter + 1);
      }
      if (map[dPos + 1][rPos] == targetTile) {      
        spreadCancer2(dPos,rPos - 1, limit, counter + 1);
      }
    }
  }
  
}