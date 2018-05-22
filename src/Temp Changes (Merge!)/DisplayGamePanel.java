import javax.swing.*;
import java.awt.*;

class DisplayGamePanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  private int draw;
  private Color currentCol;
  DisplayGamePanel(int xVal, int yVal, Color currentCol, int draw){
    this.xVal = xVal;
    this.yVal = yVal;
    this.currentCol = currentCol;
    this.draw = draw;
    Dimension gamePanelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(gamePanelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image map = Toolkit.getDefaultToolkit().getImage("MapG.png");
    Image left = Toolkit.getDefaultToolkit().getImage("MetalL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("MetalR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("MetalM.png");
    Image hp = Toolkit.getDefaultToolkit().getImage("HpBarG.png");
    Image exp = Toolkit.getDefaultToolkit().getImage("ExpBarG.png");
    g.setColor (currentCol);
    if (draw==0){
      g.fillRect (0,0, xVal,yVal);
    }else if (draw==1){
      g.drawImage(left,0,0,(int)(yVal*208.0/87.0), yVal,this);
      g.drawImage(middle,(int)(yVal*208.0/87.0),0,xVal-(int)(yVal*1.05*2), yVal,this);
      g.drawImage(right,xVal-(int)(yVal*208.0/87.0),0,(int)(yVal*208.0/87.0), yVal,this);
    }else if (draw==2){
      g.drawImage(map,0,0,xVal, yVal,this);
    }else if (draw==3){
      g.drawImage(hp,0,0,xVal, yVal,this);
      g.fillRect (8,8, xVal-16,yVal-16);
    }else if (draw==4){
      g.drawImage(exp,0,0,xVal, yVal,this);
      g.fillRect (8,8, xVal-16,yVal-16);
    }
  }
  public void animate(){
    this.repaint();
  }
}