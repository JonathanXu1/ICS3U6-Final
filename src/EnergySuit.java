import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public class EnergySuit extends Armor {
  EnergySuit(int dbty){
    super(dbty);
    this.setRarity(3);
    this.setDefense(15); 
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    
  }
}