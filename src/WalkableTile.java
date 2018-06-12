import java.awt.Color;
import java.awt.Graphics;

abstract class WalkableTile extends Tile{
  private boolean steppedOn = false;
  WalkableTile(Color minimapColor){
    super(minimapColor);
  }
  
  public void stepped(){
    steppedOn = true;
  }
  
  public boolean checkStepped(){
    return steppedOn;
  }
  abstract void drawTile(Graphics g, int x, int y, int width, int height, GamePanel gamePanel, boolean focus);
}