import javax.swing.JPanel;
import java.awt.Dimension;

class MenuPanel extends JPanel{
  MenuPanel(int xVal, int yVal){
    setFocusable(true);
    Dimension panelSize = new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
}