/////////////////////
abstract class Weapon extends Equipment {
  private int damage;
  private int freezeChance;
  private int flameChance;
  private int lightningChance;
  private boolean weaponSelect;
  
// Weapon is created with a duarbility constructor passed up through superclasses up to Equipment.
  Weapon(int dbty) {
    super(dbty);
  }
  
//Getters-Setters
  /**
   *getDamage
   *Gets the damage
   *@param: 
   *@return: An int
   */
  public int getDamage() {
    return this.damage;
  }
  
  /**
   *setDamage
   *Sets the weapon damage
   *@param: int dm
   *@return: 
   */
  public void setDamage(int dm) {
    this.damage = dm;
  }
  /**
   *getFreezeChance
   *Gets the freeze chance
   *@param: 
   *@return: An int
   */
  public int getFreezeChance(){
    return (freezeChance);
  }
  /**
   *setFreezeChance
   *Sets the freeze chance
   *@param: int freezeChance
   *@return: 
   */
  public void setFreezeChance(int freezeChance){
    if (freezeChance>100){
      this.freezeChance=100;
    }else{
      this.freezeChance = freezeChance;
    }
  }
  /**
   *getFlameChance
   *Gets the flame chance
   *@param: 
   *@return: An int
   */
  public int getFlameChance(){
    return (flameChance);
  }
  /**
   *setFlameChance
   *Sets the flame chance
   *@param: int flameChance
   *@return: 
   */
  public void setFlameChance(int flameChance){
    if (flameChance>100){
      this.flameChance=100;
    }else{
      this.flameChance = flameChance;
    }
  }
  /**
   *getLightningChance
   *Gets the lightning chance
   *@param: 
   *@return: An int
   */
  public int getLightningChance(){
    return (lightningChance);
  }
  /**
   *setLightningChance
   *Sets the lightning chance
   *@param: int lightningChance
   *@return: 
   */
  public void setLightningChance(int lightningChance){
    if (lightningChance>100){
      this.lightningChance=100;
    }else{
      this.lightningChance = lightningChance;
    }
  }
  /**
   *getWeaponSelect
   *Gets the selection of the weapon
   *@param: 
   *@return: A boolean
   */
  public boolean getWeaponSelect(){
    return (weaponSelect);
  }
  /**
   *setWeaponSelect
   *Sets the selection of the weapon
   *@param: boolean weaponSelect
   *@return: 
   */
  public void setWeaponSelect(boolean weaponSelect){
    this.weaponSelect = weaponSelect;
  }
}
