import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Armor extends Equipment {
  private int defense;
  private boolean freezeDefense;
  private boolean flameDefense;
  private boolean lightningDefense;
  Armor(int dbty){
    super (dbty);
  }
  public int getDefense(){
    return (defense);
  }
  public void setDefense(int defense){
    this.defense = defense;
  }
  public boolean getFreezeDefense(){
    return (freezeDefense);
  }
  public void setFreezeDefense(boolean freezeDefense){
    this.freezeDefense = freezeDefense;
  }
  public boolean getFlameDefense(){
    return (flameDefense);
  }
  public void setFlameDefense(boolean flameDefense){
    this.flameDefense = flameDefense;
  }
  public boolean getLightningDefense(){
    return (lightningDefense);
  }
  public void setLightningDefense(boolean lightningDefense){
    this.lightningDefense = lightningDefense;
  }
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}