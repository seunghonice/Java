
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList list1 = new LinkedList();
		
		list1.insert(10);
		list1.print_list();
		list1.insert(30);
		list1.print_list();
		list1.insert(50);
		list1.print_list();
		list1.insert(2,20);
		list1.print_list();
		list1.insert(1,40);
		list1.print_list();
		list1.delete(3);
		list1.print_list();
		list1.delete(0);
		list1.print_list();

	}

}
