import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Item{
  private int rarity;
  private String name;
  private boolean selected = false;
  Item(){      
  }
  public int getRarity() {
    return this.rarity;
  }
  public void setRarity(int r) {
    this.rarity = r;
  }
  public String getName(){
    return (name);
  }
  public void setName(String name){
    this.name = name;
  }
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
  public void setItemSelected (boolean selected){
    this.selected = selected;
  }
  public boolean getItemSelected(){
    return (selected);
  }
}