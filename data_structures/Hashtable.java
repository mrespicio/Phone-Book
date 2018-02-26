/* By: Megan Respicio
   Date: December 2017
   Github: github.com/mrespicio */

package data_structures;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class Hashtable <K extends Comparable<K>, V> implements DictionaryADT<K, V>{
	private class DictionaryNode<K, V> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		public DictionaryNode(K key, V value){
			this.key = key;
			this.value = value;
		}	
		public int compareTo(DictionaryNode<K,V> node){
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
	}
	private long modCounter;
	private int currentSize, maxSize, tableSize;
	private LinearListADT<DictionaryNode<K,V>>[] list;
	public Hashtable(int size){
		currentSize = 0;
		maxSize = size;
		modCounter = 0;
		tableSize = (int) (maxSize *1.3f);
		list = new LinearList[tableSize];
		for(int i =0; i < tableSize; i++)
			list[i] = new LinearList<DictionaryNode<K,V>>();	//array of linked lists
	}
	private int getIndex(K key){
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}
	public boolean contains(K key){
		return list[getIndex(key)].contains(new DictionaryNode<K,V>(key,null));
	}
	public boolean add(K key, V value){
		if(isFull()) return false;
		if(contains(key)) return false;  
		list[getIndex(key)].addLast(new DictionaryNode<K,V> (key,value)); 
		currentSize++;
		modCounter++;
		return true;
	}
	public boolean delete(K key){
		if(isEmpty()) return false; 
		if(!contains(key)) return false;	
		list[getIndex(key)].remove(new DictionaryNode<K,V> (key,null));
		currentSize--;
		modCounter++;
		return true;
	}
	public V getValue(K key){
		DictionaryNode<K,V> tmp = list[getIndex(key)].find(new DictionaryNode<K,V>(key,null));
		if(tmp == null) return null;
		return tmp.value;
	}
	public K getKey(V value){
		for(int i = 0; i < tableSize; i++){
			for(DictionaryNode<K,V> n : list[i]){
				if(((Comparable<V>)value).compareTo(n.value) == 0) return n.key;
			}
		}
		return null; 
	} 
	public int size(){
		return currentSize;
	}
	public boolean isFull(){
		return currentSize == maxSize;
	}
	public boolean isEmpty(){
		return currentSize == 0;
	} 
	public void clear(){
		for(int i = 0; i < tableSize; i++)
			list[i].clear();
		currentSize = 0;
		modCounter = 0;
	}
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryNode<K, V> [] nodes;
		protected int index;
		protected long modCheck;
		public IteratorHelper(){
			nodes = new DictionaryNode[currentSize];
			index = 0;
			int j = 0;
			modCheck = modCounter;
			for(int i = 0; i < tableSize; i++)
				for(DictionaryNode<K,V> n : list[i])
					nodes[j++] = n;
			nodes = (DictionaryNode<K,V>[]) shellSort(nodes);
		} // IteratorHelper method
		public boolean hasNext(){
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return index < currentSize;
		} 
		public abstract E next();
		public void remove(){
			throw new UnsupportedOperationException();
		} 
		private DictionaryNode<K,V>[] shellSort(DictionaryNode<K,V> []array){
			DictionaryNode<K,V>[] n = array;
			DictionaryNode<K,V> tmp;
			int in, out, h=1;
			int size = n.length;
			while(h <= size/3) h = h*3+1;
			while(h>0){
				for(out=h; out<size; out++){
					tmp = n[out];
					in = out;
					while(in>h-1 && n[in-h].compareTo(tmp) >= 0){
						n[in] = n[in-h];
						in -= h;
						} //while
					n[in] = tmp;
					} //for
				h = (h-1)/3;
				} 
			return n;
		} //shellSort
	} //IteratorHelper class
	
	public Iterator keys(){
		return new KeyIteratorHelper();
	}
	class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}
		public K next(){
			return (K) nodes[index++].key;
		}
	} //KeyIteratorHelper
	
	public Iterator values(){
		return new ValueIteratorHelper();
	}
	class ValueIteratorHelper<V> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}
		public V next(){
			return (V) nodes[index++].value;
		}
	} //ValueIteratorHelper
} //Hastable class
