import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
class WallTile extends Tile{
  Image wall = Toolkit.getDefaultToolkit().getImage("../res/WallTile.png");
  WallTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.drawImage(wall, x,y,width,height,gamePanel);
  }
}