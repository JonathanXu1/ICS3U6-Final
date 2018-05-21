import javax.swing.*;
import java.awt.*;

class DisplayPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  private int draw;
  private Color currentCol;
  DisplayPanel(int xVal, int yVal, Color currentCol, int draw){
    this.xVal = xVal;
    this.yVal = yVal;
    this.currentCol = currentCol;
    this.draw = draw;
    Dimension gamePanelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(gamePanelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Image map = Toolkit.getDefaultToolkit().getImage("MAP.png");
    Image left = Toolkit.getDefaultToolkit().getImage("METALL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("METALR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("METALM.png");
    Image hp = Toolkit.getDefaultToolkit().getImage("HpBar.png");
    Image exp = Toolkit.getDefaultToolkit().getImage("ExpBar.png");
    g.setColor (currentCol);
    if (draw==0){
      g.fillRect (0,0, xVal,yVal);
    }else if (draw==1){
      g.drawImage(left,100,0,(int)(yVal*1.05), yVal,this);
      g.drawImage(middle,(int)(yVal*1.05)+100,0,xVal-(int)(yVal*1.05*2)-200, yVal,this);
      g.drawImage(right,xVal-(int)(yVal*1.05)-100,0,(int)(yVal*1.05), yVal,this);
    }else if (draw==2){
      g.drawImage(map,0,0,xVal, yVal,this);
    }else if (draw==3){
      g.drawImage(hp,0,0,xVal, yVal,this);
      g.fillRect (10,10, xVal-20,(int)((yVal-20.0)/2.0));
      g.setColor (new Color (230, 0,0));
      g.fillRect (10,10+(int)((yVal-20.0)/2.0), xVal-20,(int)((yVal-20.0)/2.0));
    }else if (draw==4){
      g.drawImage(exp,0,0,xVal, yVal,this);
      g.fillRect (8,8, xVal-17,(int)((yVal-16.0)/2.0)+1);
      g.setColor (new Color (143,188,143));
      g.fillRect (8,8+(int)((yVal-16.0)/2.0), xVal-17,(int)((yVal-16.0)/2.0)+1);
    }
  }
  public void animate(){
    this.repaint();
  }
}