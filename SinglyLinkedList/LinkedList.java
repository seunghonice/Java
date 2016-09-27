
public class LinkedList {
	private Node head; // ����Ʈ�� ��带 ����Ű�� ������	point to head of the list
	private int count; // ����Ʈ�� ��尹��	the number of nodes in the list

	private class Node {	// ����Ŭ���� Node	inner class Node
		int data;
		Node next;

		Node(int input) {
			data = input;
			next = null;
		}
	}
	public LinkedList() {
		head = null;
		count = 0;
	}
	public void insert(int input) {		// �Ǿտ� input �����͸� ������ ��� �߰�	insert a node at the first of the list
		Node newnode = new Node(input);		
		if (isEmpty()) {
			head = newnode;
		} else {
			newnode.next = head;
			head = newnode;
		}
		count++;
	}
	public void insert(int position, int input){	// position ��ġ�� input �����͸� ������ ��� �߰�	insert a node at the given position
		if(position > count || position <0) {
			System.out.println("position value - out of range.");
			return;
		}
		if (position < 1) {
			insert(input);
		} else {
			Node newnode = new Node(input);
			Node tmp = head;
			for (int i = 0; i < position-1; i++) {
				tmp = tmp.next;
			}
			newnode.next = tmp.next;
			tmp.next = newnode;
			count++;
		}
	}

	public void delete() {	// �Ǿ��� ��带 ����
		// TODO
		Node tmp = head;
		head = head.next;
		tmp.next = null;		
		count--;
	}
	public void delete(int position) {	// position ��ġ�� ��带 ����		delete a Node at the given position
		if(position > count || position <0){
			System.out.println("position value - out of range.");
			return;
		}
		if (position < 1) {
			delete();
		} else {
			Node tmp = head;
			for (int i = 0; i < position-1; i++) {
				tmp = tmp.next;
			}
			Node tmpToNull = tmp.next;
			tmp.next = tmpToNull.next;
			tmpToNull.next = null;
			count--;
		}
	}
	public boolean isEmpty() { // ����Ʈ�� ����ִ��� �˻�	check whether the list is empty or not
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}
	public void print_list() {
		Node temp = head;
		for (int i = 0; i < count; i++) { // ��� ���
			System.out.print(temp.data + "  ");
			temp = temp.next;
		}
		System.out.println("");
	}

}
