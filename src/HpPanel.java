import javax.swing.*;
import java.awt.*;

class HpPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  HpPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    g.setColor (new Color (69,218,215));
    g.drawImage(hp,0,0,xVal, yVal,this);
    g.fillRect (8,8, xVal-16,yVal-16);
  } 
  public void refresh(){
    this.repaint();
  }
}