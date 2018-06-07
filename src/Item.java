import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Item{
  private int rarity;
  private boolean selected = false;
  Item(){      
  }
  
  public void setRarity(int r) {
    this.rarity = r;
  }
  
  public int getRarity() {
    return this.rarity;
  }
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
  public void setItemSelected (boolean selected){
    this.selected = selected;
  }
  public boolean getItemSelected(){
    return (selected);
  }
}