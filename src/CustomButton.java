import javax.swing.*;

class CustomButton extends JButton{
  CustomButton(String name, int type){
    super(name);
    //Transparent button
    if (type==1){
      this.setOpaque(false);
      this.setContentAreaFilled(false);
      this.setBorderPainted(false);
    }
  }
}