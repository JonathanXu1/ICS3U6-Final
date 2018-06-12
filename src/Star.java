import java.util.Random;
import java.awt.Color;

public class Star{
  private Color color;
  private int rgb, maxrgb;
  private boolean peaked = false;
  private boolean died = false;
  Random rand = new Random();
  Star(){
    color = new Color(0, 0, 0);
    maxrgb = rand.nextInt(100)+150; //150 to 250
  }
  public Color getColor(){
    return color;
  }
  public void updateColor(){
    if(rgb <= maxrgb && !peaked){
      rgb += 5;
    } else if(rgb > maxrgb){
      peaked = true;
    }
    if (rgb >= 5 && peaked){
      rgb -= 5;
    } else if (rgb < 5 && peaked){
      died = true;
    }
    color = new Color(rgb, rgb, rgb);
  }
  public boolean getDied(){
    return died;
  }
}