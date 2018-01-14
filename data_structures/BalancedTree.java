/*
Assignment#4
BalancedTree.java
Megan Respicio
cssc0935
*/

package data_structures;

import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree <K extends Comparable<K>, V> implements DictionaryADT<K,V> {
	private TreeMap<K,V> tree;
	public BalancedTree(){
		tree = new TreeMap<K,V>();
	}
	public boolean contains(K key) {
		return tree.containsKey(key);
	}
	public boolean add(K key, V value) {
		if(tree.containsKey(key)) return false;
		tree.put(key, value);
		return true;
	}
	public boolean delete(K key) {
		if(tree.remove(key)==null) return false;
		return true;
	}
	public V getValue(K key) {
		return tree.get(key);
	}
	public K getKey(V value) {
		for(K keys : tree.keySet())
			if(((Comparable<V>)tree.get(keys)).compareTo(value)==0) return keys;
		return null;
	}
	public int size() {
		return tree.size();
	}
	public boolean isFull() {
		return false;
	}
	public boolean isEmpty() {
		return tree.isEmpty();
	}
	public void clear() {
		tree.clear();
	}
	public Iterator keys() {
		return tree.keySet().iterator();
	}
	public Iterator values() {
		return tree.values().iterator();
	}
}
