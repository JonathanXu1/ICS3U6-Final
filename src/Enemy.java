import java.awt.Color;

abstract class Enemy extends Entity{
  
  private boolean enraged = false;
  private boolean seen = false;
  
  //Constructor
  Enemy(int h,int hC,int a,int sP,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor, boolean enraged){
    super (h,hC,a,sP,freezeStatus,lightningStatus,flameStatus, minimapColor);
    this.enraged = enraged;
  }
  
  //Getters and setters
  public void setEnraged (boolean enraged){
    this.enraged = enraged;
  }
  public boolean getEnraged (){
    return enraged;
  }
  public boolean getSeen(){
    return (seen);
  }
  public void setSeen(boolean seen){
    this.seen=seen;
  }
}