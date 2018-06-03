import java.awt.Graphics;
import java.awt.Color;

abstract class Entity {
  private int health;
  private int healthCap;
  private int armor;
  private int speed; 
  private int status;
  private boolean moved;
  private int direction;
  private int tileXMod;
  private int tileYMod;
  private Color minimapColor;
  
  Entity(int h,int hC,int a,int sP,int sT, Color minimapColor) {
    this.health = h;
    this.healthCap = hC;
    this.armor = a;
    this.speed = sP;
    this.status = sT;
    this.moved = false;
    this.minimapColor = minimapColor;
  }
  Entity(){
  }
  public boolean isDead() {
    if (this.health < 1) {
      return true;
    }
    return false;
  }
  
  public int getHealth() {
    return this.health;
  }
  
  public void setHealth(int updt) {
    this.health = updt;
  }
  
  public int getArmor() {
    return this.armor;
  }
  
  public void setArmor(int updt) {
    this.armor = updt;
  }
  
  public int getCap() {
    return this.healthCap;
  }
  
  public int getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(int updt) {
    this.speed = updt;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int updt) {
    this.status = updt;
  }
  
  public boolean getMoved() {
    return this.moved;
  }
  
  public void setMoved(boolean moved) {
    this.moved = moved;
  }
  
  abstract void drawEntity(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
  //0 is nothing, 1 is up, 2 is down, 3 is left, 4 is right
  public void setTiling (int direction){
    this.direction = direction;
  }
  public int getTiling(){
    return (direction);
  }
  public int getTileXMod(){
    return (tileXMod);
  }
  public void setTileXMod(int tileXMod) {
    this.tileXMod = tileXMod;
  }
  public int getTileYMod(){
    return (tileYMod);
  }
  public void setTileYMod(int tileYMod) {
    this.tileYMod = tileYMod;
  }
  public Color getMinimapColor(){
    return minimapColor;
  }
}