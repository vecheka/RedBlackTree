
/**
 * 
 * @author Vecheka Chhourn
 * @version 1.0
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {
	
	/** Black Node Integer Representation.*/
	private static final int BLACK = 0;
	/** Red Node Integer Representation.*/
	private static final int RED = 1;
	
	/** Overall root of the tree.*/
	private TreeNode<T> root;
	
	
	/**
	 * A constructor to construct an empty Red-Black Tree.
	 */
	public RedBlackTree() {
		root = null;
	}
	
	
	/**
	 * Insert new node to the tree.
	 * @param theData data to add to the tree.
	 */
	public void insert(final T theData) {
		insert(root, theData);
	}

	/**
	 * Remove a node from the tree.
	 * @param theData data to be removed.
	 */
	public void delete(final T theData) {
		if (root == null) return;
		TreeNode<T>  current = root;
		TreeNode<T> replaceNode = findNode(current, theData);
		if (replaceNode.left != null) { // find the left most node
			while (replaceNode.left != null) replaceNode = replaceNode.left;
		}
		else if (replaceNode.right != null) { // find the right most node
			while (replaceNode.left != null) replaceNode = replaceNode.right;
		}

		fixDeleteTree(replaceNode); // fix the red black tree before deletion
		root = delete(root, theData); // delete the node from the tree
		
	}
	
	/**
	 * Check whether the tree has a given data.
	 * @param theData data to be searched for
	 * @return true if found
	 */
	public boolean contains(final T theData) {
		final TreeNode<T> temp = findNode(root, theData);
		return temp != null;
	}
	
	// helper methods
	/**
	 * Helper method to insert new data to the tree
	 * @param theRoot overall root of the tree
	 * @param theData data to be inserted
	 * @return root of the tree
	 */
	private void insert(TreeNode<T> theRoot, final T theData) {
		
		TreeNode<T> current = root;
		TreeNode<T> newNode = null; // to store newly added node of the tree
		if (theRoot == null) {
			root = new TreeNode<T>(theData);
			root.color = BLACK;
			root.parent = null;
			newNode = root;
		} else {
			while (true) {
				if (current.data.compareTo(theData) == 1) { // left-branch
					if (current.left == null) {
						current.left = new TreeNode<T>(theData);
						current.left.parent = current;
						current.left.color = RED;
						newNode = current.left;
						break;
					} else {
						current = current.left;
					}
				} else if (current.data.compareTo(theData) == -1) { // right-branch
					if (current.right == null) {
						current.right = new TreeNode<T>(theData);
						current.right.parent = current;
						current.right.color = RED;
						newNode = current.right;
						break;
						
					} else {
						current = current.right;
					}
				}
			}
		}
		
		fixTree(newNode);
		
//		return theRoot;
	}

	/**
	 * Re-color or rotate Red-Black Tree to meet constraints.
	 * Pre-Conditions: 
	 *   1. If parent's sibling is BLACK, apply rotations (4 cases) and re-color if necessary.
	 *   2. If parent's sibling is RED, re-color. 
	 * @param theNode new node in the tree
	 * @return a fixed tree
	 */
	private void fixTree(TreeNode<T> theNode) {
		if (theNode.parent == null) return;
		
		while (theNode.parent != null && theNode.parent.color == RED) {
			TreeNode<T> uncle = null;
			
			if (theNode.parent == theNode.parent.parent.left) { // left-branch
				uncle = theNode.parent.parent.right;
				if (uncle != null && uncle.color == RED) {// recolor
					theNode.parent.color = BLACK;
					uncle.color = BLACK;
					theNode.parent.parent.color = RED;
					theNode = theNode.parent.parent;
				} else {
					
					if (theNode == theNode.parent.right) { // left-rotation
						theNode = theNode.parent;
						rotateLeft(theNode);
					}
					
					theNode.parent.color = BLACK;
					theNode.parent.parent.color = RED;
					if (theNode == theNode.parent.left) { // right-rotation
						rotateRight(theNode.parent.parent);
					}
	
				}
				
				
			} else if (theNode.parent == theNode.parent.parent.right) { // right-branch
				uncle = theNode.parent.parent.left;
				if (uncle != null && uncle.color == RED) {// recolor
					theNode.parent.color = BLACK;
					uncle.color = BLACK;
					theNode.parent.parent.color = RED;
					theNode = theNode.parent.parent;
				} else {
					
					if (theNode == theNode.parent.left) { // right-rotation
						theNode = theNode.parent;
						rotateRight(theNode);
					}
					
					theNode.parent.color = BLACK;
					theNode.parent.parent.color = RED;
					if (theNode == theNode.parent.right) { // left-rotation
						rotateLeft(theNode.parent.parent);
					}
				}
				
			}
			
		}
		root.color = BLACK;
	}
	
	
	
	/** 
	 * Delete the given node in the tree (overload method)
	 * @param theRoot overall root of the tree
	 * @param theData data to be deleted
	 */
	private TreeNode<T> delete(TreeNode<T> theRoot, final T theData) {
		if (theRoot.data.compareTo(theData) == -1) {
			theRoot.right = delete(theRoot.right, theData);
		} else if (theRoot.data.compareTo(theData) == 1) {
			theRoot.left = delete(theRoot.left, theData);
		} else {
			if (theRoot.left == null && theRoot.right == null) {
				theRoot.data = null;
			} else if (theRoot.left != null && theRoot.data.compareTo(theData) == 0) {
				final T temp = theRoot.data;
				theRoot.data = theRoot.left.data;
				theRoot.left.data = temp;
				theRoot.left = delete(theRoot.left, theData);
			} else if (theRoot.right != null && theRoot.data.compareTo(theData) == 0) {
				final T temp = theRoot.data;
				theRoot.data = theRoot.right.data;
				theRoot.right.data = temp;
				theRoot.right = delete(theRoot.right, theData);
			}
		}
		return theRoot;
	}

	
	
	/**
	 * Find a node in the tree with the given data
	 * @param theRoot overall root of the tree
	 * @param theData data store in the node
	 * @return the node with the given data
	 */
	private TreeNode<T> findNode(final TreeNode<T> theRoot, final T theData) {
		if (theRoot == null) return null;
		if (theRoot.data.compareTo(theData) == 0) {
			return theRoot;
		} else if (theRoot.data.compareTo(theData) == 1) {
			return findNode(theRoot.left, theData);
		} else {
			return findNode(theRoot.right, theData);
		}
	}


	/**
	 * Fix tree after deletion of a node.
	 * @param theNode the deleted node
	 */
	private void fixDeleteTree(final TreeNode<T> theNode) {
		
		TreeNode<T> sibling = null;
		if (theNode.color != RED) {
			if (theNode == theNode.parent.left) { // right-branch
				sibling = theNode.parent.right;
				// If sibling parent is black and has at least one red child, do rotation
				if (sibling.color == BLACK) { // sibling is black
					if ((sibling.left != null && sibling.right != null)
							&& (sibling.left.color == RED || sibling.right.color == RED)) {
						sibling.right.color = sibling.color;
						sibling.color = theNode.parent.color;
						theNode.parent.color = theNode.color;
						rotateLeft(theNode.parent);
					} else if (sibling.left != null && sibling.left.color == RED) {
						int temp = sibling.color;
						sibling.color = sibling.left.color;
						sibling.left.color = temp;
						rotateRight(sibling);
						sibling.color = sibling.parent.color;
						rotateLeft(theNode.parent);
					} else if (sibling.right != null && sibling.right.color == RED){
						sibling.color = theNode.parent.color;
						sibling.right.color = BLACK;
						sibling.parent.color = theNode.color;
						rotateLeft(theNode.parent);
					} else {
						sibling.color = RED;
					}
				} else { // sibling is red
					if (sibling.left != null)  sibling.left.color = RED;
					sibling.color = BLACK;
					rotateLeft(theNode.parent);
				}

			} else { // left-branch
				sibling = theNode.parent.left;
				// If sibling parent is black and has at least one red child, do rotation
				if (sibling.color == BLACK) { // sibling is black
					if ((sibling.left != null && sibling.right != null)
							&& (sibling.left.color == RED || sibling.right.color == RED)) {
						sibling.left.color = sibling.color;
						rotateRight(theNode.parent);
					} else if (sibling.left != null && sibling.left.color == RED) {
						int temp = sibling.color;
						sibling.color = sibling.right.color;
						sibling.right.color = temp;
						rotateLeft(sibling);
						sibling.color = sibling.parent.color;
						rotateRight(theNode.parent);
					} else if (sibling.right != null && sibling.right.color == RED){
						sibling.color = theNode.parent.color;
						sibling.left.color = BLACK;
						sibling.parent.color = theNode.color;
						rotateRight(theNode.parent);
					} else {
						sibling.color = RED;
					}
				} else { // sibling is red
					if (sibling.left != null)  sibling.left.color = RED;
					sibling.color = BLACK;
					rotateRight(theNode.parent);
				}
			}
		}

		
	}


	/**
	 * Rotate tree to the right.
	 * @param theNode current node in the tree to be rotated
	 */
	private void rotateRight(TreeNode<T> theNode) {
		TreeNode<T> newRoot = null;
		if (theNode.parent != null) {
			newRoot = theNode.left;
			if (theNode == theNode.parent.left) {
				theNode.parent.left = newRoot;
			} else {
				theNode.parent.right = newRoot;
			}
			newRoot.parent = theNode.parent;
			theNode.left = newRoot.right;
			if (newRoot.right != null) newRoot.right.parent = theNode;
			newRoot.right = theNode;
			theNode.parent = newRoot;
			
		} else {
			
			newRoot = theNode.left;
			theNode.left = newRoot.right;
			if (newRoot.right != null) newRoot.right.parent = theNode;
			newRoot.right = root;
			theNode.parent = newRoot;
			newRoot.parent = null;
			root = newRoot;
			
			
		}
	}


	/**
	 * Rotate tree to the left.
	 * @param theNode current node in the tree to be rotated
	 */
	private void rotateLeft(TreeNode<T> theNode) {
		TreeNode<T> newRoot = null;
		if (theNode.parent != null) {
			newRoot = theNode.right;
			if (theNode == theNode.parent.left) {
				theNode.parent.left = newRoot;
			} else {
				theNode.parent.right = newRoot;
			}
			newRoot.parent = theNode.parent;
			theNode.right = newRoot.left;
			if (newRoot.left != null) newRoot.left.parent = theNode;
			newRoot.left = theNode;
			theNode.parent = newRoot;
			
//			theNode = newRoot;
		} else {
			newRoot = theNode.right;
			theNode.right = newRoot.left;
			if (newRoot.left != null) newRoot.left.parent = theNode;
			newRoot.left = root;
			theNode.parent = newRoot;
			newRoot.parent = null;
			root = newRoot;
			
		}
	}


	/**
	 * Display tree nodes in-order traversal.
	 */
	public void printPreOrder() {
		printPreOrder(root);
	}
	
	/**
	 * Display tree nodes in-order traversal.
	 * @param theRoot overall root of a tree
	 */
	private void printPreOrder(final TreeNode<T> theRoot) {
		if (theRoot != null) {
			if (theRoot.data != null) {
				if (theRoot.color == 0) System.out.print(theRoot.data + "B ");
				else System.out.print(theRoot.data + "R ");
			}
			printPreOrder(theRoot.left);
			printPreOrder(theRoot.right);
		}
		
	}
}



/**
 * A class to construct a node in the tree.
 * @param <T> Generic type
 */
class TreeNode<T> {
	
	/** Value of a node.*/
	protected T data;
	/** Height of the tree.*/
	protected Integer color;
	/** Left child.*/
	protected TreeNode<T> left;
	/** Right child.*/
	protected TreeNode<T> right;
	/** Parent of the node.*/
	protected TreeNode<T> parent;
	
	
	/** 
	 * Constructor that takes an object, left root, and right root.
	 * @param theData to add to the tree
	 * @param theLeft left root
	 * @param theRight right root
	 */
	public TreeNode(final T theData, final TreeNode<T> theLeft, 
			final TreeNode<T> theRight) {
		data = theData;
		left = theLeft;
		right = theRight;
		color =  1; // black = 0, red = 1;
		parent = null;
	}
	
	/**
	 * Copy constructor that takes an object and add to the tree, 
	 * and initialize both children to null.
	 * @param theData to add to the tree
	 */
	public TreeNode(final T theData) {
		this(theData, null, null);
	}
	
	/**
	 * Constructor that takes no arguments, and initialize left and
	 * right child to null.
	 */
	public TreeNode(TreeNode<T> theNode) {
		data = theNode.data;
		color = theNode.color;
		left = theNode.left;
		right = theNode.right;
		parent = theNode.parent;
	}
}