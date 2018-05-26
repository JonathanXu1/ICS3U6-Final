/////////////////////
import javax.swing.*;

/////////////////////
class CustomButton extends JButton{
  CustomButton(String name){
    super(name);
//Transparent button
    this.setContentAreaFilled(false);
    this.setBorderPainted(false);
  }
}
