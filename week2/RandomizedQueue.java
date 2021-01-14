/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int lastIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // @SuppressWarnings("unchecked")
        Item[] a = (Item[]) new Object[1];
        array = a;
        lastIndex = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return lastIndex + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (lastIndex + 1 == array.length) {
            resize(array.length * 2);
        }
        array[++lastIndex] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int i = StdRandom.uniform(lastIndex + 1);
        Item removed = array[i];
        array[i] = array[lastIndex];
        array[lastIndex--] = null;
        if (size() > 0 && size() == array.length / 4) {
            resize(array.length / 2);
        }
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int rand = StdRandom.uniform(lastIndex + 1);
        // Item sample = null;
        // while (sample == null) {
        //     sample = array[rand];
        //     rand = StdRandom.uniform(lastIndex + 1);
        // }
        // return sample;
        return array[rand];
    }

    private void resize(int newCapacity) {
        // @SuppressWarnings("unchecked")
        Item[] a = (Item[]) new Object[newCapacity];
        int i = 0;
        int j = 0;
        while (i <= lastIndex) {
            a[j++] = array[i++];
        }
        array = a;
        lastIndex = j - 1;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] copiedArray;
        private int copiedLastIndex;

        public ListIterator() {
            // @SuppressWarnings("unchecked")
            Item[] a = (Item[]) new Object[lastIndex + 1];
            for (int i = 0; i <= lastIndex; i++) {
                a[i] = array[i];
            }
            copiedArray = a;
            copiedLastIndex = lastIndex;
        }

        public boolean hasNext() {
            return copiedLastIndex >= 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = StdRandom.uniform(copiedLastIndex + 1);
            Item item = copiedArray[i];
            copiedArray[i] = copiedArray[copiedLastIndex];
            copiedArray[copiedLastIndex--] = null;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }

        // public void forEachRemaining(Consumer<? super Item> action) {
        //
        // }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.println("List 1");
        for (int i : q) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("List 2");
        for (int i : q) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("Sample: " + q.sample());
        System.out.println("dequeue: " + q.dequeue());
        System.out.println("dequeue: " + q.dequeue());
        System.out.println("dequeue: " + q.dequeue());
        System.out.println("dequeue: " + q.dequeue());
        System.out.println("dequeue: " + q.dequeue());
        System.out.println("dequeue: " + q.dequeue());
    }
}
