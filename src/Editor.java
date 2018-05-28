/**
 * {Editor.java}
 * This program will read a java file and edit it
 * IMPORTANT: This program does not yet work with abstract
 * 5/14/2018
 * Will Jeong
 */
/////////////////////
/////////////////////
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
/////////////////////
/////////////////////
class Editor{
/////////////////////
  /**
   *main 
   *
   *@param: The String[]args
   *@return: void
   */
  public static void main (String[]args) throws Exception{
    String [] fileNames = {"CustomButton.java","CustomKeyListener.java","DebugPanel.java","Display.java","DisplayTest.java","GamePanel.java","MenuBGPanel.java","MenuPanel.java","StartListener.java"};
    for (int i=0;i<fileNames.length;i++){
      String currentLine="";
      String previousLine;
      String tempString;
      String fileName = fileNames[i];
      File edit= new File (fileName);
      Scanner input = new Scanner (edit);
      File create= new File ("Edited"+fileName);
      PrintWriter output = new PrintWriter (create);
      String title;
      String paramStr= "";
      String returnStr;
      int count = 0;
      boolean workWithArrays = false;
      String firstLetter;
      while(input.hasNext()){
        previousLine = currentLine;
        currentLine = input.nextLine();
        currentLine= removeIndent (currentLine);
        if(currentLine.length()>=2){
          if((currentLine.substring(0,2).equals("/*"))){
            currentLine = "/**";
          }
          if(currentLine.length()>=2){
            if(((currentLine.substring(0,2)).equals("if"))){
              currentLine= eliminateSpace (currentLine);
              currentLine = createBrackets (currentLine);
            }
            if(currentLine.length()>=5){
              if((currentLine.substring(0,5)).equals("while")){
                currentLine= eliminateSpace (currentLine);
                currentLine = createBrackets (currentLine);
              }else if ((currentLine.substring(0,5)).equals("class")){
                output.println ("/////////////////////");
              }
              if(currentLine.length()>=7){
                if((currentLine.substring(0,6)).equals("import")){
                  if(previousLine.length()>=6){
                    if(!(previousLine.substring(0,6)).equals("import")){
                      output.println ("/////////////////////");
                    }
                  }else{
                    output.println ("/////////////////////");
                  }
                }else if (((currentLine.substring (0,6)).equals("public"))||((currentLine.substring (0,7)).equals("}public"))){
                  if (((currentLine.substring (0,7)).equals("}public"))){
                    output.println ("}");
                    currentLine = currentLine.substring (1);
                  }
                  tempString = currentLine;
                  if (tempString.indexOf("static")!=-1){
                    tempString = tempString.substring (14);
                  }else{
                    tempString = tempString.substring (tempString.indexOf("public")+7);
                  }
                  if ((tempString.indexOf ("]")!=-1)&&((tempString.indexOf ("]")<(tempString.indexOf ("("))))){
                    tempString = eliminateSpace (tempString);
                    workWithArrays = true;
                  }else{
                    workWithArrays = false;
                  }
                  if (workWithArrays){
                    if ((tempString.substring (tempString.indexOf ("]")+2 , tempString.indexOf ("]")+3)).equals("]")){
                      returnStr = tempString.substring(0, tempString.indexOf ("]")+3);
                      tempString =  tempString.substring(tempString.indexOf ("]")+3);
                    }else{
                      returnStr = tempString.substring(0, tempString.indexOf ("]")+1);
                      tempString =  tempString.substring(tempString.indexOf ("]")+1);
                    }
//Should be something like this:String fileName)
                  }else{
                    returnStr = tempString.substring(0, tempString.indexOf(" "));
                    tempString = tempString.substring(tempString.indexOf(" ")+1);
                  }
                  title  = tempString.substring (0, tempString.indexOf("("));
                  tempString  = tempString.substring (tempString.indexOf("(")+1);
                  tempString = eliminateSpace (tempString);
                  count = 0;
                  for (int j=0;j<tempString.length();j++){
                    if ((tempString.substring (j,j+1)).equals(",")){
                      count++;
                    }
                  }
                  String [] paramArray = new String [count+1];
                  if (paramArray.length!=1){
                    paramStr = "";
                    for (int j=0;j<paramArray.length;j++){
                      paramArray[j]=null;
                    }
                    for (int j=0;j<paramArray.length;j++){
                      if (j==paramArray.length-1){
                        paramArray[j]= tempString.substring(0,tempString.indexOf(")"));
                      }else{
                        paramArray[j]=tempString.substring(0,tempString.indexOf(","));
                        tempString= tempString.substring(tempString.indexOf(",")+1);
                      }
                    }
                    for (int j=0;j<paramArray.length;j++){
                      if ((paramArray[j].substring (0,1).equals(" "))){
                        paramArray[j]=paramArray[j].substring(1);
                      }
                    }
                    for (int j=0;j<paramArray.length;j++){
                      if (paramArray.length!=1){
                        if (j==0){
                          paramStr = paramStr +"The "+  paramArray[j]+", ";
                        }else if (j!=paramArray.length-1){
                          paramStr = paramStr +"the "+  paramArray[j]+", ";
                        }else{
                          paramStr = paramStr +"and the "+  paramArray[j];
                        }
                      }else{
                        paramStr = "The "+  paramArray[j];
                      }
                    }
                  }else{
                    paramStr = tempString.substring(0,tempString.indexOf(")"));;
                  }
                  output.println ("/**");
                  output.println ("*"+title);
                  output.println ("*");
                  output.println ("*"+"@param: "+paramStr);
                  if (returnStr.indexOf("void")!=-1){
                    output.println ("*"+"@return: ");
                  }else{
                    firstLetter  = ((returnStr.substring(0,1)).toLowerCase());
                    if (firstLetter.equals("a")||firstLetter.equals("e")||firstLetter.equals("i")||firstLetter.equals("o")||firstLetter.equals("u")){
                      output.println ("*"+"@return: "+"An "+returnStr);
                    }else{
                      output.println ("*"+"@return: "+"A "+returnStr);
                    }
                  }
                  output.println ("*/");
                }
              }
            }
          }
        }
        output.println (currentLine);
      }
      input.close();
      output.close();
    }
  }
  
/////////////////////
  /**
   *eliminateSpace 
   *
   *@param: The String currentLine
   *@return: String
   */
  public static String eliminateSpace (String currentLine){
    String newString="";
    for (int i=0;i<currentLine.length();i++){
      if(!(currentLine.substring(i,i+1).equals(""))){
        newString = newString + currentLine.substring(i, i+1);
      }
    }
    return (newString);
  }
/////////////////////
  /**
   *runDown 
   *
   *@param: The String currentLine, and the int highest
   *@return: int
   */
  public static int runDown (String currentLine, int highest){
    boolean bracketFound=false;
    while(!(bracketFound)){
      if(currentLine.substring(highest-1,highest).equals("(")){
        bracketFound=true;
      }else{
        highest = highest-1;
      }
    }
    return (highest);
  }
/////////////////////
  /**
   *runUp
   *
   *@param: The String currentLine, and the int lowest
   *@return: int
   */
  public static int runUp(String currentLine, int lowest){
    boolean bracketFound=false;
    while(!(bracketFound)){
      if(currentLine.substring(lowest,lowest+1).equals(")")){
        bracketFound=true;
      }else{
        lowest = lowest+1;
      }
    }
    return (lowest);
  }
  /**
   *createBrackets
   *
   *@param: The String currentLine
   *@return: String
   */
  public static String createBrackets(String currentLine){
    int [] placeOpen= new int [currentLine.length()];
    int [] placeClose= new int [currentLine.length()];
    int bracketIndex = 0;
    boolean inAString = false;
    for(int i =0;i<placeOpen.length;i++){
      placeOpen [i]=-1;
      placeClose [i]=-1;
    }
    for (int i =0;i<currentLine.length();i++){
      if(currentLine.substring(i,i+1).equals("\"")){
        if(inAString){
          inAString = false;
        }else{
          inAString = true;
        }
      }
      if(!(inAString)){
        if((currentLine.substring(i,i+1).equals("&"))||(currentLine.substring(i,i+1).equals("|"))){
          if((currentLine.substring(i+1,i+2).equals("&"))||(currentLine.substring(i+1,i+2).equals("|"))){
            if(!(currentLine.substring(i-1,i).equals(")"))){
              placeClose[bracketIndex]=i-1;
              placeOpen[bracketIndex]=runDown (currentLine, i-1);
              currentLine = currentLine.substring (0,placeOpen[bracketIndex])+"("+currentLine.substring (placeOpen[bracketIndex],placeClose[bracketIndex]+1)+")"+currentLine.substring (placeClose[bracketIndex]+1);
              bracketIndex++;
            }
          }else{
            if(!(currentLine.substring(i+1,i+2).equals("("))){
              placeOpen[bracketIndex]=i+1;
              placeClose[bracketIndex]=runUp (currentLine, i+1);
              currentLine = currentLine.substring (0,placeOpen[bracketIndex])+"("+currentLine.substring (placeOpen[bracketIndex],placeClose[bracketIndex]+1)+")"+currentLine.substring (placeClose[bracketIndex]+1);
              bracketIndex++;
            }
          }
        }
      }
    }
    return (currentLine);
  }
/////////////////////
  /**
   *removeIndent 
   *
   *@param: The String currentLine
   *@return: String
   */
  public static String removeIndent (String currentLine){
    for (int i=0;i<currentLine.length();i++){
      if(currentLine.substring(0,1).equals(" ")){
        currentLine = currentLine.substring (1);
      }
    }
    return (currentLine);
  }
}
