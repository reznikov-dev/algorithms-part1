import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int qSize = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    /**
     * List Iterator
     */
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        /** {@inheritDoc} **/
        public boolean hasNext() {
            return current != null;
        }

        /** {@inheritDoc} **/
        public void remove() {
            throw new UnsupportedOperationException("Method is unsupported");
        }

        /** {@inheritDoc} **/
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more items");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /** Constructor **/
    public Deque() {
        // empty constructor
    }

    /**
     * Returns is deque is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return qSize == 0;
    }

    /**
     * Returns deque size
     * @return int
     */
    public int size() {
        return qSize;
    }

    /**
     * Adds the item to the front
     * @param item - item to add to the front
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Empty item");
        }

        qSize++;

        if (first == null) {
            first = new Node();
            first.item = item;
            last = first;
            return;
        }

        Node node = first;
        first = new Node();
        first.item = item;
        node.prev = first;
        first.next = node;
    }

    /**
     * Adds the item to the back
     * @param item - item to add to the back
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Empty item");
        }

        qSize++;

        if (last == null) {
            last = new Node();
            last.item = item;
            first = last;
            return;
        }

        Node node = last;
        last = new Node();
        last.item = item;
        node.next = last;
        last.prev = node;
    }

    /**
     * Removes and returns the item from the front
     * @return Item
     */
    public Item removeFirst() {
        if (first == null) {
            throw new java.util.NoSuchElementException("Call remove on empty deque");
        }

        Item item = first.item;

        if (qSize == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        qSize--;
        return item;
    }

    /**
     * Removes and returns the item from the back
     * @return Item
     */
    public Item removeLast() {
        if (last == null) {
            throw new java.util.NoSuchElementException("Call remove on empty deque");
        }

        Item item = last.item;

        if (qSize == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        qSize--;
        return item;
    }

    /**
     * Returns iterator over items from front to back order
     * @return Iterator<Item>
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Some kind of unit testing - requirements was to print all methods to StdOutput
     * @param args - args not used
     */
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();

        dq.addFirst(2);
        dq.addLast(3);
        dq.addFirst(1);

        for (Integer i : dq) {
            StdOut.println(i);
        }

        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.size());
        StdOut.println(dq.isEmpty());

        for (Integer i : dq) {
            StdOut.println(i);
        }

        try {
            StdOut.println(dq.removeFirst());
        } catch (java.util.NoSuchElementException exception) {
            StdOut.println(exception.getMessage());
        }
    }
}