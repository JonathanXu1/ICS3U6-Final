import java.awt.Color;
import java.awt.Graphics;

abstract class Tile {
  private Color minimapColor;
  private boolean viewed = false;
  
  Tile(Color minimapColor){
    this.minimapColor = minimapColor;
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
  abstract void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel);
}