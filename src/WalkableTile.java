import java.awt.Color;

public class WalkableTile extends Tile{
  private boolean steppedOn = false;
  WalkableTile(){
    super(Color.WHITE);
  }
  
  public void stepped(){
    steppedOn = true;
  }
  
  public boolean checkStepped(){
    return steppedOn;
  }
}