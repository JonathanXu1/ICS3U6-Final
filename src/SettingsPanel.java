import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

class SettingsPanel extends JPanel{
  private boolean music = false;
  private boolean soundfx = false;
  private boolean fs = false;
  private int xVal, yVal;
  private CustomCheckButton musicButton = new CustomCheckButton();
  private CustomCheckButton soundFxButton = new CustomCheckButton();
  private JLabel musicLabel = new JLabel("Music");
  private JLabel soundFxLabel = new JLabel("SoundFx");
  //Constructor
  SettingsPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    musicButton.setBounds(xVal/2 - 120, yVal/2, 100, 50);
    musicButton.add(musicLabel);
    this.add(musicButton);
    musicButton.setVisible(true);
    
    soundFxButton.setBounds(xVal/2, yVal/2, 100, 50);
    soundFxButton.add(soundFxLabel);
    this.add(soundFxButton);
    soundFxButton.setVisible(true);
    
  }
  
  @Override
  public void paintComponent(Graphics g){
    g.setColor(new Color(0, 127, 127));
    g.fillRect(0, 0, xVal, yVal);
  }
  
  public boolean[] getSettings(){
    if(musicButton.isSelected()){
      music = true;
    } else{
      music = false;
    }
    if(soundFxButton.isSelected()){
      soundfx = true;
    } else{
      soundfx = false;
    }
    
    boolean[] result = new boolean[2];
    result[0] = music;
    result[1] = soundfx;
    return result;
  }
}