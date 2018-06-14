/*
 * [Armor.java];
 * 
 * The superclass for all the armor in the game, which contains the various resistances to elements and the defense 
 * that negates damage to the player. Also sets up graphics implementationas as an astract.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */ 

//Imports

public abstract class Armor extends Equipment {
  private int defense;
  private int freezeDefense;
  private int flameDefense;
  private int lightningDefense;
  Armor(int dbty){
    super (dbty); // Passes durability to Equipment superclass
  }
  
  // Getters-Setters
  /**
   *getDefense 
   * Gets the defense
   *@return: The int defense
   */
  public int getDefense(){
    return (defense);
  }
      /**
   *setDefense 
   *Sets the defense for the item
   *@param: The int defense
   */
  public void setDefense(int defense){
    this.defense = defense;
  }
  /**
   *getFreezeDefense 
   *Gets the freeze resistance
   *@return: The int freezeDefense
   */
  public int getFreezeDefense(){
    return (freezeDefense);
  }
  /**
   *setFreezeDefense 
   *Sets the freeze resistance
   *@param: The int freezeDefense
   */
  public void setFreezeDefense(int freezeDefense){
    this.freezeDefense = freezeDefense;
  }
  /**
   *getFlameDefense 
   *Gets the flame resistance
   *@return: The int flameDefense
   */
  public int getFlameDefense(){
    return (flameDefense);
  }
  /**
   *setFlameDefense 
   *Sets the flame resistance
   *@param: The int flameDefense
   */
  public void setFlameDefense(int flameDefense){
    this.flameDefense = flameDefense;
  }
    /**
   *getLightningDefense 
   *Gets the lightning resistance
   *@return: The int flameDefense
   */
  public int getLightningDefense(){
    return (lightningDefense);
  }
    /**
   *setLightningDefense 
   *Sets the lightning resisance
   *@param: The int flameDefense
   */
  public void setLightningDefense(int lightningDefense){
    this.lightningDefense = lightningDefense;
  }
}