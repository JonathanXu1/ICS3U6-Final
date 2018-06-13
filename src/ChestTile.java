import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

class ChestTile extends Tile{
  Image chest;
  String dir;
  boolean open = false;
  double orientation = 0;
  ChestTile(Color minimapColor, String dir, String type){
    super(minimapColor, type);
    this.dir = dir;
  }
  ChestTile(Color minimapColor, String dir, double orientation, String type){
    super(minimapColor, type);
    this.dir = dir;
    this.orientation = orientation;
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      if (open){
        chest = Toolkit.getDefaultToolkit().getImage(dir +"Open"+  ".png");
      }else{
        chest = Toolkit.getDefaultToolkit().getImage(dir + ".png");
      }
    } else{
      if (open){
      //chest = Toolkit.getDefaultToolkit().getImage(dir +"Open"+ "Dark" + ".png");
      }else{
        //chest = Toolkit.getDefaultToolkit().getImage(dir + ".png");
      }
    }
    if(orientation != 0){
      g.drawImage(rotate(orientation, chest), x,y,width,height,gamePanel);
    } else{
      g.drawImage(chest, x,y,width,height,gamePanel);
    }
  }
  public void openChest(){
    open  =true;
  }
}
