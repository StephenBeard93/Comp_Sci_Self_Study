import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	private MinPQ<Board> minPQ;

    private class Node{
    	public Board board;
    	public int moves;
    	public Node previous;
    	
    	public Node(Board board, int moves) {
    		this.board = board;
    		this.moves = moves;
    	}
    
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
    	BoardComparator boardComparator = new BoardComparator();
    	this.minPQ = new MinPQ<Board>(boardComparator);
    	minPQ.insert(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
    }

    // test client (see below) 
    public static void main(String[] args){
    }

}