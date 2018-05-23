class DisplayTest{
  public static void main (String[] args){
    Display disp = new Display ();
    disp.menu();
    while(!(disp.getListen())){
    }
    disp.start();
    while (true){
      disp.refreshAll();
    }
  }
}