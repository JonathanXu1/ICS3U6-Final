import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

class SettingsPanel extends JPanel{
  private boolean music = false;
  private boolean soundfx = false;
  private int xVal, yVal;
  private CustomCheckButton musicButton = new CustomCheckButton();
  private CustomCheckButton soundFxButton = new CustomCheckButton();
  private JLabel musicLabel = new JLabel("Music");
  private JLabel soundFxLabel = new JLabel("SoundFx");
  private Image bg = Toolkit.getDefaultToolkit().getImage("../res/Bg.png");
  
  //Constructor
  SettingsPanel(int xVal, int yVal){
    this.setFocusable(true);
    this.xVal = xVal;
    this.yVal = yVal;
    Dimension panelSize= new Dimension (xVal, yVal);
    this.setPreferredSize(panelSize);
    this.setLayout(null);
    
    musicButton.setBounds(xVal/2 - 100, yVal/2, 100, 50);
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
    /*
    g.setColor(new Color(0, 127, 127));
    g.fillRect(0, 0, xVal, yVal);
    */
    g.drawImage(bg,0,0,xVal,yVal,this);
  }
  
  public boolean[] getSoundSettings(){
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