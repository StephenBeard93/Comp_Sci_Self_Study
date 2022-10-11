import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
	private int n;      //grid size
	private int trials; //number of trials
	private int[] totals;  //array of simulation results
	
	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	this.n = n;
    	this.trials = trials;
    	this.totals = new int [trials];
    }
    
    // sample mean of percolation threshold
    public double mean() {
    	double total = 0;
    	for(int i = 0; i < totals.length; i++) {
    		total += totals[i];
    	}
    	double mean = total/trials;
    	return mean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
    	double mean = mean();
    	double s = 0;
    	for(int i = 0; i < totals.length; i++) {
    		double temp = (totals[i] - mean);
    		temp = temp * temp;
    		s += temp;	
    	}
    	double std = s/(trials-1);
    	return std;
    }
    
   // low endpoint of 95% confidence interval
    public double confidenceLo() {	
    	double mean = mean();
    	double s    = stddev();
    	double trialsSQRT  = Math.sqrt(trials);
    	double confidenceLow = mean - (1.96*s/trialsSQRT);
    	return confidenceLow;
    }
    
   // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	double mean = mean();
    	double s    = stddev();
    	double trialsSQRT  = Math.sqrt(trials);
    	double confidenceHi = mean + (1.96*s/trialsSQRT);
    	return confidenceHi;
    }

    public static void main(String[] args) {
    	int x = 0;
    	int y = 0;
    	 try {
             // Parse the string argument into an integer value.
             x = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException nfe) {
             // The first argument isn't a valid integer.  Print
             // an error message, then exit with an error code.
             System.out.println("The first argument must be an integer.");
             System.exit(1);
         }
         try {
             // Parse the string argument into an integer value.
             y = Integer.parseInt(args[1]);
             }
         catch (NumberFormatException nfe) {
             // The second argument isn't a valid integer.  Print
             // an error message, then exit with an error code.
             System.out.println("The first argument must be an integer.");
             System.exit(1);  
         }
    	PercolationStats mcSim = new PercolationStats(x,y);
    	for(int i = 0; i < mcSim.trials; i++ ) {
    		mcSim.totals[i] = mcSim.simulation(mcSim.n);
    	}
    	double mean = mcSim.mean();
    	System.out.println(mean);
    	double std = mcSim.stddev();
    	System.out.println(std);
    	double conLow = mcSim.confidenceLo();
    	System.out.println(conLow);
    	double conHi = mcSim.confidenceHi();
    	System.out.println(conHi);
    	}
 
    
    private int simulation(int n) {
    	Percolation sim = new Percolation(n);
    	sim.dummyConnections();
    	while(!sim.percolates()) {
    		int rand1 = StdRandom.uniformInt(n);
    		int rand2 = StdRandom.uniformInt(n);
    		sim.open(rand1, rand2);
    		// does it percolate
    		if(sim.percolates()) {
    			int p = sim.numberOfOpenSites();
    			return p;
    		  }	
    	}
		return 0;
    }
    
}