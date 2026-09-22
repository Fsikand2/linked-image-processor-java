import java.util.Iterator;

public class Image<T extends Comparable<T>> implements Iterable<Node<T>>
{
    private Node<T> head;
    private int width;
    private int height;
    
    public Image(int width, int height) {
    	
    	if (width < 1 || height < 1) {
    		throw new RuntimeException();
    	}
    	
    	this.width = width;
    	this.height = height;
    	
    	Node<T> prevRowStart = null;
    	
    	for (int r = 0; r < height; r++) {
    		
    		Node<T> rowStart = null;
    		Node<T> prev = null;
    		Node<T> aboveWalker = prevRowStart;
    		
    		for (int c = 0; c < width; c++) {
    			
    			Node<T> node = new Node<>();
    			
    			if (prev != null) {
    				prev.setRight(node);
    				node.setLeft(prev);
    			} else {
    				rowStart = node;
    			}
    			
    			if (aboveWalker != null) {
    				aboveWalker.setDown(node);
    				node.setUp(aboveWalker);
    				aboveWalker = aboveWalker.getRight();
    			}
    			
    			prev = node;
    		}
    		
    		if (r == 0) {
    			head = rowStart;
    		}
    		
    		prevRowStart = rowStart;
    		
    	}
    	
    }
    
    public int getHeight() {
    	return height;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public Node<T> getHead() {
    	return head;
    }
    
    @Override
    public Iterator<Node<T>> iterator() {
    	return new ImageIterator<>(head, Direction.HORIZONTAL);
    }
    
    public Iterator<Node<T>> iterator(Direction dir) {
    	return new ImageIterator<>(head, dir);
    }
    
    public void insertRow(int index, T value) {
    	if (index < 0 || index > height) {
    		throw new RuntimeException();
    	}
    	
    	Node<T> newRowStart = null;
    	Node<T> prev = null;
    	
    	for (int c = 0; c < width; c++) {
    		Node<T> node = new Node<>(value);
    		if (prev != null) {
    			prev.setRight(node);
    			node.setLeft(prev);
    		} else {
    			newRowStart = node;
    		}
    		prev = node;
    	}
    	
    	if (index == 0) {
    		Node<T> newNode = newRowStart;
    		Node<T> oldNode = head;
    		while (newNode != null) {
    			newNode.setDown(oldNode);
    			oldNode.setUp(newNode);
    			newNode = newNode.getRight();
    			oldNode = oldNode.getRight();
    		}
    		head = newRowStart;
    	} else {
    		Node<T> above = head;
    		for (int r = 0; r < index - 1; r++) {
    			above = above.getDown();
    		}
    		
    		Node<T> below = above.getDown();
    		Node<T> aboveNode = above;
    		Node<T> newNode = newRowStart;
    		Node<T> belowNode = below;
    		
    		while (newNode != null) {
    			aboveNode.setDown(newNode);
    			newNode.setUp(aboveNode);
    			
    			if (belowNode != null) {
    				newNode.setDown(belowNode);
    				belowNode.setUp(newNode);
    			}
    			
    			aboveNode = aboveNode.getRight();
    			newNode = newNode.getRight();
    			
    			if (belowNode != null) {
    				belowNode = belowNode.getRight();
    			}
    		}
    	}
    	
    	height++;
    	
    }
    
    public void removeColumn(int index) {
        if (index < 0 || index >= width) {
            throw new RuntimeException();
        }

        Node<T> colNode = head;
        for (int c = 0; c < index; c++) {
            colNode = colNode.getRight();
        }


        if (index == 0) {
            head = head.getRight();
            if (head != null) {
                head.setLeft(null);
            }
        }

        Node<T> cur = colNode;
        while (cur != null) {
            Node<T> leftNode = cur.getLeft();
            Node<T> rightNode = cur.getRight();

            if (leftNode != null) {
                leftNode.setRight(rightNode);
            }
            if (rightNode != null) {
                rightNode.setLeft(leftNode);
            }

            cur = cur.getDown();
        }

        width--;
    }


    public int compress() {
        int removed = 0;


        Node<T> rowA = head;
        int rowIndex = 0;
        while (rowA != null && rowA.getDown() != null) {
            Node<T> rowB = rowA.getDown();
            if (rowsEqual(rowA, rowB)) {
                removeRowAt(rowIndex + 1);
                removed += width;

            } else {
                rowA = rowA.getDown();
                rowIndex++;
            }
        }


        Node<T> colA = head;
        int colIndex = 0;
        while (colA != null && colA.getRight() != null) {
            Node<T> colB = colA.getRight();
            if (colsEqual(colA, colB)) {
                removeColumn(colIndex + 1); 
                removed += height;

            } else {
                colA = colA.getRight();
                colIndex++;
            }
        }

        return removed;
    }


    public void addBorder() {

        insertRow(0, head.getValue());

        Node<T> newTopNode = head;
        Node<T> origTopNode = head.getDown();
        while (newTopNode != null) {
            newTopNode.setValue(origTopNode.getValue());
            newTopNode = newTopNode.getRight();
            origTopNode = origTopNode.getRight();
        }

        Node<T> origLastRow = head;
        
        while (origLastRow.getDown() != null) {
            origLastRow = origLastRow.getDown();
        }
        insertRow(height, origLastRow.getValue());
        Node<T> newBotNode = head;
        while (newBotNode.getDown() != null) {
            newBotNode = newBotNode.getDown();
        }
        Node<T> origBotNode = newBotNode.getUp();
        while (newBotNode != null) {
            newBotNode.setValue(origBotNode.getValue());
            newBotNode = newBotNode.getRight();
            origBotNode = origBotNode.getRight();
        }

        Node<T> rowCur = head;
        Node<T> firstLeft = null;
        Node<T> prevLeft = null;
        while (rowCur != null) {

            Node<T> newNode = new Node<>(rowCur.getValue());
            if (prevLeft != null) {
                prevLeft.setDown(newNode);
                newNode.setUp(prevLeft);
            }
            else {
                firstLeft = newNode;
            }

            newNode.setRight(rowCur);
            rowCur.setLeft(newNode);
            prevLeft = newNode;
            rowCur   = rowCur.getDown();
        }
        head = firstLeft;
        width++;

        Node<T> rightColTop = head;
        while (rightColTop.getRight() != null) {
            rightColTop = rightColTop.getRight();
        }
        
        Node<T> rightCur = rightColTop;
        Node<T> firstRight = null;
        Node<T> prevRight = null;
        while (rightCur != null) {
            Node<T> newNode = new Node<>(rightCur.getValue());
            if (prevRight != null) {
                prevRight.setDown(newNode);
                newNode.setUp(prevRight);
            }
            else {
                firstRight = newNode;
            }

            rightCur.setRight(newNode);
            newNode.setLeft(rightCur);
            prevRight = newNode;
            rightCur = rightCur.getDown();
        }
        width++;
    }


    public void removeBorder() {
        if (width < 3 || height < 3) {
            throw new RuntimeException("Image too small to remove border");
        }


        removeRowAt(0);


        removeRowAt(height - 1);


        removeColumn(0);


        removeColumn(width - 1);
    }


    public Image<T> maxFilter() {
        Image<T> result = new Image<>(width, height);

        
        Node<T> srcRowStart = head;
        Node<T> dstRowStart = result.head;

        for (int r = 0; r < height; r++) {
            Node<T> srcNode = srcRowStart;
            Node<T> dstNode = dstRowStart;

            for (int c = 0; c < width; c++) {
                T maxVal = findNeighborhoodMax(srcNode);
                dstNode.setValue(maxVal);

                srcNode = srcNode.getRight();
                dstNode = dstNode.getRight();
            }

            srcRowStart = srcRowStart.getDown();
            dstRowStart = dstRowStart.getDown();
        }

        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> rowStart = head;
        while (rowStart != null) {
            Node<T> cur = rowStart;
            while (cur != null) {
                sb.append(cur.getValue()).append(" ");
                cur = cur.getRight();
            }
            sb.append("\n");
            rowStart = rowStart.getDown();
        }
        return sb.toString();
    }


    private void removeRowAt(int index) {
        
        Node<T> rowStart = head;
        for (int r = 0; r < index; r++) {
            rowStart = rowStart.getDown();
        }

        Node<T> above = rowStart.getUp();
        Node<T> below = rowStart.getDown();

        if (above == null) {
            
            head = below;
            if (below != null) {
                Node<T> cur = below;
                while (cur != null) {
                    cur.setUp(null);
                    cur = cur.getRight();
                }
            }
        }
        else {
            Node<T> aboveNode = above;
            Node<T> belowNode = below;
            while (aboveNode != null) {
                aboveNode.setDown(belowNode);
                if (belowNode != null) {
                    belowNode.setUp(aboveNode);
                    belowNode = belowNode.getRight();
                }
                aboveNode = aboveNode.getRight();
            }
        }

        height--;
    }


    private boolean rowsEqual(Node<T> rowA, Node<T> rowB) {
        Node<T> a = rowA;
        Node<T> b = rowB;
        while (a != null && b != null) {
            if (a.compareTo(b) != 0) {
                return false;
            }
            a = a.getRight();
            b = b.getRight();
        }
        return a == null && b == null;
    }


    private boolean colsEqual(Node<T> colA, Node<T> colB) {
        Node<T> a = colA;
        Node<T> b = colB;
        while (a != null && b != null) {
            if (a.compareTo(b) != 0) {
                return false;
            }
            a = a.getDown();
            b = b.getDown();
        }
        return a == null && b == null;
    }

    private T findNeighborhoodMax(Node<T> center) {
        T max = center.getValue();

        Node<T> topLeft = center;
        if (topLeft.getUp() != null) {
            topLeft = topLeft.getUp();
        }
        if (topLeft.getLeft() != null) {
            topLeft = topLeft.getLeft();
        }

      
        Node<T> rowNode = topLeft;
        int rowCount = 0;
        while (rowNode != null && rowCount < 3) {
            Node<T> colNode = rowNode;
            int colCount = 0;
            while (colNode != null && colCount < 3) {
                if (colNode.getValue().compareTo(max) > 0) {
                    max = colNode.getValue();
                }
                colNode = colNode.getRight();
                colCount++;
            }
            rowNode = rowNode.getDown();
            rowCount++;
        }

        return max;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
