import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Equipment extends Item{
  private int durability;
  private int durabilityCap;
  
  Equipment(int dbty) {
    this.durability = dbty;
    durabilityCap = 100;
  }
  
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