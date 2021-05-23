import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int qSize = 0;

    private class RandomArrayIterator implements Iterator<Item> {
        private int index = qSize;

        /** {@inheritDoc} **/
        public boolean hasNext() {
            return index > 0;
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

            int rIndex = StdRandom.uniform(index);
            index--;

            if (rIndex == index) {
                return queue[rIndex];
            }

            Item item = queue[rIndex];
            queue[rIndex] = queue[index];
            queue[index] = item;

            return item;
        }
    }

    /**
     * Constructor
     **/
    public RandomizedQueue() {
        queue = (Item[]) new Object[4];
    }

    /**
     * Returns is queue is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return qSize == 0;
    }

    /**
     * Returns the number of items in queue
     *
     * @return int
     */
    public int size() {
        return qSize;
    }

    /**
     * Adds item in queue
     * @param item - item to add in queue
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Empty item");
        }

        if (queue.length == qSize) {
            resize(qSize * 2);
        }

        queue[qSize] = item;
        qSize++;
    }

    /**
     * Removes and returns random item
     * @return Item
     */
    public Item dequeue() {
        if (qSize == 0) {
            throw new java.util.NoSuchElementException("Empty queue");
        }

        int index = StdRandom.uniform(qSize);
        Item item = queue[index];

        if (index == qSize - 1) {
            queue[index] = null;
        } else {
            queue[index] = queue[qSize - 1];
            queue[qSize - 1] = null;
        }

        qSize--;
        if (qSize > 0 && qSize == queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    /**
     * Returns random item without remove
     * @return Item
     */
    public Item sample() {
        if (qSize == 0) {
            throw new java.util.NoSuchElementException("Empty queue");
        }

        return queue[StdRandom.uniform(qSize)];
    }

    /**
     * Returns iterator in random order
     * @return Iterator<Item>
     */
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    /**
     * Some kind of unit testing - requirements was to print all methods to StdOutput
     * @param args - args not used
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        StdOut.println(rq.size());

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        for (Integer i : rq) {
            StdOut.println(i);
        }

        StdOut.println(rq.isEmpty());

        StdOut.println(rq.sample());
        StdOut.println(rq.sample());
        StdOut.println(rq.size());

        StdOut.println(rq.dequeue());
        StdOut.println(rq.size());

        for (Integer i : rq) {
            StdOut.println(i);
        }

        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.dequeue());

        StdOut.println(rq.isEmpty());

        try {
            StdOut.println(rq.dequeue());
        } catch (java.util.NoSuchElementException exception) {
            StdOut.println(exception.getMessage());
        }
    }

    /**
     * Resizes queue
     * @param capacity - new queue capacity
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < qSize; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }
}
