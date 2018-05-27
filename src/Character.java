class Character{
  ///Could make it so that this and the background implement tileScaling, meaning they must have get and set tile size
  private int tileSize;
  private int x;
  private int y;
  Character(int x, int y, int tileSize){
    this.x= x;
    this.y=y;
    this.tileSize = tileSize;
  }
  public int getTileSize(){
    return (tileSize);
  }
  public void setTileSize (int tileSize){
    this.tileSize = tileSize;
  }
  public int getArrayX(){
    return (x);
  }
  public void setArrayX(int x){
    this.x =x;
  }
  public int getArrayY(){
    return (y);
  }
  public void setArrayY(int y){
    this.y =y;
  }
}