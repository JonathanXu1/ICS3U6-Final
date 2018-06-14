import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public abstract class Equipment extends Item{ // Superclass for all Equipment
  private int durability; // How much any piece of euipment can last before it breaks
  private int durabilityCap; // How much durability the item starts with and how much it can be be repaired
  
  Equipment(int dbty) {
    this.durability = dbty;
    durabilityCap = dbty;
  }
  
  // Getters-setters
  public int getDurability() {
    return this.durability;
  }
  
  public void setDurability(int d) {
    this.durability = d;
  }
  public int getDurabilityCap() {
    return this.durabilityCap;
  }
  
  public void setDurabilityCap(int dc) {
    this.durabilityCap = dc;
  }
  
}