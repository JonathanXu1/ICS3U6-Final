import java.awt.Graphics;
import java.awt.Color;

class Enemy extends Entity{
  
  //Constructor
  Enemy(int h,int hC,int a,int sP,int sT){
    super (h,hC,a,sP,sT);
  }
  //Should be placed in Entity class and made abstract later
  public void drawEntity(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.setColor (Color.GRAY);
    g.fillRect(x,y,width,height);
        g.setColor (Color.BLACK);
    g.drawRect(x,y,width,height);
  }

  //Getters and setters
}