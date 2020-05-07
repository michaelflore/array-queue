public interface ArrayQueueADT<T> {
    //Determines whether queue is empty or not
    boolean isEmpty();
    //Counts number of elements
    int count();
    //Returns (but does not remove) object at front of queue
    T peek();
    //Removes T object from front of queue
    void dequeue();
    //Adds T object to queue
    void enqueue(T item);
}
