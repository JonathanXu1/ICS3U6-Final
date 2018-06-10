import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class GammaHammer extends MeleeWeapon{
  Image gammaHammer;
  GammaHammer(int dbty) {
    super(dbty);
    this.setRarity(4);
    this.setDamage(20);
    this.setName("Gamma Hammer");
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