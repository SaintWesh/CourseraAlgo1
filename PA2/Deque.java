import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int first, last, size;
    private Item[] arr;
    // construct an empty deque
    public Deque() {
        arr = (Item[]) new Object[2];
    }                        
    // is the deque empty
    public boolean isEmpty() {
        return size == 0;
    }
    // return the number of items on the deque
    public int size() {
        return size;
    }                        
    // add the item to the front
    public void addFirst(Item item) {
        // guard
        if (item == null) throw new java.lang.NullPointerException();
        // special case: empty
        if (isEmpty()) {
            arr[first] = item;
            ++size;
            return;
        }
        if (isFull()) resize(arr.length * 2);
        first = (first - 1) & (arr.length - 1);
        arr[first] = item;
        ++size;
    }

    private boolean isFull() {
        return size == arr.length;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int magicNum = arr.length - 1;
        int j = 0;
        int i = first;
        for (; i != last; i = (i + 1) & magicNum, j++) {
            copy[j] = arr[i];
        }
        copy[j] = arr[i];
        // change state
        arr = copy;
        first = 0;
        last = j;
    }
    // add the item to the end
    public void addLast(Item item) {
        // guard
        if (item == null) throw new java.lang.NullPointerException();
        // special case: empty
        if (isEmpty()) {
            arr[last] = item;
            ++size;
            return;
        }
        if (isFull()) resize(2 * arr.length);
        last = (last + 1) & (arr.length - 1);
        arr[last] = item;
        ++size;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item ret = arr[first];
        arr[first] = null;
        if (size > 1) {
            first = (first + 1) & (arr.length - 1);
        }
        // change arr
        --size;
        if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
        return ret;
    }     
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item ret = arr[last];
        arr[last] = null;
        if (size > 1) {
            last = (last - 1) & (arr.length - 1);
        }
        --size;
        if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
        return ret;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private int offset = 0;
        public DequeueIterator() {
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public boolean hasNext() {
            if (offset >= size) return false;
            return true;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = arr[(first + offset) & (arr.length - 1)];
            ++offset;
            return item;
        }
    }
    // unit testing
    public static void main(String[] args) {
        Deque<String> ds = new Deque<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.startsWith("+")) {
                ds.addFirst(s);
            } else {
                ds.addLast(s);
            }
        }

        for (String s : ds) {
            StdOut.println(s);
        }

        boolean flag = true;
        while (!ds.isEmpty()) {
            String s;
            if (flag) {
                s = ds.removeFirst();
            } else {
                s = ds.removeLast();
            }
            flag = !flag;
            StdOut.println(s);
        }
        
    }   
}