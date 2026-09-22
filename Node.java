public final class Node<T extends Comparable<T>> implements Comparable<Node<T>>
{
	private T data;
	private Node<T> up;
	private Node<T> down;
	private Node<T> right;
	private Node<T> left;
	
	public Node() {
		data = null;
		up = null;
		down = null;
		right = null;
		left = null;
	}
	
	public Node(T value) {
		data = value;
		up = null;
		down = null;
		right = null;
		left = null;
	}
	
	public T getValue() {
		return data;
	}
	
	public void setValue(T value) {
		data = value;
	}
	
	public Node<T> getUp() {
		return up;
	}
	
	public Node<T> getDown() {
		return down;
	}
	
	public Node<T> getRight() {
		return right;
	}
	
	public Node<T> getLeft() {
		return left;
	}
	
	public void setUp(Node<T> p ) {
		up = p;
	}
	
	public void setDown(Node<T> p) {
		down = p;
	}
	
	public void setRight(Node<T> p) {
		right = p;
	}
	
	public void setLeft(Node<T> p) {
		left = p;
	}
	
	@Override
	public int compareTo(Node<T> other) {
		return data.compareTo(other.data);
	}
    
}
