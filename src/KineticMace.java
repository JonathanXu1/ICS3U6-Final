/////////////////////
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;

/////////////////////
class KineticMace extends MeleeWeapon{
  Image kineticMace;
  
  
  KineticMace(int dbty) {
    super(dbty); //
    this.setRarity(3);
    this.setDamage(15);    
    this.setName("Kinetic Mace");
  }
  
  /**
   *drawItem
   *Draws the kinetic mace
   *@param: The Graphics g, the int x, the int y, the int width, the int height, and the GamePanel gamePanel
   *@return: 
   */
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    kineticMace = Toolkit.getDefaultToolkit().getImage("../res/KineticMace.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(kineticMace, x,y,width,height,gamePanel);
  }
}
