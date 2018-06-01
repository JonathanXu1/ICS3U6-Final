import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class FloorTile extends WalkableTile{
  Image floor = Toolkit.getDefaultToolkit().getImage("../res/FloorTile.png");
  FloorTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.drawImage(floor, x,y,width,height,gamePanel);
  }
}