package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadNaming.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to name threads. The Thread class provides
 * a method that allows developers to attach a name to the thread object and
 * a method that allows developers to retrieve the name. The system does not
 * use this name (string) for any specific purpose, though the name is printed
 * out by the default implementation of the toString() method of the thread.
 *
 * Using the thread name to store information is not too benificial. We could
 * just as easily have added an instance variable to the Thread class (if
 * we're threading by inheritance) or to the Runnable type class (if we're
 * threading by interfaces) and achieved the same results.
 * -----------------------------------------------------------------------------
 */

public class ThreadNaming implements Runnable {

    private int currentThread = 0;
    private int countDown     = 5;

    /**
     * Constructs a ThreadJoin object that will executed in a separate
     * thread.
     * @param curThread An integer used to tell the running thread which
     *                  calculation to work on.
     */
    public ThreadNaming(int curThread) {
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
        Thread th1 = new Thread(new ThreadNaming(1));
        th1.setName("Thread 1");
        th1.start();

        // Create a second thread of control and name it "Thread 2".
        Thread th2 = new Thread(new ThreadNaming(2));
        th2.setName("Thread 2");
        th2.start();


        System.out.println();
        System.out.println("Both threads have been started....\n");
        System.out.println("Now lets get their names using the getNames() method...\n");

        System.out.println("    - First Thread Name  : " + th1.getName());
        System.out.println("    - Second Thread Name : " + th2.getName());

        System.out.println();
        System.out.println("Now lets get their names using the default toString() method...\n");

        System.out.println("    - First Thread Name  : " + th1);
        System.out.println("    - Second Thread Name : " + th2);


        System.out.println();
        System.out.println("Will now wait until both threads are completed...\n");
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
