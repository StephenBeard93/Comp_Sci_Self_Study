import java.util.*;

//class GameLogic
public class GameLogic{
  // fields
  private Board myBoard = new Board(7,6);
  private HumanPlayer p1 = new HumanPlayer("p1",'r',7);
  private RobotPlayer p2 = new RobotPlayer("p2",'y',7);
  ArrayList<Player> playerGo = new ArrayList<Player>();
  int n;
  private char[] gameTokens = {'r','y','b'};//red, yellow and blue symbols

  // constructor
  public GameLogic(int n){ 
    this.n = n;
  }

  // method for checkings which players turn it is 
  public Player whosGo(int turn){
    int players = playerGo.size();
    Player toReturn;
    if(turn < players){
      toReturn = playerGo.get(turn);
    }
    else{
      toReturn = playerGo.get(turn % players);
    }
    return toReturn;
  }

  // method for printing out instructions
  public void printInstructions(){
    System.out.println("Welcome to Connect 4");
		System.out.println("There are 2 players red and yellow");
		System.out.println("Player 1 is Red, Player 2 is Yellow");
		System.out.println("To play the game type in the number of the column you want to drop your counter in"); 
		System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
		System.out.println("");
  }

  // method for playing the game
  public void playGame(){
    int turn = 0;
    // add players to PlayerGo list
    playerGo.add(p1);
    playerGo.add(p2);
    // print board and instructions out at start of game
    printInstructions();
    Display.printBoard(myBoard.getBoard(),gameTokens);
    // keep asking for user input until game is won
    boolean win = false;
    do{
      int move;
      move = whosGo(turn).getUserInput();
      // check column is free
      if(myBoard.hasSpace(move)){
        myBoard.placeCounter(whosGo(turn).token, move);
        Display.printBoard(myBoard.getBoard(),gameTokens);
        if(myBoard.hasWon(whosGo(turn).token,n)){
          System.out.println("We have a winner!");
          win = true;
        }
        turn += 1;
      }
      else{
        System.out.println("Column is full, try anoter one!");
      }
      
      }while(!win);
  }
}