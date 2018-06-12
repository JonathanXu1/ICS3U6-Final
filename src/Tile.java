import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Image;

abstract class Tile {
  private Color minimapColor;
  private boolean viewed = false;
  private boolean focus = false;
  private String type = "";
  
  Tile(Color minimapColor, String type){
    this.minimapColor = minimapColor;
    this.type = type;
  }
  
  public Color getMinimapColor(){
    return minimapColor;
  }
  
  public void setMinimapColor(Color minimapColor){
    this.minimapColor = minimapColor;
  }
  
  public void setViewed(){
    viewed = true;
  }
  
  public boolean getViewed(){
    return viewed;
  }
  
  public void setFocus(boolean focus){
    this.focus = focus;
  }
  
  public boolean getFocus(){
    return focus;
  }
  abstract void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus);
  
  public BufferedImage rotate(double radians, Image image){
    BufferedImage bimage;
    if(image instanceof BufferedImage){
      bimage = (BufferedImage)image;
    } else{
      // Create a buffered image with transparency
      bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      
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
  
  public String getType(){
    return type;
  }
}