class Character{
  private int x;
  private int y;
  
  //Constructor
  Character(int x, int y){
    this.x = x;
    this.y=y;
  }
  
  //Getters and setters
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