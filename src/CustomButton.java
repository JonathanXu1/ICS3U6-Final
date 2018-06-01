import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JLabel;

class CustomButton extends JButton{
  private JLabel name;
  //Constructor, no methods required
  CustomButton(String name, int type){
    this.name = new JLabel(name);
    if(type == 0){
      //Transparent button
      this.setBorderPainted(false);
      this.setFont(new Font("sansserif", Font.BOLD, 20));
      //this.setAlignmentX(0);
      this.setLayout(new BorderLayout());
      this.add(this.name, BorderLayout.WEST);
    }
  }
}
