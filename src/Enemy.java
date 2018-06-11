import java.awt.Graphics;
import java.awt.Color;

class Enemy extends Entity{
  
  private boolean enraged = false;
  private boolean seen = false;
  
  //Constructor
  Enemy(int h,int hC,int a,int sP,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor, boolean enraged){
    super (h,hC,a,sP,freezeStatus,lightningStatus,flameStatus, minimapColor);
    this.enraged = enraged;
  }
  //Should be placed in Entity class and made abstract later
  public void drawEntity(Graphics g, int x, int y, int width, int height, int xDirection, int yDirection, GamePanel gamePanel){
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
  public boolean getSeen(){
    return (seen);
  }
  public void setSeen(boolean seen){
    this.seen=seen;
  }
}