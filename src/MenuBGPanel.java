import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;

class MenuBGPanel extends JPanel{
  private int xVal;
  private int yVal;
  MenuBGPanel(int xVal, int yVal){
    setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image menuBg = Toolkit.getDefaultToolkit().getImage("../res/bgPlaceholder.jpg");
    g.drawImage(menuBg,0,0,xVal,yVal,this);
  }
  public void refresh(){
    this.repaint();
  }
}
