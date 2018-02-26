/* By: Megan Respicio
   Date: October 2017
   Github: github.com/mrespicio */

package data_structures;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E>{
	public static final int DEFAULT_MAX_CAPACITY = 100;
	private class Node<T>{
		T data;		
		Node<T> next, prev;
		public Node(T obj){
			data = obj;
			next = null;
			prev = null; 
		}
	}	
	private Node<E> head, tail; 
	
	public void LinkedList(){
		head = tail = null;
	}    
	private int currentSize; 
	private long modCounter; 

    public boolean addFirst(E obj){
    	Node<E> newNode = new Node<E>(obj);
    	if(head == null) 
    		head = tail = newNode; 
    	else{
    		newNode.next = head; 
    		head.prev = newNode; 
    		head = newNode; 	 
    	}
    	currentSize++;
    	modCounter++; 
    	return true;
    }
    public boolean addLast(E obj){
    	Node<E> newNode = new Node<E>(obj);
    	if(head == null)
    		head = tail = newNode;
    	else{
    		newNode.prev = tail; 
    		tail.next = newNode;
    		tail = newNode; 			
    		}
    	currentSize++; 
    	modCounter++;
    	return true;
    }
    public E removeFirst(){
    	if(head == null)
    		return null;
    	E tmp = head.data;
    	head = head.next; 
    	if(head == null)
    		tail = null;
    	else
        	head.prev = null;
    	currentSize--;
    	modCounter++;
    	return tmp;
    }
    public E removeLast(){
    	if(tail==null) return null;
    	E tmp = tail.data;
    	tail = tail.prev;
    	if(tail==null) head=null; 
    	else tail.next=null;
    	currentSize--;
    	modCounter++;
    	return tmp;
    }
    public E remove(E obj){
    	Node<E> current = head;
    	while(current != null && obj.compareTo(current.data) != 0 )
    		current = current.next;
    	if(current == null)
    		return null;
    	if(current == head)
    		return removeFirst();
    	if(current == tail)
    		return removeLast();
    	current.prev.next = current.next;
    	current.next.prev = current.prev;
    	currentSize--;
    	modCounter++;
    	return current.data;
    }  
    public E peekFirst(){
    	if(head == null)
    		return null;
    	return head.data;
    }
    public E peekLast(){
    	if(tail == null)
    		return null;
    	return tail.data;
    }
    public boolean contains(E obj){
    	return find(obj) != null;    	
    }
    public E find(E obj){
    	for(E tmp:this)
    		if(obj.compareTo(tmp)==0)
    			return tmp;  
    	return null;
    }
    public void clear(){
    	head = tail = null;
    	currentSize = 0; 
    	modCounter++;
    }
    public boolean isEmpty(){
    	return currentSize == 0;
    	}
    public boolean isFull(){
    	return false; 
    }
    public int size(){
    	return currentSize;
    }    
    class IteratorHelper implements Iterator<E>{
    	private Node<E> iterPtr;
    	long modCheck;
    	
    	public IteratorHelper(){
    		modCheck = modCounter; 
    		iterPtr = head;
    	}
    	public boolean hasNext(){
    		if(modCheck != modCounter)
    			throw new ConcurrentModificationException();
    		return iterPtr != null;
    	}
    	public E next(){
    		if(!hasNext())
    			throw new NoSuchElementException();
    		E tmp = iterPtr.data;
    		iterPtr = iterPtr.next;
    		return tmp;
    	}
    	public void remove(){
    		throw new UnsupportedOperationException();
    	} 
    }
	public Iterator<E> iterator() {
		return new IteratorHelper();
	} 
}
