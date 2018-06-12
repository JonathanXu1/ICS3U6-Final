import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;

class FloorTile extends WalkableTile{
  Image floor;
  String dir;
  double orientation = 0;
  FloorTile(Color minimapColor, String dir){
    super(minimapColor);
    this.dir = dir;
  }
  FloorTile(Color minimapColor, String dir, double orientation){
    super(minimapColor);
    this.dir = dir;
    this.orientation = orientation;
  }
  public void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus){
    if(focus){
      floor = Toolkit.getDefaultToolkit().getImage(dir + ".png");
    } else{
      floor = Toolkit.getDefaultToolkit().getImage(dir + "Dark" + ".png");
    }
    if(orientation != 0){
      g.drawImage(rotate(orientation, floor), x,y,width,height,gamePanel);
    } else{
      g.drawImage(floor, x,y,width,height,gamePanel);
    }
  }
  
  public BufferedImage rotate(double radians, Image image){
    BufferedImage bimage;
    if(image instanceof BufferedImage){
      bimage = (BufferedImage)image;
    } else{
      // Create a buffered image with transparency
      bimage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
      
      // Draw the image on to the buffered image
      Graphics2D bGr = bimage.createGraphics();
      bGr.drawImage(image, 0, 0, null);
      bGr.dispose();
    }
    AffineTransform tx = new AffineTransform();
    tx.rotate(radians, bimage.getWidth() / 2, bimage.getHeight() / 2);
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    bimage = op.filter(bimage, null);
    return bimage;
  }
}