import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    
    private class Node{
    	Item item;
    	Node next;
    	Node previous;
    }
    
	
	  private class DequeIterator implements Iterator<Item>{ 
		  public Node current;
	  
		  // initialize pointer to head of the list for iteration 
		  public DequeIterator(Node first) { 
			  this.current = first; 
			  }
		  
		  // returns false if next element does not exist 
		  public boolean hasNext() {
		      return current != null; 
		      } 
		  
		  // return current data and update pointer
		  public Item next() throws java.util.NoSuchElementException{
			  if(current == null) {
				  throw new java.util.NoSuchElementException("Deque is empty");
			  }
		      Item data = current.item;
		      current = current.next;
		      return data;
		    }
		  
		  // remove function - not supported
		  public void remove() throws UnsupportedOperationException{
			  throw new UnsupportedOperationException("Don't do this please");
		  }
		  }
	 
    
    // construct an empty deque
    public Deque() {
    	
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return first == null;
    }

    // return the number of items on the deque
    public int size() {
    	int count = 0;
    	Node current = first;
    	while(current.item != null) {
    		count++;
    		current = current.next;
    	}
    	return count;
    }

    // add the item to the front
    public void addFirst(Item item) throws IllegalArgumentException {
        if(item == null) {
        	throw new IllegalArgumentException("Cannot add null pointers");
        }
    	if(!isEmpty()) {
	    	Node oldFirst = first;
	    	first = new Node();
	    	first.item = item;
	    	first.next = oldFirst;
	    	oldFirst.previous = first;
        }
        else {
        	first = new Node();
        	first.item = item;
        	last = new Node();
        	last.item = item;
        }
    }

    // add the item to the back
    public void addLast(Item item) throws IllegalArgumentException {
    	if(item == null) {
        	throw new IllegalArgumentException("Cannot add null pointers");
        }
    	if(!isEmpty()) {
    		Node oldLast = last;
    	    last = new Node();
    	    last.item = item;
    	    last.previous = oldLast;
    	    oldLast.next = last;
    	}
    	else{
    		first = new Node();
        	first.item = item;
    		last = new Node();
    		last.item = item;
    		
    	}
    }

    // remove and return the item from the front
    public Item removeFirst() throws java.util.NoSuchElementException {
    	if(isEmpty()) {
    		throw new java.util.NoSuchElementException("Deque is empty");
    	}
    	Item item = first.item;
    	first = first.next;
    	first.previous = null;
    	if (isEmpty()) {
    		last = null;
    	}
    	return item;
    }

    // remove and return the item from the back
    public Item removeLast() throws java.util.NoSuchElementException {
    	if(isEmpty()) {
    		throw new java.util.NoSuchElementException("Deque is empty");
    	}
    	Item item = last.item;
    	last = last.previous;
    	last.next = null;
    	if (isEmpty()) {
    		last = null;
    	}
    	return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	DequeIterator myIterator = new DequeIterator(first);
    	return myIterator;
    }

    // unit testing (required)
    public static void main(String[] args) {
    	Deque<Integer> test = new Deque<Integer>();
    	test.addFirst(3);
    	test.addFirst(2);
    	test.addFirst(3);
    	test.addLast(4);
    	test.addLast(5);
    	Iterator<Integer> myIterator = test.iterator();	
    	System.out.println(myIterator.hasNext());
    	System.out.println(myIterator.next());
    	System.out.println(test.removeFirst());
    	System.out.println(test.removeLast());
    	System.out.println(test.removeLast());
    }
}