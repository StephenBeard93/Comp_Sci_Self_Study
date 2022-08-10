// class Display 
public class Display{
  
  // method to print out a board
  public static void printBoard(char[][] board,  char[] playerTokens){
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[i].length; j++){
        // check if any of the token symbols are in the current square
        if("ryb".indexOf(board[i][j]) != -1){
          System.out.print("| " + board[i][j] + " ");
        }
        else{
          System.out.print("|   ");
        }
      }
      System.out.println("|");
    }
    System.out.println("  1   2   3   4   5   6   7");
  }
}