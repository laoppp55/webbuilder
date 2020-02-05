package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadCurrentThread.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to use the currentThread() method. First,
 * lets examine the definition of the method:
 *
 *      static Thread currentThread();
 *          Gets the Thread object that represents the current thread of
 *          execution. The method is static and may be called through the
 *          Thread class name.
 *
 * This is a static method of the Thread class, and it simply returns a Thread
 * object (reference) that represents the current thread; the current thread
 * is the thread that called the currentThread() method. The object returned
 * is the same Thread object first created for the current thread.
 *
 * But why is this method important? The Thread object for the current thread
 * may not be saved anywhere, and even if it is, it may not be accessible to
 * the called method.
 *
 * Notice something very important about this example. The getName() method is
 * a method of the Thread class. Since we are not inheriting from the Thread
 * class, this method is not available within the run() method. This helps
 * drive the point home that we are after is the name of the thread that has
 * called the run() method, which is probably not from the ThreadCurrentThread
 * thread.
 *
 * -----------------------------------------------------------------------------
 */

public class ThreadCurrentThread implements Runnable {

    private int currentThread = 0;
    private int countDown     = 5;

    /**
     * Constructs a ThreadJoin object that will executed in a separate
     * thread.
     * @param curThread An integer used to tell the running thread which
     *                  calculation to work on.
     */
    public ThreadCurrentThread(int curThread) {
        this.currentThread = curThread;
        System.out.println("\nConstructing thread  (" + curThread + ")...\n");
    }

    /**
     * The callback method that will be called by the start() and run() methods
     * of the Thread class.
     */
    public void run() {

        System.out.println("[ Starting New Thread ] : The name of this thread is " + Thread.currentThread().getName());
        System.out.println("====================================================================");
        System.out.println();

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

        // Start the first thread of control and name it "Thread 1".
        Thread th1 = new Thread(new ThreadCurrentThread(1));
        th1.setName("Thread 1");
        th1.start();

        // Lets take a little break...
        Thread.sleep(1000);

        // Create a second thread of control and name it "Thread 2".
        Thread th2 = new Thread(new ThreadCurrentThread(2));
        th2.setName("Thread 2");
        th2.start();

        System.out.println();
        System.out.println("Both threads have been started....");
        System.out.println("Will now wait until both threads are completed...");
        System.out.println();
        th1.join();
        th2.join();

        System.out.println("All working threads are complete...\n");

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
