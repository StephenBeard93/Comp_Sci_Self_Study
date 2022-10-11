import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
	private int dummyTop = -1;
	private int dummyEnd;
	private int[] id;    // id[i] = component identifier of i
    private int count;   // number of components
	private int n;
	private char[] status; //array 'o' for open, 'b' for blocked
	
	public Percolation(int n) {
		this.n = n;
		this.dummyEnd = n*n;
		this.count = n*n;
        this.id = new int[count];
        this.status = new char[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
            status[i] = 'b';
        }
	}

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	int index = twoDto1D(row, col);
    	if(!isOpen(row, col)) {
    		status[index] = 'o';
    		checkNeighbours(index); //check surrounding sites and union if open
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	int index = twoDto1D(row, col);
    	if(id[index] == 'o') {
    		return true;
    	}
    	else {
    		return false; 
    	}
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	int index = twoDto1D(row, col);
    	if(id[index] == dummyTop) {
    		return true;
    	}
    	return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	int openSites = 0;
    	for (int i = 0; i < status.length; i++) {
    		if(status[i] == 'o') {
    			openSites += 1;
    		}
    	}
    	return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	for(int i = n*(n-1); i < n*n; i++) {
    		if(id[i] == dummyTop) {
    			return true;
    		}
    	}
        return false;
    }
    
    // convert 2D grid to 1D index 
    private int twoDto1D(int row, int col) {
    	int value = row * this.n + col;
    	return value;
    }
    
    // find the root ID 
    private int find(int p) {
        validate(p);
        return id[p];
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < -1 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }
    
    // union of two components 
    public void union(int p, int q) {
        int newID;
        int oldID;
    	validate(p);
        validate(q);
        int pID = id[p];   // needed for correctness
        int qID = id[q];   // to reduce the number of array accesses

        // p and q are already in the same component
        if (pID == qID) return;
        // make the smaller ID value the root
        if(pID > qID) {
        	newID = qID;
        	oldID = pID;
        }
        else {
        	newID = pID;
        	oldID = qID;
        }
        for (int i = 0; i < id.length; i++) {
            if (id[i] == oldID) id[i] = newID;
        }
        count--;
        
    }
    
    // connects top row to dummy top
    public void dummyConnections() {
    	for(int i =0; i < n; i++) {
    		id[i] = dummyTop;
    	}
    } 
    
    // check neighbours of a site and merge if both sites are open
    public void checkNeighbours(int index) {
    	// check 4 sites surrounding newly opened site - connect if open
    	int [] neighbours = new int[2];
    	int left  = index - 1;    // left
    	int right = index + 1;    // right
    	neighbours[0] = index - n;    // above
    	neighbours[1] = index + n;    // below
    	// check left
    	if(index % n != 0 && status[left] == 'o' ) {
    		union(left, index);
    	}
    	// check right
    	if(right % n != 0 && status[right] == 'o' ) {
    		union(right, index);
    	}
    	// check above and below
    	for(int i = 0; i < 2; i++) {
    		if(neighbours[i] < 0 || neighbours[i] >= n*n) {
    		// do nothing as out of bounds	
    		}
    		else {
    			  if(status[neighbours[i]] == 'o') {
    			    union(neighbours[i], index);
    		        }
    		     }
    	    }
        }

    
}