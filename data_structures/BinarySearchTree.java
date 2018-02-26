/* By: Megan Respicio
   Date: December 2017
   Github: github.com/mrespicio */

package data_structures;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree <K extends Comparable<K>, V> implements DictionaryADT<K,V> {
	private class Node<K,V>{
		private K key;
		private V value;
		private Node<K,V> leftChild;
		private Node<K,V> rightChild;
		public Node(K key, V value){
			this.key = key;
			this.value = value;
			leftChild = rightChild = null;
		}
	}
	private Node<K,V> root;
	private int currentSize, modCounter;
	public BinarySearchTree(){
		root = null;
		currentSize=0;
		modCounter=0;
	}
	public boolean contains(K key) {
		if(isEmpty()) return false;
		return (findValue(key,null)!=null); 
	} 
	private void insert(K key, V value, Node<K,V> n, Node<K,V> parent, boolean wasLeft){
		if(n==null){
			if(wasLeft) parent.leftChild = new Node<K,V>(key, value);
			else parent.rightChild = new Node<K,V>(key, value);
		}
		else if (((Comparable<K>)key).compareTo((K)n.key) < 0)
			insert(key, value, n.leftChild, n, true);
		else insert(key, value, n.rightChild, n, false);
	}
	public boolean add(K key, V value) {
		if(contains(key)) return false;
		if(isEmpty()) root = new Node<K,V>(key,value);
		else insert(key,value,root,null,false);
		currentSize++;
		modCounter++;
		return true;
	}
	private Node<K,V> getSuccessor(Node<K,V> n){	
		Node<K,V> tmpParent=null;
		Node<K,V> tmp=n.rightChild;
		while(tmp.leftChild!=null){
			tmpParent=tmp;
			tmp=tmp.leftChild;
		}
		if(tmpParent==null) return null;
		else tmpParent.leftChild=tmp.rightChild;
		return tmpParent;
	}	
	private boolean deleteHelper(K key, Node<K,V> n, Node<K,V> parent, boolean wentLeft){
		if(n==null) return false;
		if(((Comparable<K>)key).compareTo(n.key)==0){ //found
			if(n.leftChild==null && n.rightChild==null){ //no child
				if(parent==null) root=null;				
				else if(wentLeft) parent.leftChild=null;
				else parent.rightChild=null;
			}
			else if(n.leftChild==null){	//one right child
				if(parent==null) root=n.rightChild;	
				else if(wentLeft) parent.leftChild=n.rightChild;
				else parent.rightChild=n.rightChild;
			}
			else if(n.rightChild==null){ //one left child
				if(parent==null) root=n.leftChild;
				else if(wentLeft) parent.leftChild=n.leftChild;
				else parent.rightChild=n.leftChild;
			}
			else{ //both child
				Node<K,V> successorParent = getSuccessor(n);
				if(successorParent==null){
					n.key=n.rightChild.key;
					n.value=n.rightChild.value;
					n.rightChild=n.rightChild.rightChild;
				}
				else{
					n.key=successorParent.key;
					n.value=successorParent.value;
				}
			} 
		} 
		else if(((Comparable<K>)key).compareTo(n.key) < 0) 
			deleteHelper(key, n.leftChild, n, true);
		else 
			deleteHelper(key, n.rightChild, n, false);
		return true;
	}
	public boolean delete(K key) {
		if(isEmpty()) return false; 
		if(!deleteHelper(key,root,null,false)) return false;
		modCounter++;
		currentSize--;
		return true;
	} 
	private V findValue(K key, Node<K,V> n){
		if(n==null) return null;
		if(((Comparable<K>)key).compareTo(n.key) < 0)
			return findValue(key, n.leftChild);
		if(((Comparable<K>)key).compareTo(n.key) > 0)
			return findValue(key, n.rightChild);
		return (V) n.value;
	}
	public V getValue(K key) {
		if(isEmpty()) return null;
		return findValue(key, root);
	}
	K keyFound;
	private void findKey(Node<K,V> n, V value){
		if(n==null) return; 
		if(((Comparable<V>)value).compareTo(n.value)==0){
			keyFound= n.key;
			return;
		}
		findKey(n.leftChild, value);
		findKey(n.rightChild, value);
	}
	public K getKey(V value) {
		if(isEmpty()) return null;
		keyFound=null;
		findKey(root, value);
		return keyFound;
	}
	public int size() {
		return currentSize;
	}
	public boolean isFull() {
		return false;
	}
	public boolean isEmpty() {
		return currentSize==0; 
	}
	public void clear() {
		root=null;
		modCounter=0;
		currentSize=0;
	}
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Node<K,V>[] nodes;
		protected int index, j;
		protected long modCheck;
		public IteratorHelper(){
			nodes = new Node[currentSize];
			index=0;
			j=0;
			modCheck=modCounter;
			inOrder(root);
		}
		public boolean hasNext(){
			if(modCheck!=modCounter)
				throw new ConcurrentModificationException();
			return index<currentSize;
		}
		private void inOrder(Node<K,V> n){
			if(n==null) return;
			inOrder(n.leftChild);
			nodes[j++]=n;
			inOrder(n.rightChild);
		}
		public abstract E next();
		public void remove(){
			throw new UnsupportedOperationException();
		}
	} //iteratorhelper class
	
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}
		public K next(){
			if(!hasNext()) 
				throw new NoSuchElementException();
			return (K) nodes[index++].key;
		} 
	} //keyiteratorhelper 
	public Iterator keys() {
		return (Iterator<K>) new KeyIteratorHelper<K>();
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next(){
			if(!hasNext()) 
				throw new NoSuchElementException();
			return (V) nodes[index++].value;
		} 
	} //valueiteratorhelper 
	public Iterator values() {
		return (Iterator<V>) new ValueIteratorHelper<V>();
	}
}
