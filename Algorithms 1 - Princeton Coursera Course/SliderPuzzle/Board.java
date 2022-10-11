import java.util.Comparator;
import java.util.Iterator;
import edu.princeton.cs.algs4.Queue;

public class Board {
    public int[][] board;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	this.board = tiles;
    }
    
    // Pair class, pair of two numbers
    private class Pair {
		public int a;
		public int b;
		
		Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}
    
    private class PairIterator implements Iterator<Pair>{
    	Queue<Pair> stack = new Queue<Pair>();

		public PairIterator() {
		    for(int i = 0; i < board.length; i++) {
		    	for(int j = 0; j < board.length; j++) {
		    		// find zero and add any pairs it's touching with to the stack
		    		if(board[i][j] == 0) {
		    			// left
		    			if(j != 0) {
		    				int zeroPosition = i * dimension() + j;
		    				int leftPosition = i * dimension() + j - 1;
		    				Pair left = new Pair(zeroPosition, leftPosition);
		    				stack.enqueue(left);
		    			}
		    			// right
		    			if(j != 2) {
		    				int zeroPosition = i * dimension() + j;
		    				int rightPosition = i * dimension() + j + 1;
		    				Pair right = new Pair(zeroPosition, rightPosition);
		    				stack.enqueue(right);
		    			}
		    			// up 
		    			if(i != 0) {
		    				int zeroPosition = i * dimension() + j;
		    				int upPosition = (i-1) * dimension() + j;
		    				Pair up = new Pair(zeroPosition, upPosition);
		    				stack.enqueue(up);
		    			}
		    			// down
		    			if(i != 2) {
		    				int zeroPosition = i * dimension() + j;
		    				int downPosition = (i+1) * dimension() + j;
		    				Pair down = new Pair(zeroPosition, downPosition);
		    				stack.enqueue(down);
		    			}
		    		}
		    	}
		    }
		}
		
    	@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public Pair next() {
			return stack.dequeue();
		}}
                                           
    // string representation of this board
    public String toString() {
    	String value = "";
    	value += board.length;
    	for(int i = 0; i < board.length; i++) {
    		value += "\n";
    		for(int j = 0; j < board.length; j++) {
    			value += board[i][j];
    			value += " ";
    		}
    	}
    	return value;
    }

    // board dimension n
    public int dimension() {
    	int value = board.length;
    	return value;
    }

    // number of tiles out of place
    public int hamming() {
    	int count = 0;
    	for(int i = 0; i < board.length; i++) {
    		for(int j = 0; j < board.length; j++) {
    			if(board[i][j] == 0) {
    				continue;
    			}
    			if(board[i][j] != (i * board.length) + j + 1) {
    				count++;
    			}
    		}
    	}
    	return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int total = 0;
    	for(int i = 0; i < board.length; i++) {
    		for(int j = 0; j < board.length; j++) {
    			int value = board[i][j];
    			// skip if value is zero or tile is in goal position
    			if(value == 0 || value == board.length * i + j + 1) {
    				continue;
    			}
    			// find index positions of tiles goal position
    			int row = value / dimension();
    			int col;
    			if(value % dimension() == 0) {
    				row--;
    				col = dimension() - 1;
    			}
    			else {
    				col = value % dimension() -1;
    			}
    			// calculate manhattan distance	
    			int distance = Math.abs((row - i)) + Math.abs((col - j));
    			total += distance;
    		}
    	}
    	return total;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	for(int i = 0; i < board.length; i++) {
    		for(int j = 0; j < board.length; j++) {
    			if(i == board.length -1 && j == board.length -1) {
    				if(board[i][j] != 0) {
    					return false;
    				}
    				else {
    					continue;
    				}
    			}
    			if(board[i][j] != (i * board.length) + j +1) {
    				return false;
    			}
        }
      }
    	return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
    	Board a = (Board) y;  //typecast object 
    	//check if boards are equal
    	if(a.dimension() != dimension()) {
    		return false;
    	}
    	//check that all tiles match
    	for(int i = 0; i < board.length; i++) {
    		for(int j = 0; j < board.length; j++) {
    			if(a.board[i][j] != board[i][j]) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

    // all neighboring board
    public Queue<Board> neighbors(){
    	Queue<Board> stack = new Queue<Board>();
    	PairIterator Pairs = new PairIterator();
    	while(Pairs.hasNext()) {
    		Pair temp = Pairs.next();
    		int[][] arraySize = new int[dimension()][dimension()];
    		for(int i = 0; i < dimension(); i++) {
    			for(int j = 0; j < dimension(); j++) {
    				arraySize[i][j] = board[i][j];
    			}
    		}
    		// swap elements 
    		int aX = temp.a/dimension();
    		int aY = temp.a%dimension();
    		int bX = temp.b/dimension();
    		int bY = temp.b%dimension();
    		int holding = arraySize[aX][aY];
    		arraySize[aX][aY] = arraySize[bX][bY];
    		arraySize[bX][bY] = holding;
    		Board neighbour = new Board(arraySize);
    		stack.enqueue(neighbour);
    	}
    	return stack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	Queue<Board> neighbors = neighbors();
    	Board oneMove = neighbors.dequeue();
    	return oneMove;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    	int[][] myArray = {{1,2,3},{4,5,6},{7,8,0}};
    	Board myBoard = new Board(myArray);
    	System.out.println(myBoard.toString());
    	//System.out.println(myBoard.hamming());
    	//System.out.println(myBoard.manhattan());
    	//System.out.println(myBoard.isGoal());
    	int[][] myArray2 = {{2,1,3},{4,5,6},{7,8,0}};
    	//Board myBoard2 = new Board(myArray2);
    	//System.out.println(myBoard.equals(myBoard2));
    	Board oneMove = myBoard.twin();
    	System.out.println(oneMove.toString());
    }

}