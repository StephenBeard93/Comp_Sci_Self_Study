import java.io.*;

// HumanPlayer class
public class HumanPlayer extends Player{
  // fields
  private BufferedReader input;
  
  // constructor 
  public HumanPlayer(String name, char token, int max){
    super(name, token, max);
    input = new BufferedReader(new InputStreamReader(System.in));
  }

  // method for getting a move
  public int getUserInput(){
    String userInput;
    int move;
    boolean flag = true;
    // while loop for reading string in correctly
    do{
      System.out.println("To place a token enter a column number:");
      try{
        userInput = input.readLine();
      }
      catch(IOException e){
        System.out.println("Please enter a valid integer between 1 and " + max + " - try again!"); 
        continue;
      }
    
      // check that UserInput is an int 
      boolean isStringInt = userInput.matches("[0-9]+");
      if(isStringInt){
        // convert string to int
        move = Integer.parseInt(userInput);
        // check that number is within column range
        if(move > 0 && move <= max){
        flag = false;
        return move;  
        }  
        else{
          System.out.println("Please enter a valid integer between 1 and " + max + " - try again!");
        }
      }
      else{
       System.out.println("Please enter a valid integer between 1 and " + max + " - try again!"); 
      }
    }while(flag);
  return 0;
  }
} 