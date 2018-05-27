import java.awt.Color;

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
}