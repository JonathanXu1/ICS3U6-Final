import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
public abstract class Weapon extends Equipment {
  private int damage;
  
  Weapon(int dbty) {
    super(dbty);
  }
  
  public int getDamage() {
    return this.damage;
  }
  
  public void setDamage(int dm) {
    this.damage = dm;

  }
    abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}