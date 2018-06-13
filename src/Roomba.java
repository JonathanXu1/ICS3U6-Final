import java.awt.Color;

abstract class Roomba extends Enemy{
  Roomba(int h,int hC,int a,int sP,boolean freezeStatus,boolean lightningStatus,boolean flameStatus, Color minimapColor, boolean enraged){
    super (h,hC,a,sP,freezeStatus,lightningStatus,flameStatus, minimapColor,enraged);
  }
}