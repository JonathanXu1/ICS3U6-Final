import javax.swing.JRadioButton;
import java.awt.FontFormatException;
import java.io.IOException;
import java.awt.Font;
import java.io.File;

class CustomRadioButton extends JRadioButton{
  private Font customHeader;
  
  CustomRadioButton(String name){
    super(name);
    
    try {
      customHeader = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(32f);
    } catch (IOException e) {
      e.printStackTrace();
    } catch(FontFormatException e) {
      e.printStackTrace();
    }
    
    this.setFont(customHeader);
    this.setContentAreaFilled(false);
  }
}