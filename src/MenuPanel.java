import javax.swing.*;
import java.awt.*;

class MenuPanel extends JLayeredPane{
  MenuPanel(int xVal, int yVal){
    setFocusable(true);
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
}