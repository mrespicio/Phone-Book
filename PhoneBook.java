/* By: Megan Respicio
   Date: December 2017
   Github: github.com/mrespicio */

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook <K extends Comparable<K>, V>{
	private int maxSize;
	private DictionaryADT<K,V> book;
    public PhoneBook(int maxSize){
    	this.maxSize=maxSize;
    	book = (DictionaryADT<K,V>) new Hashtable<Integer,Integer>(maxSize);
    	//book = (DictionaryADT<K,V>) new BinarySearchTree<Integer,Integer>();
    	//book = (DictionaryADT<K,V>) new BalancedTree<Integer,Integer>();
    }
    public void load(String filename){
    	String line;
    	try{
    		BufferedReader in = new BufferedReader(new FileReader(filename));
    		while((line=in.readLine())!=null){
    			PhoneNumber tmp = new PhoneNumber(line.substring(0,12));
    			String value = line.substring(13);
    			addEntry(tmp, value);
    		}
    		in.close();
    	}
    	catch(IOException e){
    		System.out.println("Exception" + e);
    	}
    }
    public String numberLookup(PhoneNumber number){
    	return (String) book.getValue((K) number);
    }       
    public PhoneNumber nameLookup(String name){
    	return (PhoneNumber) book.getKey((V)name);
    }      
    public boolean addEntry(PhoneNumber number, String name){
    	return book.add((K)number, (V)name);
    }    
    public boolean deleteEntry(PhoneNumber number){
    	return book.delete((K)number);
    }      
    public void printAll(){
    	Iterator<PhoneNumber> keys = book.keys();
    	Iterator<String> values = book.values();
    	while(keys.hasNext()){
    		System.out.print(keys.next());
    		System.out.print(" " + values.next());
    		System.out.println();
    	}
    }     
    public void printByAreaCode(String code){
    	Iterator<K> iter = book.keys();
    	while(iter.hasNext()){
    		PhoneNumber tmp = (PhoneNumber) iter.next();
    		if(code.compareTo(tmp.areaCode)==0)
    			System.out.println(tmp.toString() + ": " + book.getValue((K) tmp));
    	}
    }    
    public void printNames(){
    	Iterator<V> iter = book.values();
    	while(iter.hasNext()) System.out.println(iter.next());
    }
}
