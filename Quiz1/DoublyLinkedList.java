package doubly;

public class DoublyLinkedList<T> {
	private Node<T> header;
	private Node<T> trailer;
	private int size;
	
	public DoublyLinkedList() {
		header = new Node<T>(null);
		trailer = new Node<T>(null);
		size = 0;
	}
	
	public Node<T> getFirst() {
		return header.getNext();
	}
	
	public Node<T> getLast() {
		return trailer.getPrev();
	}
	
	public void addFirst(T data) {
		
	}
	
	public void addLast(T data) {
		
	}
	public void addAt(int position, T data) {
		
	}
	public void removeFirst() {
		
	}
	public void removeLast() {
		
	}
	public void removeAt(int position) {
		
	}
	public int getSize() {
		return size;
	}
	public void printList() {
		Node<T> tmp = getFirst();
		do {
			System.out.println(tmp.getData() + " -> ");
			tmp = tmp.getNext();
		} while(tmp != trailer);
	}
}
