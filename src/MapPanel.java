import javax.swing.*;
import java.awt.*;

class MapPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  MapPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image map = Toolkit.getDefaultToolkit().getImage("../res/Map.png");
    g.drawImage(map,0,0,xVal, yVal,this);
  }
  public void refresh(){
    this.repaint();
  }
}