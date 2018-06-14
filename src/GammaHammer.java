/*
 * [GammaHammer.java];
 * 
 * The specific class for the Gamma Hammer, a rare melee weapon. It has very high damage.
 * It sets all the stats in the constructor and has code for graphics.
 * 
 * Developed by: Will, Artem, Jonathan
 * 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

class GammaHammer extends MeleeWeapon{
  Image gammaHammer;
  
  GammaHammer(int dbty) {
    super(dbty); // sends the durability back up to equipment
    this.setRarity(4); // sets rarity of item to 4
    this.setDamage(20); // sets damage of tem to 20 per hit
    this.setName("Gamma Hammer"); // sets name for inventory display
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    gammaHammer = Toolkit.getDefaultToolkit().getImage("../res/GammaHammer.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(gammaHammer, x,y,width,height,gamePanel);
  }
}