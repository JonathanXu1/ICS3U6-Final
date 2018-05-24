class Time{
  private long time;
  Time(){
    this.time = System.nanoTime();
  }
  public long getTime(){
    return time;
  }
  public void setTime(){
    this.time=System.nanoTime();
  }
}