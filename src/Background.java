class Background{

  //Coordinates
  private int x = 0;
  private int y  =0;
  private int xDirection = 0;
  private int yDirection  =0;
  //Positioning
  private boolean onTile = true;
  private final int TILE_SIZE;
  
  //Constructor
  Background(int TILE_SIZE){
    this.TILE_SIZE = TILE_SIZE;
  }
  
  //Getters and setters
  //Coordinates
  public int getX(){
    return (x);
  }
  public void setX(int x){
    this.x = x;
  }
  public int getY(){  
    return (y);
  }
  public void setY(int y){  
    this.y = y;
  }
    public int getXDirection(){
    return (xDirection);
  }
  public void setXDirection(int xDirection){
    this.xDirection = xDirection;
  }
  public int getYDirection(){
    return (yDirection);
  }
  public void setYDirection(int yDirection){
    this.yDirection = yDirection;
  }
  //Positioning
  public boolean getOnTile(){
    return (onTile);
  }
  public void setOnTile (){
    if ((x%TILE_SIZE==0)&&(y%TILE_SIZE==0)){
      onTile =true;
    }else{
      onTile =false;
    }
  }
  //Moves the background smoothly    
  public void move(){
    x=x + xDirection;
    y=y + yDirection;
  }
}