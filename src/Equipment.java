import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Equipment extends Item{
  private int durability;
  
  Equipment(int dbty) {
    this.durability = dbty;
  }
  
  public int getDurability() {
    return this.durability;
  }
  
  public void setDurability(int d) {
    this.durability = d;
  }
  
}