import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class SolarScorcher extends RangedWeapon {
  Image solarScorcher;
  SolarScorcher(int dbty){ 
    super(dbty);
    this.setRarity(5);
    this.setDamage(20);
    this.setSPT(1);
    this.setName("Solar Scorcher");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    solarScorcher = Toolkit.getDefaultToolkit().getImage("../res/SolarScorcher.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(solarScorcher, x,y,width,height,gamePanel);
  }
}