import java.awt.Color;
import java.awt.Graphics;

class VoidTile extends WallTile{
  VoidTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.setColor (Color.BLACK);
    g.fillRect(x,y,width,height);
  }
}