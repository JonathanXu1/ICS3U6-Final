import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class SpaceSuit extends Armor {
  SpaceSuit(int dbty){
    super(dbty);
    this.setRarity(1);
    this.setDefense(5); 
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    
  }
}