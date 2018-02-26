/* By: Megan Respicio
   Date: December 2017
   Github: github.com/mrespicio */

import java.util.Iterator;
import java.util.regex.Pattern;

import data_structures.*;

public class PhoneNumber implements Comparable<PhoneNumber> {
    String areaCode, prefix, number;
    String phoneNumber;
    public PhoneNumber(String n){
    	verify(n);
    	phoneNumber=n;
    	String[] num = phoneNumber.split("-");
    	areaCode = num[0];
    	prefix = num[1];
    	number = num[2];
    }
    public int compareTo(PhoneNumber n){
    	return phoneNumber.compareTo(n.phoneNumber);
    }   
    public int hashCode(){
	    byte b[] = phoneNumber.getBytes();
	    long hashVal=0;
	    for(int i=number.length(); i>=0; i--)
	    	hashVal=(hashVal<<5) +b[i];
	    return (int) hashVal;
    }     
    private void verify(String n){
    	String form = "\\d{3}-\\d{3}-\\d{4}";
    	if (!n.matches(form)) 
    		throw new IllegalArgumentException();
    }  
    public String getAreaCode(){
    	return areaCode;
    }   
    public String getPrefix(){
    	return prefix;
    }
    public String getNumber(){
    	return number;
    }    
    public String toString(){
    	return phoneNumber;
    }   
} 
