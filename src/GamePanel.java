import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel{
  private int maxX;
  private int maxY;
  private boolean debugState;
  private int stringLength;
  private String fps;
    private Font menuFont = new Font("Courier New", Font.PLAIN, 20);
  private double totalMem, memUsed, memPercent;

  GamePanel(int maxX, int maxY){
    setFocusable(true);
    this.maxX= maxX;
    this.maxY = maxY;
    Dimension panelSize= new Dimension (maxX, maxY);
    this.setPreferredSize(panelSize);
  }
  public void setDebugState(boolean debugState, int fps, double totalMem, double memUsed){
    this.debugState = debugState;
    this.fps = Integer.toString(fps);
    this.totalMem = totalMem;
    this.memUsed = memUsed;
    memPercent = (memUsed/totalMem)*100;
  }
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
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
    g.fillRect (16,16, ((int)(maxX*1.0/5.0))-12, ((int)(maxX*1.0/5.0/200.0*14.0))-12);
    Image exp = Toolkit.getDefaultToolkit().getImage("../res/ExpBar.png");
    g.setColor (new Color (152,251,152));
    g.drawImage(exp,10,15+ ((int)(maxX*1.0/5.0/200.0*14.0)),((int)(maxX*1.0/5.0)), ((int)(maxX*1.0/5.0/200.0*10.0)),this);
    g.fillRect (16,21+((int)(maxX*1.0/5.0/200.0*14.0)), ((int)(maxX*1.0/5.0))-12,((int)(maxX*1.0/5.0/200.0*10.0))-12);
    if(debugState){
      stringLength = ("FPS: "+fps).length();
      g.setColor(Color.decode("#565656"));
      g.fillRect(30, 15, 12*stringLength, 20);
      g.setFont (menuFont);
      g.setColor(Color.WHITE);
      g.drawString("FPS: " + fps, 30, 30);
     // g.fillRect(maxX - 300, 25, 250, 20);
    //  g.drawString("Memory Usage: " + String.format("%.2f", memPercent) + "% (" + Double.toString(memUsed) + " out of " + Double.toString(totalMem), maxX-295, 30);
    }
    this.setVisible(true);
  }
  public void refresh(){
    this.repaint();
  }
}