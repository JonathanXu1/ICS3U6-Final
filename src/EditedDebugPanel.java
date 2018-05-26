/////////////////////
import javax.swing.*;
import java.awt.*;

/////////////////////
class DebugPanel extends JLayeredPane{
  private int maxX;
  private int maxY;
  DebugPanel(int maxX, int maxY){
    this.maxX = maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
    this.setBackground(new Color(0,0,0,0));
  }
  @Override
  /**
   *paintComponent
   *
   *@param: Graphics g
   *@return: 
   */
    public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.RED);
    g.fillRect(0,0,maxX,maxY);
  }
}
