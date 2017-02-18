import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

private class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private static class Node<Item> {
        private Item item;
        private Node next;

        public Item getItem() {
            return item;
        }

        public void setItem(Item it) {
            item = it;
        }
        
        public Node<Item> getNext() {
            return (Node<Item>) next;
        }

        public void setNext(Node<Item> n) {
            next = n;
        }
    }

    private Node<Item> first, last;
    // construct an empty randomized queue
    public RandomizedQueue() {

    }                 
    // is the queue empty?
    public boolean isEmpty() {
        return first == null;
    }               
    // return the number of items on the queue
    public int size() {
        return size;
    }                   
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.setItem(item);
        last.setNext(null);
        if (isEmpty()) first = last;
        else oldLast.setNext(last);
        ++size;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int offset = StdRandom.uniform(size());
        Node<Item> dummyNode = new Node<Item>();
        dummyNode.setNext(first);
        Node<Item> n = dummyNode;
        for (int i = 0; i != offset; i++) {
            n = n.getNext();
        }
        if (n.getNext() == last) {
            last = n;
        }
        Item item = n.getNext().getItem();
        n.setNext(n.getNext().getNext());
        --size;
        first = (Node<Item>) dummyNode.getNext();
        if (isEmpty()) last = null;
        return item;
    }                
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int offset = StdRandom.uniform(size());
        Node<Item> n = first;
        for (int i = 0; i != offset; i++) {
            n = n.getNext();
        }
        return n.getItem();
    }                   
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Node<Item> currentNode = first;

        public RandomizedQueueIterator() {

        }
        
        public boolean hasNext() {
            return currentNode != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = currentNode.getItem();
            currentNode = currentNode.getNext();
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        
    }
}