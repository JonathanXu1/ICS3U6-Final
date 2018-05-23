import javax.swing.*;

class CustomButton extends JButton{
  CustomButton(String name){
    super(name);
    //Transparent button
//Uneeded     
  // this.setOpaque(false);
    this.setContentAreaFilled(false);
    this.setBorderPainted(false);
  }
}