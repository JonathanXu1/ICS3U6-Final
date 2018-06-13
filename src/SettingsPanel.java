import javax.swing.JPanel;
import java.awt.Graphics;

class SettingsPanel extends JPanel{
  private boolean music = false;
  private boolean soundfx = false;
  SettingsPanel(){
  }
  @Override
  public void paintComponent(Graphics g){
  }
  public boolean[] getSoundSettings(){
    boolean[] result = new boolean[2];
    result[1] = music;
    result[2] = soundfx;
    return result;
  }
}