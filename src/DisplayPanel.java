import javax.swing.*;
import java.awt.*;

class DisplayPanel extends JLayeredPane{
  private int xVal;
  private int yVal;
  private int [] quadX = new int [4];
  private int [] quadY = new int [4];
  private int [] hexX = new int [6];
  private int [] hexY = new int [6];
  private int polygon;
  private Color currentCol;
  DisplayPanel(int xVal, int yVal, Color currentCol, int polygon){
    this.xVal = xVal;
    this.yVal = yVal;
    this.currentCol = currentCol;
    this.polygon = polygon;
    Dimension gamePanelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(gamePanelSize);
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    //Image metal = Toolkit.getDefaultToolkit().getImage("METAL.png");
    g.setColor (currentCol);
    if (polygon==0){
      g.fillRect (0,0, xVal,yVal);
    }else if (polygon==1){
      quadX[0]=0;
      quadX[1]=0;
      quadX[2]=xVal-yVal;
      quadX[3]=xVal;
      quadY[0]=0;
      quadY[1]=yVal;
      quadY[2]=yVal;
      quadY[3]=0;
      g.fillPolygon (quadX, quadY, 4);
    }else if (polygon==2){
      g.fillRect (0,0, xVal,yVal);
//      for (int i=0;i<xVal;i=i+(int)(yVal*6.0)){
//        g.drawImage(metal,i,0,(int)(yVal*6.0), yVal,this);
//      }
    }else{
      hexX[0]=0;
      hexX[1]=0;
      hexX[2]=(int)(xVal/6.0);
      hexX[3]=xVal-(int)(xVal/6.0);
      hexX[4]=xVal ;
      hexX[5]=xVal;
      hexY[0]=yVal;
      hexY[1]=(int)(yVal/2.0);
      hexY[2]=0;
      hexY[3]=0;
      hexY[4]=(int)(yVal/2.0);
      hexY[5]=yVal;
      g.fillPolygon (hexX, hexY, 6);
//      for (int i=0;i<xVal;i=i+(int)(yVal*6.0)){
//        g.drawImage(metal,i,0,(int)(yVal*6.0), yVal,this);
//      }
    }
  }
  public void animate(){
    this.repaint();
  }
}