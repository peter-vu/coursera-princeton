/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        // binding with oldNode
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        // retrieve item in front
        Item item = first.item;
        // roll first to next item
        first = first.next;
        // detach with previous Node
        if (first != null) {
            if (first.prev != null) {
                first.prev.next = null;
            }
            first.prev = null;
        }
        size--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = last.item;
        last = last.prev;
        // detach with next Node
        if (last != null) {
            if (last.next != null) {
                last.next.prev = null;
            }
            last.next = null;
        }
        size--;
        if (isEmpty()) {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            // try {
            if (current != null) {
                Item item = current.item;
                current = current.next;
                return item;
            }
            // }
            // catch (NoSuchElementException e) {
            //     return null;
            // }
            throw new NoSuchElementException("No more element");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }

        // @Override
        // public void forEachRemaining(Consumer<? super Item> action) {
        //     throw new UnsupportedOperationException("This operation is not supported");
        // }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        q.addFirst(2);
        q.addFirst(3);
        q.addFirst(4);
        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        System.out.println("remove first: " + q.removeFirst());
        System.out.println("remove last: " + q.removeLast());
        System.out.println("isEmpty: " + q.isEmpty());
        System.out.println("size: " + q.size());
        for (int i : q) {
            System.out.println(i);
        }

        Iterator<Integer> it = q.iterator();
        while (it.hasNext()) {
            System.out.println("next: " + it.next());
        }
        it.next();
    }

}
