import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

class Character extends Entity{
    Image character = Toolkit.getDefaultToolkit().getImage("../res/Character.png");
  //Constructor
  Character(int h,int hC,int a,int sP,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor){
    super (h,hC,a,sP,freezeStatus,lightningStatus,flameStatus, minimapColor);
  }
  public void drawEntity(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.drawImage(character, x,y,width,height,gamePanel);
  }
  
  //Getters and setters
}