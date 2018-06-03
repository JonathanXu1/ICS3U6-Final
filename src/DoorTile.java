import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class DoorTile extends WalkableTile{
  Image door = Toolkit.getDefaultToolkit().getImage("../res/DoorTile.png");
  DoorTile(Color minimapColor){
    super(minimapColor);
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel){
    g.drawImage(door, x,y,width,height,gamePanel);
  }
}