package DateOrganizer;

import java.util.*;

/**
 * This class describes a priority min-queue that uses an array-list-based min binary heap 
 * that implements the PQueueAPI interface. The array holds objects that implement the 
 * parameterized Comparable interface.
 * @author Dr. Duncan, Ethan Elmer
 * @param <E> the priority queue element type. 
 * @since 09-25-2023
 */
public class PQueue<E extends Comparable<E>> implements PQueueAPI<E>
{    
   /**
    * A complete tree stored in an array list representing the 
    * binary heap
    */
   private ArrayList<E> tree;
   /**
    * A comparator lambda function that compares two elements of this
    * heap when rebuilding it; cmp.compare(x,y) gives 1. negative when x less than y
    * 2. positive when x greater than y 3. 0 when x equal y
    */   
   private Comparator<? super E> cmp;
   
   /**
    * Constructs an empty PQueue using the compareTo method of its data type as the 
	* comparator
    */
   public PQueue(){
	  //Implement this method 
	  tree = new ArrayList<E>();
	  cmp = (object1, object2) -> object1.compareTo(object2);
   }
   
   /**
    * A parameterized constructor that uses an externally defined comparator    
    * @param fn - a trichotomous integer value comparator function   
    */
   public PQueue(Comparator<? super E> fn){
	 //Implement this method
       tree = new ArrayList<E>();
       cmp = fn;
   }

   public boolean isEmpty(){
	 //Implement this method
     if(tree.size() < 1) {
    	 return true;
     }
     else {
    	 return false;
     }
   }

	public void insert(E obj) {
		//Implement this method
	    tree.add(obj);
	    int newObjectIndex = tree.size() - 1;
	    while (newObjectIndex > 0) {
	        int parentIndex = (newObjectIndex - 1)/2;
	        int newObjectCompare = cmp.compare(tree.get(newObjectIndex), tree.get(parentIndex));
	        if (newObjectCompare < 0) {
	            swap(newObjectIndex, parentIndex);
	            newObjectIndex = parentIndex;
	        } 
	        else {
	            break;
	        }
	    }
	}
   
   public E remove() throws PQueueException {
	//Implement this method
    if (tree.isEmpty()) {
        throw new PQueueException("Priority queue is empty");
    } 
    else {
        E removeElement = tree.get(0);
        int endingIndex = tree.size()-1;
        E lastElement = tree.remove(endingIndex);
        if(tree.isEmpty()) {
        	return removeElement;
        }
        tree.set(0, lastElement);
        rebuild(0);
        return removeElement;
   }
  }
 
   public E peek() throws PQueueException{
	   //Implement this method
       if(tree.isEmpty()){
        throw new PQueueException("Priority queue is empty.");
       }
      else {
    	  return tree.get(0);
      }
   }

   public int size(){
      //Implement this method
      return tree.size();
   }
   
   
   /**
    * Swaps a parent and child elements of this heap at the specified indices
    * @param place an index of the child element on this heap
    * @param parent an index of the parent element on this heap
    */
   private void swap(int place, int parent){
      //Implement this method
      E childObject = tree.get(place);
      E parentObject = tree.get(parent);
      tree.set(place, parentObject);
      tree.set(parent, childObject);
   }

   /**
    * Rebuilds the heap to ensure that the heap property of the tree is preserved.
    * @param root the root index of the subtree to be rebuilt
    */
    private void rebuild(int root) {
      //Implement this method
      int leftChild = (2*root)+1; 
      int rightChild = (2*root)+2; 
      int rootValue = root; 
      
      if (leftChild<tree.size()) {
    	  //leftChildSmallerCheck = -1 if leftChild < rootValue, 0 if leftChild = rootValue, and 1 if leftChild > rootValue
    	  int leftChildSmallerCheck = cmp.compare(tree.get(leftChild), tree.get(rootValue));
    	  if(leftChildSmallerCheck < 0) {
              rootValue = leftChild; 
    	  }
      }
      if (rightChild<tree.size()) {
       	  //rightChildSmallerCheck = -1 if rightChild < rootValue, 0 if rightChild = rootValue, and 1 if rightChild > rootValue
    	  int rightChildSmallerCheck = cmp.compare(tree.get(rightChild), tree.get(rootValue));
    	  if(rightChildSmallerCheck < 0) {
              rootValue = rightChild; 
    	  }
      }
      if (rootValue != root) {
          swap(root, rootValue);
          rebuild(rootValue); 
      }
   }
}