import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.io.File;
import java.awt.FontFormatException;
import java.awt.Color;
import java.io.IOException;

class CustomButton extends JButton{
  private JLabel label;
  private Font customHeader;
  
  //Constructor, no methods required
  CustomButton(String name, int type, CustomMouseListener listener){
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
    this.add(label);
    
    if(type == 0){
      //Transparent button
      this.setBorderPainted(false);
      this.setFont(new Font("sansserif", Font.BOLD, 20));
      //this.setAlignmentX(0);
      this.setLayout(new BorderLayout());
      this.add(label, BorderLayout.WEST);
    }
  }
}
