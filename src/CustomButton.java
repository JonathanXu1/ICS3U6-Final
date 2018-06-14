/////////////////////
import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.io.File;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.IOException;

//Imports
/////////////////////
class CustomButton extends JButton{
  private JLabel label;
  private Font customHeader;
  
//Constructor, no methods required
  CustomButton(String name, CustomMouseListener listener){
    label = new JLabel(name);
    try {
      customHeader = Font.createFont(Font.TRUETYPE_FONT, new File("../res/fonts/spaceage.ttf")).deriveFont(32f);
    } catch (IOException e) {
      e.printStackTrace();
    } catch(FontFormatException e) {
      e.printStackTrace();
    }
    this.addMouseListener(listener);
    label.setForeground(Color.WHITE);
    label.setFont(customHeader);
    this.setLayout(new BorderLayout());
    this.add(label, BorderLayout.WEST);
    
    this.setBackground(new Color(46, 121, 132, 50));
    this.setContentAreaFilled(false);
  }
  
  /**
   *updateStyle
   *Updates the image style
   *@param: int state
   *@return: 
   */
  public void updateStyle(int state){
    if(state == 0){
//Transparent button
      this.setBorderPainted(false);
      this.setContentAreaFilled(false);
//this.setAlignmentX(0);
      
    } else if (state == 1){
//Highlighted button
      this.setBorderPainted(true);
      this.setContentAreaFilled(true);
    }
  }
}
