import java.util.Iterator;

public class ImageIterator<T extends Comparable<T>> implements Iterator<Node<T>>
{
    private Node<T> current;
    private Node<T> lineStart;
    private Direction direction;
    
    public ImageIterator(Node<T> head, Direction direction) {
    	current = head;
    	lineStart = head;
    	this.direction = direction;
    }
    
    @Override
    public boolean hasNext() {
    	return current != null;
    }
    
    @Override
    public Node<T> next() {
    	
    	Node<T> toReturn = current;
    	
    	if (direction == Direction.HORIZONTAL) {
    		
    		if (current.getRight() != null) {
    			current = current.getRight();
    		} else {
    			lineStart = lineStart.getDown();
    			current = lineStart;
    		}
    	} else {
    		if (current.getDown() != null) {
    			current = current.getDown();
    		} else {
    			lineStart = lineStart.getRight();
    			current = lineStart;
    		}
    	}
    	
    	return toReturn;
    }
    
    @Override
    public void remove() {
    	throw new UnsupportedOperationException();
    }
}
