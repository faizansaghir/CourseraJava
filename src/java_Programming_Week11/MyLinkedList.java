package java_Programming_Week11;


import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head=new LLNode<>(null);
		tail=new LLNode<>(null);
		head.next=tail;
		tail.prev=head;
		size=0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element==null) {
			throw(new NullPointerException());
		}
		LLNode<E> newNode=new LLNode<>(element);
		newNode.next=tail;
		newNode.prev=tail.prev;
		tail.prev.next=newNode;
		tail.prev=newNode;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if(index>=size||index<0) {
			throw(new IndexOutOfBoundsException());
		}
		LLNode<E> currNode=head;
		for(int i=0;i<=index;i++) {
			currNode=currNode.next;
		}
		return currNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if(index>size||index<0) {
			throw(new IndexOutOfBoundsException());
		}
		else if(element==null) {
			throw(new NullPointerException());
		}
		
		if(index==0) {
			LLNode<E> newNode=new LLNode<>(element);
			newNode.prev=head;
			newNode.next=head.next;
			head.next.prev=newNode;
			head.next=newNode;
			size++;
		}
		else if(index==size) {
			add(element);
		}
		else {
			LLNode<E> currNode=head;
			for(int i=0;i<=index;i++) {
				currNode=currNode.next;
			}
			LLNode<E> newNode=new LLNode<>(element);
			newNode.next=currNode;
			newNode.prev=currNode.prev;
			currNode.prev.next=newNode;
			currNode.prev=newNode;
			size++;
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index>=size||index<0) {
			throw(new IndexOutOfBoundsException());
		}
		LLNode<E> currNode=head;
		for(int i=0;i<=index;i++) {
			currNode=currNode.next;
		}
		currNode.next.prev=currNode.prev;
		currNode.prev.next=currNode.next;
		size--;
		return currNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(index>=size||index<0) {
			throw(new IndexOutOfBoundsException());
		}
		else if(element==null) {
			throw(new NullPointerException());
		}
		LLNode<E> currNode=head;
		for(int i=0;i<=index;i++) {
			currNode=currNode.next;
		}
		E temp=currNode.data;
		currNode.data=element;
		return temp;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
