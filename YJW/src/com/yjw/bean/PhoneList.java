package com.yjw.bean;

import java.io.Serializable;
//import java.util.AbstractList;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.RandomAccess;

public class PhoneList<T> extends ArrayList<T>   
        implements List<T>, RandomAccess, Cloneable, Serializable    
{   
    private static final long serialVersionUID = 8683452581122892189L;    
  
   /**  
     * The array buffer into which the elements of the ArrayList are stored.  
    * The capacity of the ArrayList is the length of this array buffer.  
     */  
    @SuppressWarnings("unused")
	private T[] elementData;  
    public ArrayList<String> list;
  


private void writeObject(java.io.ObjectOutputStream s)    
        throws java.io.IOException{    
    int expectedModCount = modCount;    
    // Write out element count, and any hidden stuff    
    s.defaultWriteObject();   
 
   if (modCount != expectedModCount) {    
        throw new ConcurrentModificationException();    
   }   
   }   
 
    /**  
26.     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,  
27.     * deserialize it).  
28.     */  
   private void readObject(java.io.ObjectInputStream s)    
        throws java.io.IOException, ClassNotFoundException {    
   // Read in size, and any hidden stuff    
    s.defaultReadObject();   
    }

	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}   
  
}  
