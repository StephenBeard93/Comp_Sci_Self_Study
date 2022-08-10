// class board
public class Board{
  // fields
  private char[][] board;

  // constructor - make a board of any size
  public Board(int x, int y){
    board = new char[y][x];
  }

  // method to access board
  public char[][] getBoard(){
    return board;
  }

  // method to check that a token can be placed in the chosen column
  public boolean hasSpace(int position){
    //returns -1 if none of the char found in "ryb"
    if("ryb".indexOf(board[0][position-1]) == -1){
      return true;
    }
    else{
      return false;
    }
  }
  
  // method to place a token on the board
  public void placeCounter(char token, int position){
    for(int i=board.length-1; i>=0; i--){
      if("ryb".indexOf(board[i][position-1]) == -1){
        board[i][position-1] = token;
        break;
      }
    }
  } 

  // method for checking if a player has won the game
  public boolean hasWon(char token, int n){
    // check horizontally
    for(int i=0; i<board.length; i++){
      int count = 0;
      for(int j=0; j<board[i].length; j++){
        if(board[i][j]==token){
          count +=1;
        }
        else{
          count = 0;
        }
        if(count == n){
          return true;
        }
      }
    }
    // check vertically 
    for(int i=0; i<board[0].length; i++){
      int count = 0;
      for(int j=0; j<board.length; j++){
        if(board[j][i]==token){
          count +=1;
        }
        else{
          count = 0;
        }
        if(count == n){
          return true;
        }
      }
    }
    // check diagonaly left to right "\"
    int maxX = board[0].length-1;
    int maxY = board.length-1;
    int[] currentXY = {0,0};
    int[] nextXY = {0,0};

    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[i].length; j++){
        int dCount = 0;
        // check square equals token
        if(board[i][j] == token){
          dCount += 1;
        }
        else{
          continue;
        }
        // check if next diagonal square equals token
        boolean stillCounting = true;
        currentXY[0] = i;
        currentXY[1] = j;
        do{
          nextXY[0] = currentXY[0]+1;
          nextXY[1] = currentXY[1]+1;
          // method for checking next int [] is in range - continue if not
          if(nextXY[0]<=maxY && nextXY[1]<=maxX){
            // check if next diagonal equals token
            if(board[nextXY[0]][nextXY[1]]==token){
              dCount +=1;
              currentXY[0] = nextXY[0];
              currentXY[1] = nextXY[1];
              // check if dCount = n
              if(dCount==n){
                return true;
              }
            }
            else{
              stillCounting = false;
              continue;
            }
          }
          else{
            stillCounting = false;
            continue;
          }
        }while(stillCounting);  
      } 
    } 
    // check diagonaly left right "/"
    for(int i=0; i<board.length; i++){
      for(int j=0; j<board[i].length; j++){
        int dCount = 0;
        // check square equals token
        if(board[i][j] == token){
          dCount += 1;
        }
        else{
          continue;
        }
        // check if next diagonal square equals token
        boolean stillCounting = true;
        currentXY[0] = i;
        currentXY[1] = j;
        do{
          nextXY[0] = currentXY[0]-1;
          nextXY[1] = currentXY[1]+1;
          // method for checking next int [] is in range - continue if not
          if(nextXY[0]<=maxY && nextXY[0]>=0 && nextXY[1]<=maxX){
            if(board[nextXY[0]][nextXY[1]]==token){
              dCount +=1;
              currentXY[0] = nextXY[0];
              currentXY[1] = nextXY[1];
              // check if dCount = n
              if(dCount==n){
                return true;
              }
            }
            else{
              stillCounting = false;
              continue;
            }
          }
          else{
            stillCounting = false;
            continue;
          }
        }while(stillCounting);  
      } 
    } 
  return false;  
 } 
 
}