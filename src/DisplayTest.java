class DisplayTest{
  public static void main (String[] args) throws Exception{
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