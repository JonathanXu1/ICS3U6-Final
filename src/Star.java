import java.util.Random;
import java.awt.Color;

public class Star{
  private int rgb, maxrgb;
  private int minrgb = 21;
  private Color color = new Color(minrgb, minrgb, minrgb);
  private boolean peaked = false;
  private boolean died = false;
  Random rand = new Random();
  Star(){
    maxrgb = rand.nextInt(100)+75; //75 to 175
    rgb = minrgb;
  }
  public Color getColor(){
    return color;
  }
  public void updateColor(){
    if(rgb <= maxrgb && !peaked){
      rgb += 5;
    } else if(rgb > maxrgb && !peaked){
      peaked = true;
    }
    if (rgb >= minrgb && peaked){
      rgb -= 5;
    } else if (rgb < minrgb && peaked){
      died = true;
    }
    color = new Color(rgb, rgb, rgb);
  }
  public boolean getDied(){
    return died;
  }
}