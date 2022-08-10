import java.util.*;
import java.lang.Math;

/**
 * Program class for an SRPN calculator.
 */

public class SRPN {
  // fields required for SRPN class
  private ArrayList<Integer> stack = new ArrayList<>(); /* stack
  to hold numeric variables */
  private long long1; //holds string as a long after conversion
  private Integer num1; // last integer added to stack
  private String stringTemp; //holds digits 0-9
  private Integer result; /*holds result of any calcs between numbers
  called from the stack */
  private boolean commentFlag;// flag for if comment is in progress
  private int rCount; /* keeps track of how many times 'r' has been
  called */
  private ArrayList<Integer> rList = new ArrayList<>(Arrays.asList(1804289383,846930886,1681692777,1714636915,1957747793,424238335,719885386,1649760492,596516649,1189641421,1025202362,1350490027,783368690,1102520059,2044897763,1967513926,1365180540,1540383426,304089172,1303455736,35005211,521595368)); /* array list of r values */

  // constructor
  public SRPN(){  
    this.long1 = 0;
    this.num1 = 0;
    this.stringTemp = "";
    this.result = 0;
    this.commentFlag = false;
    this.rCount = 0;
  }
  

  // method called to impliment SRPN calculator
  public void processCommand(String s) {  
    for(int i = 0; i < s.length(); i++){
      // get current & next charcter from String
      char character = s.charAt(i);
      char charNext;
      // check if there is no next charcter due to string ending
      if(i+1 <= s.length()-1){
          charNext = s.charAt(i+1);
        }
      else{
          charNext = ' '; // treat end of string as empty space
      }
      // sets the commnet flag (true = comment being written)
      if("#".indexOf(character) > -1){
        commentFlag = !commentFlag;
      }
      // check if a comment is currently open
      if(!commentFlag){
        // if character is digit add to stringtemp
        if(Character.isDigit(character)){
          stringTemp += Character.toString(character);
        // add stringTemp to stack if next character is not a digit 
          if(!Character.isDigit(charNext)){
            long1 = Long.parseLong(stringTemp);
            num1 = checkSaturation(long1);
            if(isStackFull()){
              stringTemp = ""; // reset after adding to stack
            }
            else{
              stack.add(num1);
              stringTemp = "";  
            } 
          }
        }
        // check to see if next number is negative
        else if("-".indexOf(character) > -1 && Character.isDigit(charNext)){
          stringTemp += Character.toString(character);
        }
        // check to see if it's an operator
        else if("%/*+-^".indexOf(character) > -1){
          // check if stack has two elements or more
          if(isStack()){
            // perform operations, update stack and retrun result
            result = operatorCalc(character);
            }
        }
        // check to see if it's '='
        else if("=".indexOf(character) > -1){
          System.out.println(stack.get(stack.size()-1));
        }
        // check to see if it's a 'd'
        else if("d".indexOf(character) > -1){
          printStack();
        }
        // check to see if it's an 'r'
        else if("r".indexOf(character) > -1){
          rAdd();
        }
        // check to see if it's a '#'
        else if("#".indexOf(character) > -1){
        }
        // check to see if it's a ' '
        else if(" ".indexOf(character) > -1){
        } 
        // ignore other characters and add stringTemp to stack
        else{
          System.out.printf("Unrecognised operator or operand \"%c\"", character);
        }  
       }
    
    }  
    stringTemp = ""; 
  }
  
  // method for printing all the variables on the stack when 'd' is entered
  public void printStack(){
    if(stack.size() == 0){
      System.out.println("-2147483648");
    }
    else{
      for (int value : stack) {
        System.out.println(value);
      }
    }
  }

  // method for checking that the stack has more than two values
  public boolean isStack(){
    if(stack.size() >= 2){
      return true;
    }
    else{
      System.out.println("Stack underflow.");
      return false;
    }
  }

  // method for checking whether the stack is full
  public boolean isStackFull(){
    if(stack.size() < 23){
      return false;
    }
    else{
      System.out.println("Stack overflow.");
      return true;
    }
  }

  // method of checking for saturation
  public Integer checkSaturation(long x){
    Integer max = 2147483647;
    Integer min = -2147483648;
    int intx;
    Integer integerx;
    if(x>2147483647){
      return max;
    }
    else if(x<-2147483648){
      return min;
    }
    else{
      intx = (int)x;
      integerx = (Integer) intx;
      return integerx;
    }
  }
  // method for performing operator calculation "%/*+-"
  public Integer operatorCalc(char character){
    int index = stack.size() -1;
    long int1 = stack.get(index);
    long int2 = stack.get(index-1);
    long temp;
    Integer result;
    
    switch(character){
        case '+': temp = int2 + int1;
        result = checkSaturation(temp);
        stack.remove(index);
        stack.remove(index-1);
        stack.add(result);
        return result;
        case '-': temp = int2 - int1;
        result = checkSaturation(temp);
        stack.remove(index);
        stack.remove(index-1);
        stack.add(result);
        return result;
        case '*': temp = int2 * int1;
        result = checkSaturation(temp);
        stack.remove(index);
        stack.remove(index-1);
        stack.add(result);
        return result;
        case '/': 
        if(int1 != 0){
          temp = int2 / int1;
          result = checkSaturation(temp);
          stack.remove(index);
          stack.remove(index-1);
          stack.add(result);
        }
        else{System.out.println("Divide by 0.");
        }
        return this.result;
        case '%': temp = int2 % int1;
        result = checkSaturation(temp);
        stack.remove(index);
        stack.remove(index-1);
        stack.add(result);
        return result;
        case '^': temp = (long) Math.pow(int2, int1);
        result = checkSaturation(temp);
        stack.remove(index);
        stack.remove(index-1);
        stack.add(result);
        return result;
        
    }
    return 0;
  }

  // method for adding r to stack and printing r
  public void rAdd(){
    if(isStackFull()){
      // do nothing
    }
    else{
      stack.add(rList.get(rCount % 22));
      rCount += 1;
    }
  }
}
