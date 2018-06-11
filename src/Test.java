class Test{
  public static void main (String[]args){
    double tier2 = 0;
    for(int i=0;i<10000;i++){
     if (((5-(int)(Math.sqrt(((int)(Math.random()*16))))))==5){
       tier2++;
     }
    }
    System.out.print (tier2/10000.0*100.0);
  }
}