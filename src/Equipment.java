public abstract class Equipment extends Item{ // Superclass for all Equipment
  private int durability; // How much any piece of euipment can last before it breaks
  private int durabilityCap; // How much durability the item starts with and how much it can be be repaired
  
  Equipment(int dbty) {
    this.durability = dbty;
    durabilityCap = dbty;
  }
  
  // Getters-setters
  /**
   *getDurability
   *Returns durability
   *@param:
   *@return: An int which is the durability
   */
  public int getDurability() {
    return this.durability;
  }
  /**
   *setDurability
   *Returns durability
   *@param: An int which is the durabiity
   *@return: 
   */
  public void setDurability(int d) {
    this.durability = d;
  }
  /**
   *getDurabilityCap
   *Returns durability cap
   *@param:
   *@return: An int which is the durability cap
   */
  public int getDurabilityCap() {
    return this.durabilityCap;
  }
  /**
   *setDurabilityCap
   *Sets durability cap
   *@param: The durability cap, which is an int
   *@return:
   */
  public void setDurabilityCap(int dc) {
    this.durabilityCap = dc;
  }
  
}