import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel{
  private int maxX;
  private int maxY;
  GamePanel(int maxX, int maxY){
    setFocusable(true);
    this.maxX= maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
  }
  public void paintComponent(Graphics g){
    //Main 

    g.setColor (Color.BLACK);
    g.fillRect (0,0, maxX,maxY);
    Image left = Toolkit.getDefaultToolkit().getImage("../res/MetalL.png");
    Image right = Toolkit.getDefaultToolkit().getImage("../res/MetalR.png");
    Image middle = Toolkit.getDefaultToolkit().getImage("../res/MetalM.png");
    g.drawImage(left,0,maxY-(int)(maxY*2.5/10.0),(int)((int)(maxY*2.5/10.0)*208.0/87.0),(int)(maxY*2.5/10.0),this);
    g.drawImage(middle,(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0),maxX -(int)((int)(maxY*2.5/10.0)*208.0/87.0*2.0)+5, (int)(maxY*2.5/10.0),this);
    g.drawImage(right,maxX-(int)((int)(maxY*2.5/10.0)*208.0/87.0),maxY-(int)(maxY*2.5/10.0), (int)((int)(maxY*2.5/10.0)*208.0/87.0), (int)(maxY*2.5/10.0),this);
    //Continue
    Image map = Toolkit.getDefaultToolkit().getImage("../res/Map.png");
    g.drawImage(map,maxX-(int)(maxX*1.0/5.0),0,(int)(maxX*1.0/5.0), (int)(maxX*1.0/5.0),this);
    Image hp = Toolkit.getDefaultToolkit().getImage("../res/HpBar.png");
    g.setColor (new Color (69,218,215));
    g.drawImage(hp,10,10, ((int)(maxX*1.0/5.0)),  ((int)(maxX*1.0/5.0/200.0*14.0)),this);
    g.fillRect (18,18, ((int)(maxX*1.0/5.0))-16, ((int)(maxX*1.0/5.0/200.0*14.0))-16);
    Image exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    g.setColor (new Color (152,251,152));
    g.drawImage(exp,10,15+ ((int)(maxX*1.0/5.0/200.0*14.0)),((int)(maxX*1.0/5.0)), ((int)(maxX*1.0/5.0/200.0*10.0)),this);
    g.fillRect (18,23+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-16,((int)(maxX*1.0/5.0/200.0*10.0))-16);
    this.setVisible(true);
  }
  public void refresh(){
    this.repaint();
  }
}