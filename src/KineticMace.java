import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class KineticMace extends MeleeWeapon{
  Image kineticMace;
  
  
  KineticMace(int dbty) {
    super(dbty); //
    this.setRarity(3);
    this.setDamage(15);    
    this.setName("Kinetic Mace");
  }
  
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    kineticMace = Toolkit.getDefaultToolkit().getImage("../res/KineticMace.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(kineticMace, x,y,width,height,gamePanel);
  }
}