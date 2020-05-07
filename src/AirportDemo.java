import java.util.Random;
import java.text.DecimalFormat;

public class AirportDemo {

    public static void main(String[] args) {

        final double LANDING_TIME = 3;
        final double TAKE_OFF_TIME = 2;
        final double LANDING_RATE  = 10;
        final double TAKE_OFF_RATE = 10;
        final int ITERATIONS = 1440;
        
        int landQueueTime = 0;
        int planesLanded = 0;
        int takeOffQueueTime = 0;
        int planesTakenOff = 0;

        Random generator = new Random(System.currentTimeMillis());
        DecimalFormat form = new DecimalFormat("#0.000000");

        //Two ArrayQueues, one for landing and one for takeoff
        ArrayQueue<Integer> landingQueue = new ArrayQueue<>();
        ArrayQueue<Integer> takeOffQueue = new ArrayQueue<>();

        //Simulation
        for(int i = 0; i < ITERATIONS; i++) {
            //If 1st random number is less than landingRate/60, landing arrival occurred
            // + is added to landing queue
            if(generator.nextDouble() < (LANDING_RATE /60)) {
                landingQueue.enqueue(i);
            }
            //If 2nd random number is less than takeOffRate/60, takeoff arrival occurred
            //+ is added to takeoff queue.
            if(generator.nextDouble() < (TAKE_OFF_RATE /60)) {
                takeOffQueue.enqueue(i);
            }

            //Landing queue is prioritized
            while(!landingQueue.isEmpty()) {
                //Check whether the runway is free
                while(!landingQueue.isEmpty() && queueFree(landingQueue.peek(), i, LANDING_RATE)) {
                    landingQueue.dequeue();
                }

                //Check whether the landing queue is nonempty
                if(!landingQueue.isEmpty()) {
                    //Allow the first airplane to land
                    int temp = landingQueue.peek();
                    landingQueue.dequeue();
                    landQueueTime += (i - temp);
                    planesLanded++;

                    //Add to queues
                    int j;
                    for(j = i; j < LANDING_TIME + i && j < ITERATIONS; j++) {
                        if(generator.nextDouble() < (LANDING_RATE /60)) {
                            landingQueue.enqueue(j);
                        }
                        if(generator.nextDouble() < (TAKE_OFF_RATE /60)) {
                            takeOffQueue.enqueue(j);
                        }
                    }
                    i = j;

                    //Do not exceed # of iterations, move onto take-off queue
                    if(i >= ITERATIONS) {
                        break;
                    }
                }
            }

            //Consider take-off queue when landing queue is empty
            if(!takeOffQueue.isEmpty()) {
                //Allow first airplane to take off
                int toTakeOff = takeOffQueue.peek();
                takeOffQueue.dequeue();
                takeOffQueueTime += (i - toTakeOff);
                planesTakenOff++;

                //Add to queues
                int k;
                for(k = i; k < TAKE_OFF_TIME + i && k < ITERATIONS; k++) {
                    if(generator.nextDouble() < (LANDING_RATE /60)) {
                        landingQueue.enqueue(k);
                    }
                    if(generator.nextDouble() < (TAKE_OFF_RATE /60)) {
                        takeOffQueue.enqueue(k);
                    }
                }
                i = k;
            }
        }

        double pl = planesLanded;
        double pto = planesTakenOff;

        System.out.println("Average landing queue length: " + form.format(pl/ITERATIONS));
        System.out.println("Average take off queue length: " + form.format(pto/ITERATIONS));
        System.out.println("Average landing queue time: " + form.format(landQueueTime / pl) + " min");
        System.out.println("Average take off queue time: " + form.format(takeOffQueueTime / pto) + " min");
    }

    //Checks if queue is free
    public static boolean queueFree(int val, int it, double rate) {
        return (val - it > rate);
    }
}