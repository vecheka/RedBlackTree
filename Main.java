
public class Main {

	public static void main(String[] args) {
		final RedBlackTree<Integer> tree = new RedBlackTree<>();
		
		tree.insert(2);
		tree.insert(1);
		tree.insert(4);
		tree.insert(5);
		tree.insert(9);
		tree.insert(3);
		tree.insert(6);
		tree.insert(7);
		
		tree.printPreOrder();
		
		System.out.println();
		tree.delete(5);
		tree.printPreOrder();
		
//		System.out.println(tree.contains(2));
	}

}
