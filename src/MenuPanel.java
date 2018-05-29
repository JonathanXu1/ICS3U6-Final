import javax.swing.JLayeredPane;
import java.awt.Dimension;

class MenuPanel extends JLayeredPane{
  MenuPanel(int xVal, int yVal){
    setFocusable(true);
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
}