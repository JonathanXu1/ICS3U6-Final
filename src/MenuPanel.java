import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;

class MenuPanel extends JPanel{
  MenuPanel(int x, int y, int xVal, int yVal){
    setFocusable(true);
    this.setBounds(x, y, xVal, yVal);
    this.setLayout(new GridLayout(6,1));
    this.setOpaque(false);
  }
}