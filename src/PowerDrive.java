import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
class PowerDrive extends Drive{
  Image powerDrive;
  PowerDrive(){
    this.setName("Power Drive");
  }
  public void consume(){
    System.out.print ("Power");
  }
  public void drawItem(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    powerDrive = Toolkit.getDefaultToolkit().getImage("../res/PowerDrive.png");
    if (this.getItemSelected()){
      g.setColor(new Color(255, 255, 255, 100)); 
      g.fillRect (x,y,width,height);
    }
    g.drawImage(powerDrive, x,y,width,height,gamePanel);
  }
}