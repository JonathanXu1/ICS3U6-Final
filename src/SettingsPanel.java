import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

class SettingsPanel extends JPanel{
  private boolean music = false;
  private boolean soundfx = false;
  private int xVal, yVal;
  private CustomCheckButton musicButton = new CustomCheckButton();
  //Constructor
  SettingsPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    musicButton.setBounds(xVal/2- 100, yVal/2, 200, 200);
    this.add(musicButton);
    musicButton.setVisible(true);
  }
  
  @Override
  public void paintComponent(Graphics g){
    g.setColor(new Color(0, 127, 127));
    g.fillRect(0, 0, xVal, yVal);
  }
  
  public boolean[] getSoundSettings(){
    boolean[] result = new boolean[2];
    result[1] = music;
    result[2] = soundfx;
    return result;
  }
}