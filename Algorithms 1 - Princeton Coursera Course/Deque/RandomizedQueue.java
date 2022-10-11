import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Node first;
    private int N = 0;
    
    private class Node{
    	Item item;
    	Node next;
    	Node previous;
    }
    
    // read in linked list and copy items to a RandomizedQueue
    // remove items from random queue and construct new linked list
    private class RandomIterator implements Iterator<Item>{ 
		  private Node original;
		  private Node reordered;
		  private RandomizedQueue<Item> temp;
	  
		  // Constructor
		  public RandomIterator(Node first) { 
			  this.original = first; 
			  this.reordered = new Node();
			  this.temp =  new RandomizedQueue<Item>();
			  // reorder original list 
			  while(original != null) {
				  temp.enqueue(original.item);
				  original  = original.next;
			  }
			  reordered.item = temp.dequeue();
			  Node current = reordered;
			  Node next = new Node();
			  while(!temp.isEmpty()) {
			  next.item = temp.dequeue();
			  current.next = next;
			  current = current.next;
			  }
			  }
		  
		  // returns false if next element does not exist 
		  public boolean hasNext() {
		      return reordered != null; 
		      } 
		  
		  // return current data and update pointer
		  public Item next() throws java.util.NoSuchElementException{
			  if(reordered == null) {
				  throw new java.util.NoSuchElementException("Iterator is empty");
			  }
		      Item data = reordered.item;
		      reordered = reordered.next;
		      return data;
		    }
		  
		  // remove function - not supported
		  public void remove() throws UnsupportedOperationException{
			  throw new UnsupportedOperationException("Don't do this please");
		  }
		  }
	 
    
	// construct an empty randomized queue
    public RandomizedQueue() {	
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
    	int count = 0;
    	Node current = first;
    	while(current.item != null) {
    		count++;
    		current = current.next;
    	}
    	return count;
    }

    // add the item
    public void enqueue(Item item) {
    	if(item == null) {
        	throw new IllegalArgumentException("Cannot add null pointers");
        }
    	if(!isEmpty()) {
	    	Node oldFirst = first;
	    	first = new Node();
	    	first.item = item;
	    	first.next = oldFirst;
	    	oldFirst.previous = first; 
	    	 N++;
        }
        else {
        	first = new Node();
        	first.item = item;
        	N++;
        }
    }

    // remove and return a random item
    public Item dequeue() throws java.util.NoSuchElementException {
    	if(isEmpty()) {
    		throw new java.util.NoSuchElementException("Randomized Queue is empty");
    	}
    	// Case of just one node in list
    	if(N == 1) {
    	    Item value = first.item;
    	    first = null;
    	    return value;
    	}
    	int index = StdRandom.uniformInt(N);
    	Node current = first;
    	for(int i = 0; i < index; i++) {
    		current = current.next;
    	}
    	Item item   = current.item;
    	// case of item occurring before and after chosen index position in list
    	if(current.previous != null && current.next != null) {
    		Node before = current.previous;
    		Node after  = current.next;before.next = current.next; 
        	after.previous = current.previous;
        	current = null;
        	N--;
        	return item;
    	}
    	// case of no node in front of current
    	else if(current.previous == null){
    		Node after = current.next;
    		after.previous = null;
    		current = null;
        	N--;
        	return item;
    	}
    	// case of no node after current
    	else{
    		Node before = current.previous;
    		before.next = null;
    		current = null;
        	N--;
        	return item;
    	}
    	
    }

    // return a random item (but do not remove it)
    public Item sample() throws java.util.NoSuchElementException  {
    	if(isEmpty()) {
    		throw new java.util.NoSuchElementException("Randomized Queue is empty");
    	}
    	int index = StdRandom.uniformInt(N);
    	Node current = first;
    	for(int i = 0; i < index; i++) {
    		current = current.next;
    	}
    	Item item   = current.item;
    	return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
    	RandomIterator myIterator = new RandomIterator(first);
    	return myIterator;
    }
    
    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
    	System.out.println(test.isEmpty());
    	test.enqueue(1);
    	System.out.println(test.isEmpty());
    	test.enqueue(2);
    	Iterator<Integer> myIterator = test.iterator();
    	System.out.println(test.sample());
    	System.out.println(test.dequeue());
    	System.out.println(test.dequeue());
    }

}