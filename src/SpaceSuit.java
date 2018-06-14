/*
 * [SpaceSuit.java];
 * 
 * The specific class for the space suit, the starting armor that the player has. It is poor
 * at negating damage. It sets all the stats in the constructor and has code for graphics.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

class SpaceSuit extends Armor {
  Image spaceSuit;
  SpaceSuit(int dbty){
    super(dbty);
    this.setRarity(1);
    this.setDefense(5); 
    this.setName("Space Suit");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    spaceSuit = Toolkit.getDefaultToolkit().getImage("../res/SpaceSuit.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(spaceSuit, x,y,width,height,gamePanel);
  }
}