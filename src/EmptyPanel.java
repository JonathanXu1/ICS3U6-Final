import javax.swing.*;
import java.awt.*;

class EmptyPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  EmptyPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
}