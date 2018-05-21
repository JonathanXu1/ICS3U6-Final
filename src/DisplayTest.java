class DisplayTest{
  public static void main (String[] args){
    Display disp = new Display ();
    disp.menu();
    //Delay to test out transition; will delete later
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    disp.start();
  }
}