/*
 * [RangedWeapon.java];
 * 
 * The superclass for all ranged weapons in the game. It enables functional sorting of weapons and
 * ferries up durability to the Weapon superclass.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

import java.awt.Graphics;

public abstract class RangedWeapon extends Weapon{
  
  RangedWeapon(int dbty) {
    super(dbty);
  }

  
  abstract public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}