public abstract class Equipment {
  private int durability;
  private int[] ingredients;
  
  public int getDurability() {
    return this.durability;
  }
  
  public void setDurability(int d) {
    this.durability = d;
  }
  
  public int[] getIngredients() {
    return this.ingredients;
  }
  
  public void setIngredients(int[] input) {
    this.ingredients = input;
  }
  
  
}