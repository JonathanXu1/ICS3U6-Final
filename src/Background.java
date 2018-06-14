/////////////////////
class Background{
  
//Coordinates
  private int x = 0;
  private int y  =0;
  private int xDirection = 0;
  private int yDirection  =0;
//Positioning
  private boolean onTile = true;
  private boolean firstMovement =false;
  private final int TILE_SIZE;
  
//Constructor
  Background(int TILE_SIZE){
    this.TILE_SIZE = TILE_SIZE;
  }
  
//Getters and setters
//Coordinates
  /**
   *getX
   *Returns the x value for the background
   *@param: 
   *@return: An int
   */
  public int getX(){
    return (x);
  }
  /**
   *setX
   *Sets the x value for the background
   *@param: int x
   *@return: 
   */
  public void setX(int x){
    this.x = x;
  }
  /**
   *getY
   *Returns the y value for the background
   *@param: 
   *@return: An int
   */
  public int getY(){  
    return (y);
  }
  /**
   *setY
   *Sets the y value for the background
   *@param: int y
   *@return: 
   */
  public void setY(int y){  
    this.y = y;
  }
  /**
   *getXDirection
   *Returns the x direction
   *@param: 
   *@return: An int
   */
  public int getXDirection(){
    return (xDirection);
  }
  /**
   *setXDirection
   *Sets the x direction
   *@param: int xDirection
   *@return: 
   */
  public void setXDirection(int xDirection){
    this.xDirection = xDirection;
  }
  /**
   *getYDirection
   *Returns the y direction
   *@param: 
   *@return: An int
   */
  public int getYDirection(){
    return (yDirection);
  }
  /**
   *setYDirection
   *Sets the y direction
   *@param: int yDirection
   *@return: 
   */
  public void setYDirection(int yDirection){
    this.yDirection = yDirection;
  }
//Positioning
  /**
   *getOnTile
   *Returns if the character is on the tile
   *@param: 
   *@return: A boolean
   */
  public boolean getOnTile(){
    return (onTile);
  }
  /**
   *setOnTile 
   *Sets whether or not the character is on the tile
   *@param: 
   *@return: 
   */
  public void setOnTile (){
    if ((x%TILE_SIZE==0)&&(y%TILE_SIZE==0)){
      onTile =true;
    }else{
      onTile =false;
    }
  }
  /**
   *getFirstMovement
   *Gets the first movement
   *@param: 
   *@return: A boolean
   */
  public boolean getFirstMovement(){
    return (firstMovement);
  }
  /**
   *setFirstMovement 
   *Sets the first movement
   *@param: boolean firstMovement
   *@return: 
   */
  public void setFirstMovement (boolean firstMovement){
    this.firstMovement =firstMovement;
  }
//Moves the background smoothly    
  /**
   *move
   *Moves the background by increments of 10 pixels, creating the effect of a moving character
   *@param: 
   *@return: 
   */
  public void move(){
    x=x + xDirection;
    y=y + yDirection;
  }
}
