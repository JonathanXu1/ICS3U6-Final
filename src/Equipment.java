public abstract class Equipment extends Item{
  private int durability;
  private int[] components;
  
  Equipment(int dbty) {
    this.durability = dbty;
  }
  
  public int getDurability() {
    return this.durability;
  }
  
  public void setDurability(int d) {
    this.durability = d;
  }
  
  public int[] getComponets() {
    return this.components;
  }
  
  public void setComponents(int[] input) {
    this.components = input;
  }
  
  
}