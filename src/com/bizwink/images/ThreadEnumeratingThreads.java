package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadEnumeratingThreads.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to obtain a list of all threads in a
 * program:
 *
 *      static int enumerate(Thread threadArray[])
 *          Gets all the thread objects of the program and stores the result
 *          into the thread array. The value returned is the number of thread
 *          objects stored in the array. The method is static and may be called
 *          through the Thread class name.
 *
 *      static int activeCount()
 *          Returns the number of threads in the program. The method is static
 *          and may be called through the Tread class name.
 *
 * This list is retrieved with the enumerate() method. The developer simply
 * needs to create a Thread array and pass it as a parameter. The enumerate()
 * method stores the thread references into the array and returns the number
 * of thread objects stored; this number is the size of the array parameter
 * or the number of threads in teh program, whichever is small.
 *
 * In order to size the array for the enumerate() method, we need to determine
 * the number of threads in the program by using the activeCount() method.
 *
 * Keep in mind that the array returned is a list of ALL threads in the
 * program. This will include at least [main()] as well as any threads you
 * may have created. It is important to note that we say the list is "all the
 * threads in the program" rather than "all the threads in the virtual machine".
 * That's because at the level of the Thread class, the enumerate() method shows
 * us only the threads that our program has created, plus (possibly) the main
 * and GUI threads of an application or applet that the virtual machine has
 * created for us. It will not show us other threads of the virtual machine
 * (e.g., the garbage collection thread), and in an applet, it will not show
 * us other threads in other applets.
 *
 * -----------------------------------------------------------------------------
 */

public class ThreadEnumeratingThreads implements Runnable {

    private int currentThread = 0;
    private int countDown     = 5;

    /**
     * Constructs a ThreadJoin object that will executed in a separate
     * thread.
     * @param curThread An integer used to tell the running thread which
     *                  calculation to work on.
     */
    public ThreadEnumeratingThreads(int curThread) {
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
        Thread th1 = new Thread(new ThreadEnumeratingThreads(1));
        th1.setName("Thread 1");
        th1.start();

        // Create a second thread of control and name it "Thread 2".
        Thread th2 = new Thread(new ThreadEnumeratingThreads(2));
        th2.setName("Thread 2");
        th2.start();


        System.out.println();
        System.out.println("Both threads have been started....\n");
        System.out.println("Now lets get a list of all threads within the program...\n");

        Thread[] tList = new Thread[Thread.activeCount()];

        int numThreads = Thread.enumerate(tList);

        for (int i = 0; i < numThreads; i++) {
            System.out.println("  - Thread List [" + i + "] = " + tList[i].getName());
        }

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
