import javax.swing.*;
import java.awt.*;

class Background{
  private static int x = 200;
  private static int y  =200;
  private static int xDirection = 0;
  private static int yDirection  =0;
  private static boolean onTile = true;
  
  Background(){
  }
  public int getXDirection(){
    return (xDirection);
  }
  public void setXDirection(int xDirection){
    this.xDirection = xDirection;
  }
  
  public int getYDirection(){
    return (yDirection);
  }
  public void setYDirection(int yDirection){
    this.yDirection = yDirection;
  }
  
  public void move(){
    x=x + xDirection;
    y=y + yDirection;
  }
  public int getX(){
    return (x);
  }
  public  int getY(){  
    return (y);
  }
  public boolean getOnTile(){
    return (onTile);
  }
  public void setOnTile (){
    if ((x%100==0)&&(y%100==0)){
      this.onTile =true;
    }else{
      this.onTile =false;
    }
  }
}