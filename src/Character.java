class Character{
  ///Could make it so that this and the background implement tileScaling, meaning they must have get and set tile size
  //We should probably make everything here static
  private int x;
  private int y;
  Character(){
  }
  public int getArrayX(){
    return (x);
  }
  public void setArrayX(int xNew){
    x =xNew;
  }
  public int getArrayY(){
    return (y);
  }
  public void setArrayY(int yNew){
    y =yNew;
  }
}