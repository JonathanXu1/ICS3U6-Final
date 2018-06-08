import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Armor extends Equipment {
  int defense;
  Armor(int dbty){
    super (dbty);
  }
  public int getDefense(){
    return (defense);
  }
  public void setDefense(int defense){
    this.defense = defense;
  }
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}