import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
class LaserRifle extends RangedWeapon {
  // item stats;
  
  
  LaserRifle(int dbty){ 
    super(dbty);
    this.setRarity(1);
    this.setDamage(25);
    this.setSPT(1);
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    
  }
}