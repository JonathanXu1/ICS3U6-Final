import java.awt.Graphics;
import java.awt.Color;

class Enemy extends Entity{
  
  private boolean enraged = false;
  
  //Constructor
  Enemy(int h,int hC,int a,int sP,int sT, Color minimapColor, boolean enraged){
    super (h,hC,a,sP,sT, minimapColor);
    this.enraged = enraged;
  }
  //Should be placed in Entity class and made abstract later
  public void drawEntity(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.setColor (Color.GRAY);
    g.fillRect(x,y,width,height);
    g.setColor (Color.BLACK);
    g.drawRect(x,y,width,height);
  }
  
  //Getters and setters
  public void setEnraged (boolean enraged){
    this.enraged = enraged;
  }
  public boolean getEnraged (){
    return enraged;
  }
}