//import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
public abstract class Armor extends Equipment {
  private int defense;
  private int freezeDefense;
  private int flameDefense;
  private int lightningDefense;
  Armor(int dbty){
    super (dbty);
  }
  public int getDefense(){
    return (defense);
  }
  public void setDefense(int defense){
    this.defense = defense;
  }
  public int getFreezeDefense(){
    return (freezeDefense);
  }
  public void setFreezeDefense(int freezeDefense){
    this.freezeDefense = freezeDefense;
  }
  public int getFlameDefense(){
    return (flameDefense);
  }
  public void setFlameDefense(int flameDefense){
    this.flameDefense = flameDefense;
  }
  public int getLightningDefense(){
    return (lightningDefense);
  }
  public void setLightningDefense(int lightningDefense){
    this.lightningDefense = lightningDefense;
  }
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}