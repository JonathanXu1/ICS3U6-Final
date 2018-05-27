import java.awt.Color;

public class Tile {
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
}