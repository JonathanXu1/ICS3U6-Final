/*
 * [Armor.java];
 * 
 * The superclass for all the armor in the game, which contains the various resistances to elements and the defense 
 * that negates damage to the player. Also sets up graphics implementationas as an astract.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 

import java.awt.Graphics;

public abstract class Armor extends Equipment {
  private int defense;
  private int freezeDefense;
  private int flameDefense;
  private int lightningDefense;
  Armor(int dbty){
    super (dbty); // Passes durability to Equipment superclass
  }
  
  // Getters-Setters
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
  
  // Abstract method for drawing the object, taking in an x,y dimensions, and the gamepanel to be applied to. 
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}