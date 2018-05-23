import javax.swing.*;
import java.awt.*;

class ItemPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  ItemPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    g.drawImage(left,0,0,(int)(yVal*208.0/87.0), yVal,this);
    g.drawImage(middle,(int)(yVal*208.0/87.0),0,xVal-(int)(yVal*1.05*2), yVal,this);
    g.drawImage(right,xVal-(int)(yVal*208.0/87.0),0,(int)(yVal*208.0/87.0), yVal,this);
  }
  public void refresh(){
    this.repaint();
  }
}