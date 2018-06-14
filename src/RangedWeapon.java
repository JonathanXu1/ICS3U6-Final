import java.awt.Graphics;

public abstract class RangedWeapon extends Weapon{
  
  RangedWeapon(int dbty) {
    super(dbty);
  }
  
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}