import javax.swing.*;
import java.awt.*;

class DebugPanel extends JLayeredPane{
  private int maxX;
  private int maxY;
  DebugPanel(int maxX, int maxY){
    this.maxX = maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
    //Transparent panel
    this.setOpaque(false);
    /* Getting errors for this
    this.setContentAreaFilled(false);
    this.setBorderPainted(false);
    */
  }
}