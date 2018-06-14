/*
 * [Enemy.java];
 * 
 * This is the super class for all the enemies
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 
//Imports
/////////////////////
import java.awt.Color;

abstract class Enemy extends Entity{
  
  private boolean enraged = false;
  private boolean seen = false;
  
//Constructor
  Enemy(int h,int hC,int a,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor, boolean enraged){
    super (h,hC,a,freezeStatus,lightningStatus,flameStatus, minimapColor);
    this.enraged = enraged;
  }
  
//Getters and setters
  
  /**
   *setEnraged 
   *Sets whether or not the mob is enraged
   *@param: boolean enraged
   *@return: 
   */
  public void setEnraged (boolean enraged){
    this.enraged = enraged;
  }
  /**
   *getEnraged 
   *Returns whether or not the mob enranged
   *@param: 
   *@return: A boolean
   */
  public boolean getEnraged (){
    return enraged;
  }
  /**
   *getSeen
   *Returns if the mob is seen
   *@param: 
   *@return: A boolean
   */
  public boolean getSeen(){
    return (seen);
  }
  /**
   *setSeen
   *Sets if the mob is seen
   *@param: boolean seen
   *@return: 
   */
  public void setSeen(boolean seen){
    this.seen=seen;
  }
}
