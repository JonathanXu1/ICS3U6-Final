import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class EnergySword extends MeleeWeapon {
  Image energySword;
  EnergySword(int dbty){
    super(dbty);
    this.setRarity(2);
    this.setDamage(10);    
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel) {
    energySword = Toolkit.getDefaultToolkit().getImage("../res/EnergySword.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(energySword, x,y,width,height,gamePanel);
  }
} 