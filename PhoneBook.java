/*
Assignment#4
PhoneBook.java
Megan Respicio
cssc0935
*/

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook <K extends Comparable<K>, V>{
	private int maxSize;
	private DictionaryADT<K,V> book;
    // Constructor.  There is no argument-less constructor, or default size
    public PhoneBook(int maxSize){
    	this.maxSize=maxSize;
    	book = (DictionaryADT<K,V>) new Hashtable<Integer,Integer>(maxSize);
    	//book = (DictionaryADT<K,V>) new BinarySearchTree<Integer,Integer>();
    	//book = (DictionaryADT<K,V>) new BalancedTree<Integer,Integer>();
    }
    // Reads PhoneBook data from a text file and loads the data into
    // the PhoneBook.  Data is in the form "key=value" where a phoneNumber
    // is the key and a name in the format "Last, First" is the value.
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
    // Returns the name associated with the given PhoneNumber, if it is
    // in the PhoneBook, null if it is not.
    public String numberLookup(PhoneNumber number){
    	return (String) book.getValue((K) number);
    }       
    // Returns the PhoneNumber associated with the given name value.
    // There may be duplicate values, return the first one found.
    // Return null if the name is not in the PhoneBook.
    public PhoneNumber nameLookup(String name){
    	return (PhoneNumber) book.getKey((V)name);
    }      
    // Adds a new PhoneNumber = name pair to the PhoneBook.  All
    // names should be in the form "Last, First".
    // Duplicate entries are *not* allowed.  Return true if the
    // insertion succeeds otherwise false (PhoneBook is full or
    // the new record is a duplicate).  Does not change the datafile on disk.
    public boolean addEntry(PhoneNumber number, String name){
    	return book.add((K)number, (V)name);
    }    
    // Deletes the record associated with the PhoneNumber if it is
    // in the PhoneBook.  Returns true if the number was found and
    // its record deleted, otherwise false.  Does not change the datafile on disk.
    public boolean deleteEntry(PhoneNumber number){
    	return book.delete((K)number);
    }      
    // Prints a directory of all PhoneNumbers with their associated
    // names, in sorted order (ordered by PhoneNumber).
    public void printAll(){
    	Iterator<PhoneNumber> keys = book.keys();
    	Iterator<String> values = book.values();
    	while(keys.hasNext()){
    		System.out.print(keys.next());
    		System.out.print(" " + values.next());
    		System.out.println();
    	}
    }     
    // Prints all records with the given Area Code in ordered
    // sorted by PhoneNumber.
    public void printByAreaCode(String code){
    	Iterator<K> iter = book.keys();
    	while(iter.hasNext()){
    		PhoneNumber tmp = (PhoneNumber) iter.next();
    		if(code.compareTo(tmp.areaCode)==0)
    			System.out.println(tmp.toString() + ": " + book.getValue((K) tmp));
    	}
    }  
    // Prints all of the names in the directory, in sorted order (by name,
    // not by number).  There may be duplicates as these are the values.       
    public void printNames(){
    	Iterator<V> iter = book.values();
    	while(iter.hasNext()) System.out.println(iter.next());
    }
}