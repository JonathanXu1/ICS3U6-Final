class Background{
  private static int x = 0;
  private static int y  =0;
  private static int xDirection = 0;
  private static int yDirection  =0;
  private static boolean onTile = true;
  private static int tileSize;
  
  Background(int tSize){
    tileSize = tSize;
  }
  Background(){
  }
  public static int getXDirection(){
    return (xDirection);
  }
  public static void setXDirection(int xDire){
    xDirection = xDire;
  }
  
  public static int getYDirection(){
    return (yDirection);
  }
  public static void setYDirection(int yDire){
    yDirection = yDire;
  }
  
  public static void move(){
    x=x + xDirection;
    y=y + yDirection;
  }
  public static int getX(){
    return (x);
  }
  public static int getY(){  
    return (y);
  }
  public static boolean getOnTile(){
    return (onTile);
  }
  public static void setOnTile (){
    if ((x%tileSize==0)&&(y%tileSize==0)){
      onTile =true;
    }else{
      onTile =false;
    }
  }
  public static void setTileSize(int tSize){
    tileSize = tSize;
  }
  public static int getTileSize(){
    return (tileSize);
  }
}