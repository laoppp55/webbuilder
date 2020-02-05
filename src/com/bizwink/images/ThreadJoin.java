package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadJoin.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how two threads communicate with each other. If
 * you think about it, the isAlive() method can be thought of as a crude form
 * of communication. When using the isAlive() method, we are waiting for
 * information: the indication that the other thread has completed. Take for
 * example the starting of several threads to do a long calculation, we are then
 * free to do other tasks. Assume that sometime later we have completed all
 * other secondary tasks and need to deal with the results of the long
 * calculation: we need to wait until the calculation(s) are finished before
 * continuing on to process the results.
 *
 * As seen in an example before, we could accomplish this task by using the
 * looping isAlive() technique we've just discussed, but there are other
 * techniques in the Java API that are more suited to this task. This act of
 * waiting is called a "thread join". We are "joining" with the thread that
 * was forked off from us earlier when we started the thread.
 *
 * Keep in mind that there are three versions of the join method:
 *
 *      void join()
 *          Waits for the completion of the specified thread. By definition,
 *          join() returns as soon as the thread is considered "not alive". This
 *          includes the case in which the join() method is called on a thread
 *          that has not been started.
 *
 *      void join(long timeout)
 *          Waits for the completion of the specified thread, but no longer
 *          than the timeout specified in milliseconds. This timeout value is
 *          subject to rounding based on the capabilities of the underlying
 *          platform.
 *
 *      void join(long timeout, int nanoseconds)
 *          Waits for the completion of the specified thread, but no longer
 *          than a timeout specified in milliseconds and nanoseconds. This
 *          timeout value is subject to rounding based on the capabilities
 *          of the underlying platform.
 *
 * When the join() method is called, the current thread will simply wait until
 * the thread it is joining with is no longer alive. This can be caused by
 * the thread not having been started, or having been stopped by yet another
 * thread, or by the completion of the thread itself. The join() method
 * basically accomplishes the same task as the combination of the sleep() and
 * isAlive() methods. However, by using the join() method, we accomplish
 * the same task with a single method call. We also have better control over the
 * timeout interval, and we don't have to waste CPU cycles by polling.
 * -----------------------------------------------------------------------------
 */

public class ThreadJoin implements Runnable {

    private static int threadOneValue    = 0;
    private static int threadTwoValue    = 0;

    private int currentThread = 0;
    private int countDown     = 5;

    /**
     * Constructs a ThreadJoin object that will executed in a separate
     * thread.
     * @param curThread An integer used to tell the running thread which
     *                  calculation to work on.
     */
    public ThreadJoin(int curThread) {
        this.currentThread = curThread;
        System.out.println("\nConstructing thread  (" + curThread + ")...\n");
    }

    /**
     * The callback method that will be called by the start() and run() methods
     * of the Thread class.
     */
    public void run() {

        if (currentThread == 1) {

            while (true) {

                System.out.println("  - Thread " + currentThread + "  ( Current Countdown = " + countDown + " )");

                for (int j = 0; j < 300000000; j++) {
                    // This is a test...
                }

                if (--countDown == 0) {
                    System.out.println("\nEnding thread " + currentThread + "...\n");
                    break;
                }
            }

            threadOneValue = 25;
            return;
        }

        if (currentThread == 2) {


            while (true) {

                System.out.println("  - Thread " + currentThread + "  ( Current Countdown = " + countDown + " )");

                for (int j = 0; j < 100000000; j++) {
                    // This is a test...
                }

                if (--countDown == 0) {
                    System.out.println("\nEnding thread " + currentThread + "...\n");
                    break;
                }
            }

            threadTwoValue = threadOneValue * 4;
            return;
        }

    }


    /**
     * Static method that starts a thread of control then continues to check
     * the status (is it alive) of the thread.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    private static void doThreadTest()
        throws java.lang.InterruptedException {

        // Start the first thread of control immediatley. It will perform a long
        // calculation and update the proper static variable for this class.
        Thread th1 = new Thread(new ThreadJoin(1));
        th1.start();

        // Create a second thread of control that will need perform work on
        // the results of the first thread on control. In this case, we ONLY
        // create the thread object and keep it in the "New Thread" state. We
        // can only allow this thread to start when the first thread is
        // complete.
        Thread th2 = new Thread(new ThreadJoin(2));

        // The following method call puts the current thread in a wait state
        // until the first thread is complete. (In this case, the "th1" object).
        System.out.println("\nWill now wait until the first thread is complete...\n");
        th1.join();

        // At this point, the first thread is complete and has updated the
        // proper varaibles. We can now allow the second thread to start and
        // work with the results of the first thread.
        System.out.println("Now that the first thread is complete, starting second thread...\n");
        th2.start();

        // We now need to put the current thread in a wait state until the
        // second thread of control is complete. If we didn't put a join here,
        // the value for the second thread (threadTwoValue) would probably print
        // a zero (0) in the System.out.println() statement that follows.
        System.out.println("Will now wait until the second thread is complete...\n");
        th2.join();

        // At this point, we are assured that all threads have completed and
        // it is safe to print out the values.
        System.out.println("All working threads are complete, now time to print out there values...\n");
        System.out.println("    Thread One Value: " + threadOneValue);
        System.out.println("    Thread Two Value: " + threadTwoValue);
        System.out.println();

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    public static void main(String[] args)
        throws java.lang.InterruptedException {

        System.out.println("\n<< MAIN METHOD (Begin) >>");

        doThreadTest();

        System.out.println("<< MAIN METHOD (End) >>\n");

    }

}
