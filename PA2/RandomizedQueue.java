import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] arr;
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private boolean isFull() {
        return size == arr.length;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (isFull()) resize(arr.length * 2);
        arr[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int randNum = StdRandom.uniform(size);
        Item item = arr[randNum]; 
        arr[randNum] = arr[--size];
        arr[size] = null;
        if (size > 0 && size == arr.length / 4) resize(arr.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int randNum = StdRandom.uniform(size);
        return arr[randNum];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indices;
        private int id;

        public RandomizedQueueIterator() {
            indices = new int[size];
            for (int i = 0; i < size; ++i) {
                indices[i] = i;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return id < indices.length;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return arr[indices[id++]];
        }
    }

    public static void main(String[] args) {

    }
}