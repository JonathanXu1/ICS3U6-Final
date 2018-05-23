import javax.swing.*;
import java.awt.*;

class ExpPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  ExpPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
  public void paintComponent(Graphics g){
    Image exp = Toolkit.getDefaultToolkit().getImage("../res/Exp.png");
    g.setColor (new Color (152,251,152));
    g.drawImage(exp,0,0,xVal, yVal,this);
    g.fillRect (10,10, xVal-20,yVal-20);
  }
  public void refresh(){
    this.repaint();
  }
}