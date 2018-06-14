import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

class SettingsPanel extends JPanel{
  private boolean music = false;
  private boolean soundfx = false;
  private int xVal, yVal;
  //Constructor
  SettingsPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
  }
  
  @Override
  public void paintComponent(Graphics g){
    g.setColor(Color.RED);
    g.fillRect(0, 0, xVal, yVal);
  }
  
  public boolean[] getSoundSettings(){
    boolean[] result = new boolean[2];
    result[1] = music;
    result[2] = soundfx;
    return result;
  }
}