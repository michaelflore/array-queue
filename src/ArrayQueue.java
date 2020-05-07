public class ArrayQueue<T> implements ArrayQueueADT<T> {

    private int count;
    private int queueFront;
    private int queueRear;
    private T[] arrayQueue;

    //Default constructor
    public ArrayQueue() {
        queueFront = 0;
        queueRear = 99;
        count = 0;
        arrayQueue = (T[]) new Object[100];
    }

    //Determines whether queue is empty or not
    public boolean isEmpty() {
        return count == 0;
    }

    //Counts number of elements
    public int count() {
        return count;
    }

    //Returns (but does not remove) object at front of queue
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        else {
            return arrayQueue[queueFront];
        }
    }

    //Removes T object from front of queue
    public void dequeue() {
        if (!isEmpty()) {
            count--;
            arrayQueue[queueFront] = null;
            queueFront = (queueFront + 1) % arrayQueue.length;
        }
    }

    //Adds T object to queue
    public void enqueue(T item) {
        if(count != arrayQueue.length) {
            count++;
            queueRear = (queueRear + 1) % arrayQueue.length;
            arrayQueue[queueRear] = item;
        }
    }
}