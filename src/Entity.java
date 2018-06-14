/////////////////////
import java.awt.Graphics;
import java.awt.Color;

abstract class Entity {
  private int health;
  private int healthCap;
  private int armor;
  private boolean freezeStatus;
  private boolean lightningStatus;
  private boolean flameStatus;
  private boolean moved;
  private int direction;
  private int tileXMod;
  private int tileYMod;
  private Color minimapColor;
  
  Entity(int h,int hC,int a,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor) {
    this.health = h;
    this.healthCap = hC;
    this.armor = a;
    this.freezeStatus = freezeStatus;
    this.lightningStatus = lightningStatus;
    this.flameStatus = flameStatus;
    this.moved = false;
    this.minimapColor = minimapColor;
  }
  Entity(){
  }
  /**
   *isDead
   *
   *@param: 
   *@return: A boolean
   */
  public boolean isDead() {
    if (this.health < 1) {
      return true;
    }
    return false;
  }
  
  abstract void drawEntity(Graphics g, int x, int y, int width, int height, int xDirection, int yDirection, GamePanel gamePanel);
//0 is nothing, 1 is up, 2 is down, 3 is left, 4 is right
  //Getters and settters
  /**
   *setTiling 
   *Sets if the mob is tiling
   *@param: int direction
   *@return: 
   */
  public void setTiling (int direction){
    this.direction = direction;
  }
  /**
   *getTiling
   *Returns how the mob is tiling
   *@param: 
   *@return: An int
   */
  public int getTiling(){
    return (direction);
  }
  /**
   *getTileXMod
   *Gets the mod for the tiling on the x
   *@param: 
   *@return: An int
   */
  public int getTileXMod(){
    return (tileXMod);
  }
  /**
   *setTileXMod
   *Sets the mod for the tiling on the x
   *@param: int tileXMod
   *@return: 
   */
  public void setTileXMod(int tileXMod) {
    this.tileXMod = tileXMod;
  }
  /**
   *getTileYMod
   *Gets the mod for the tiling on the y
   *@param: 
   *@return: An int
   */
  public int getTileYMod(){
    return (tileYMod);
  }
  /**
   *setTileYMod
   *Sets the mod for the tiling on the y
   *@param: int tileYMod
   *@return: 
   */
  public void setTileYMod(int tileYMod) {
    this.tileYMod = tileYMod;
  }
  /**
   *getMinimapColor
   *Returns the minimap color
   *@param: 
   *@return: A Color
   */
  public Color getMinimapColor(){
    return minimapColor;
  }
  //No setter as this is determined by the tile
  /**
   *getHealth
   *Returns the entity health
   *@param: 
   *@return: An int
   */
  public int getHealth() {
    return this.health;
  }
  
  /**
   *setHealth
   *Sets the health
   *@param: int updt
   *@return: 
   */
  public void setHealth(int updt) {
    this.health = updt;
  }
  
  /**
   *getArmor
   *Returns the entity armor
   *@param: 
   *@return: An int
   */
  public int getArmor() {
    return this.armor;
  }
  
  /**
   *setArmor
   *Sets the entity armor
   *@param: int updt
   *@return: 
   */
  public void setArmor(int updt) {
    this.armor = updt;
  }
  
  /**
   *getHealthCap
   *Returns the entity health cap
   *@param: 
   *@return: An int
   */
  public int getHealthCap() {
    return this.healthCap;
  }
  
  /**
   *setHealthCap
   *Sets the entity health cap
   *@param: int healthCap
   *@return: 
   */
  public void setHealthCap(int healthCap) {
    this.healthCap=healthCap;
  }
  
  /**
   *getFlame
   *Returns the entity flame status
   *@param: 
   *@return: A boolean
   */
  public boolean getFlame() {
    return flameStatus;
  }
  /**
   *setFlame
   *Sets the entity flame status
   *@param: boolean flameStatus
   *@return: 
   */
  public void setFlame(boolean flameStatus) {
    this.flameStatus = flameStatus;
  }
  /**
   *getFreeze
   *Returns the entity freeze status
   *@param: 
   *@return: A boolean
   */
  public boolean getFreeze() {
    return freezeStatus;
  }
  /**
   *setFreeze
   *Sets the entity freeze status
   *@param: boolean freezeStatus
   *@return: 
   */
  public void setFreeze(boolean freezeStatus) {
    this.freezeStatus = freezeStatus;
  }
  /**
   *getLightning
   *Returns the entity lightning status
   *@param: 
   *@return: A boolean
   */
  public boolean getLightning() {
    return lightningStatus;
  }
  /**
   *setLightning
   *Sets the entity lightning status
   *@param: boolean lightningStatus
   *@return: 
   */
  public void setLightning(boolean lightningStatus) {
    this.lightningStatus = lightningStatus;
  }
  /**
   *getMoved
   *Returns if the entity is moved
   *@param: 
   *@return: A boolean
   */
  public boolean getMoved() {
    return this.moved;
  }
  
  /**
   *setMoved
   *Sets if the entity has moved
   *@param: boolean moved
   *@return: 
   */
  public void setMoved(boolean moved) {
    this.moved = moved;
  }
}
