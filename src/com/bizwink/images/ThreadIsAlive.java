package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadIsAlive.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to communicate to a thread of control. In
 * this example, we simply want to know if the thread is alive or not. For this
 * we will use the isAlive() method of the Thread class. The isAlive method
 * is generally used to test if a thread has started.
 *
 * Right after creating a Thread object, the thread object is in the
 * "New Thread" state. When a thread is a New Thread, it is merely an "empty"
 * Thread object; no system resources have been allocated for it yet. When a
 * thread is in this state, you can only start the thread. Calling any method
 * besides start when a thread is in this state makes no sense and causes
 * an IllegalThreadStateException. (Infact, the runtime system throws an
 * IllegalThreadStateException any time a method is called on a thread and
 * that thread's state does not allow for that method call.)
 *
 * The start method then creates the system resources necessary to run the
 * thread, schedules the thread to run, and calls the thread's run() method.
 *
 * DEFINITION OF THE isAlive() METHOD:
 * ===================================
 *      The API for the Thread class includes a method called isAlive. The
 *      isAlive method returns true if the thread has been started and not
 *      stopped. If the isAlive method returns false, you know that the thread
 *      either is a New Thread (start has not been called yet) or is Dead.
 *      If the isAlive method returns true, you know that the thread is either
 *      Runnable or Not Runnable. You cannot differentiate between a New Thread
 *      or a Dead thread. Nor can you differentiate between a Runnable thread
 *      and a Not Runnable thread.
 *
 * NOTE:
 * =====
 *      There is a period of time after you call the start() method before the
 *      virtual machine can actually start the thread. Similarly, when a thread
 *      returns from its run() method, there is a period of time before the
 *      virtual machine can clean up after the thread; and if you se the stop()
 *      method, there is an even greater period of time before the virtual
 *      machine can clean up after the thread.
 *
 *      This delay occurs because it takes time to start or terminate a thread;
 *      therefore, there is a transitional period from when a thread is running
 *      to when a thread is not running. After the run() method returns, there
 *      is a short period of time before the thread stops. If we want to know
 *      if the start() method of the thread has been called - or, more usefully,
 *      if the thread has terminated - we must use the isAlive() method. This
 *      method is used to find out if a thread has actually been started and has
 *      not yet been terminated:
 *
 *          boolean isAlive()
 *              Determines if a thread is considered alive. By definition, a
 *              thread is considered alive from sometime BEFORE a thread is
 *              actually started to sometime AFTER a thread is actually stopped.
 * -----------------------------------------------------------------------------
 */

public class ThreadIsAlive implements Runnable {

    private int countDown = 5;

    /**
     * Constructs a ThreadIsAlive object that will executed in a separate
     * thread.
     */
    public ThreadIsAlive() {
        System.out.println("\nThread constructor...\n");
    }

    /**
     * The callback method that will be called by the start() and run() methods
     * of the Thread class.
     */
    public void run() {

        while(true) {

            System.out.println("  - Thread ( Current Countdown = " + countDown + " )");

            for (int j = 0; j < 300000000; j++) {
                // This is a test...
            }

            if (--countDown == 0) {
                System.out.println("\nEnding thread...\n");
                return;
            }
        }
    }


    /**
     * Static method that starts a thread of control then continues to check
     * the status (is it alive) of the thread.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    private static void doThreadTest()
        throws java.lang.InterruptedException {

        int checkCount = 0;

        Thread th = new Thread(new ThreadIsAlive());
        th.start();

        while (th.isAlive()) {
            Thread.sleep(500);
            System.out.println("Thread is still alive. Count = " + ++checkCount);
        }

        System.out.println("\n<< Finished running and checking thread!!! >>\n");
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
