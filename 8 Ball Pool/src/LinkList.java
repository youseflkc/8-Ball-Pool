public class LinkList {
	// node points to the beginning of the list
	private Node first;

	// constructor to make an empty list
	public LinkList() {
		first = null;
	}

	/**
	 * insert- this method inserts a new item to the end of the list
	 * 
	 * @param item
	 *            - the word to be inserted into the list
	 */
	public void insert(Ball ball) {
		Node newNode = new Node(ball);
		newNode.next = first;
		first = newNode;
	}


	/**
	 * printList- this method is used to print the linked list I used this just
	 * for testing purposes
	 */
	public void printList() {
		Node runner = first;
		while (runner != null) {
			System.out.println(runner.ball);
			runner = runner.next;
		}
	}

	/**
	 * Returns an array that contains all the elements in the list. If the list
	 * is empty, the return value is an array of length zero.
	 */
	public Ball[] getElements() {
		int count = 0; // For counting elements in the list.
		Node runner; // For traversing the list.
		Ball[] elements;
		runner = first;
		while (runner != null) {
			count++;
			runner = runner.next;
		}
		elements = new Ball[count];
		runner = first;
		count--;
		while (runner != null) {
			elements[count] = runner.ball;
			count--;
			runner = runner.next;
		}
		return elements;
	}

}