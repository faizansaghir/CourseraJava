/**
 * 
 */
package java_Programming_Week11;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		try {
			list1.remove(-1);
			fail("Check lower bounds exception while removing");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			list1.remove(8);
			fail("Check upper bounds exception while removing");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// TODO: Add more tests here
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		shortList.add("C");
		assertEquals("Check short list size",3,shortList.size);
		assertEquals("Check short list add","C",shortList.get(shortList.size-1));
		emptyList.add(7);
		assertEquals("Check empty list size",1,emptyList.size);
		assertEquals("Check empty list add",(Integer)7,emptyList.get(emptyList.size-1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		shortList.add("C");
		emptyList =new MyLinkedList<>();
		assertEquals("Check short list size",3,shortList.size());
		assertEquals("Check empty list size",0,emptyList.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			shortList.add(0,null);
			fail("Check null element insertion in short at index");
		}
		catch(NullPointerException e) {
			
		}
		try {
			emptyList.add(0,null);
			fail("Check null element insertion in empty at index");
		}
		catch(NullPointerException e) {
			
		}
		shortList.add(1,"C");
		assertEquals("Check short list add at index","C",shortList.get(1));
		assertEquals("Check short list shift","B",shortList.get(2));
		shortList.add(3,"D");
		assertEquals("Check short list add at index","D",shortList.get(3));
		assertEquals("Check short list shift","B",shortList.get(2));
		try {
			shortList.add(5,"F");
			fail("Check bound exception in short in add at index");
			emptyList.add(1,7);
			fail("Check bound exception in empty in add at index");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.add(-1,"F");
			fail("Check lower bound exception in short in add at index");
			emptyList.add(-1,7);
			fail("Check lower bound exception in empty in add at index");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		emptyList.add(0,6);
		assertEquals("Check empty list add at index",(Integer)6,emptyList.get(0));
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			shortList.set(3,"C");
			fail("Check out of bounds shortList");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			emptyList.set(3,1);
			fail("Check out of bounds emptyList");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			longerList.set(13,22);
			fail("Check out of bounds longerList");
		}
		catch(IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.set(1,null);
			fail("Check null point shortList");
		}
		catch(NullPointerException e) {
			
		}
		try {
			longerList.set(2,null);
			fail("Check null point longerList");
		}
		catch(NullPointerException e) {
			
		}
		String ret1=shortList.set(1,"C");
		assertEquals("Check return of set at index","B",ret1);
		assertEquals("Check set at index","C",shortList.get(1));
		Integer ret3=longerList.get(5);
		Integer ret2=longerList.set(5,99);
		assertEquals("Check return of set at index",ret3,ret2);
		assertEquals("Check set at index",(Integer)99,longerList.get(5));
	}
	
	
	// TODO: Optionally add more test methods.
	
}
