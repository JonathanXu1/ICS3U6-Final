public abstract class Item{
  private int rarity;
  
  Item(){      
  }
  
  public void setRarity(int r) {
    this.rarity = r;
  }
  
  public int getRarity() {
    return this.rarity;
  }
}