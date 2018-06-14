/////////////////////
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
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
  /**
   *rotate
   *Rotates the image
   *@param: The double radians, and the Image image
   *@return: A BufferedImage
   */
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
  
  /**
   *drawTile
   *Draws the tile, is an abstract method
   *@param: The Graphics g, the int x, the int y, the int width, the int height, the GamePanel gamePanel, and the boolean focus
   *@return:
   */
  abstract void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus);
  //Getters and setters
  /**
   *getMinimapColor
   *Gets the minimap color for visualization
   *@param: 
   *@return: A Color
   */
  public Color getMinimapColor(){
    return minimapColor;
  }
  
  /**
   *setMinimapColor
   *Sets the minimap color for visualization
   *@param: Color minimapColor
   *@return: 
   */
  public void setMinimapColor(Color minimapColor){
    this.minimapColor = minimapColor;
  }
  
  /**
   *setViewed
   *Sets the viewed
   *@param: 
   *@return: 
   */
  public void setViewed(){
    viewed = true;
  }
  
  /**
   *getViewed
   *Gets the viewed
   *@param: 
   *@return: A boolean
   */
  public boolean getViewed(){
    return viewed;
  }
  
  /**
   *setFocus
   *Sets the image focus
   *@param: boolean focus
   *@return: 
   */
  public void setFocus(boolean focus){
    this.focus = focus;
  }
  
  /**
   *getFocus
   *Returns the image focus
   *@param: 
   *@return: A boolean
   */
  public boolean getFocus(){
    return focus;
  } 
  
  /**
   *getType
   *Returns the string
   *@param: 
   *@return: A String
   */
  public String getType(){
    return type;
  }
}
