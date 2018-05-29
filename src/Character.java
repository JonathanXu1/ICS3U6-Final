class Character{
  ///Could make it so that this and the background implement tileScaling, meaning they must have get and set tile size
  //We should probably make everything here static
  private static int x;
  private static int y;
  Character(){
  }
  public static int getArrayX(){
    return (x);
  }
  public static void setArrayX(int xNew){
    x =xNew;
  }
  public static int getArrayY(){
    return (y);
  }
  public static void setArrayY(int yNew){
    y =yNew;
  }
}