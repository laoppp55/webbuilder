package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadCountdownImpRunnable.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to test the Java Thread facilities by
 * issuing an array of threads that perform looping operations. The loop was
 * used to utilize as much CPU as possible with very little I/O.
 *
 * Threading Concepts
 * ------------------
 * While it is true that the start() method eventually calls the run() method,
 * it does so in another thread of control; this new thread, after dealing
 * with some initialization details, then calls the run() method. After the
 * run() method completes, this new thread also deals with the details of
 * terminating the thread. Keep in mind that the start() method of the original
 * thread returns immediately. Thus, the run() method will be executing in the
 * newly formed thread at about the same time the start() method returns in the
 * first thread.
 *
 * In this example, we create a thread of control by implementing the Runnable
 * interface. This is the second of two methods for creating threads. As simple
 * as it was to create another thread of control by extending the Thread class,
 * there is one problem with this technique. It's caused by the fact that Java
 * classes can inherit their behaviour only from a single class, which means
 * that inheritance itself can be considered a scarce resource, and is
 * therefore expensive to the developer.
 *
 * Instead of inheriting from the Thread class, developers can implement
 * the Runnable interface (java.lang.Runnable), which is defined as follows:
 *
 *      public interface Runnable {
 *          public abstract void run();
 *      }
 *
 * The Runnable interface contains only one method; the run() method. The
 * Thread class actually implements the Runnable interface; hence, when you
 * inherit from the Thread class, your subclass also implements the Runnable
 * interface. However, in this case we want to implement the Runnable interface
 * without actually inheriting from the Thread class. This is achieved by simply
 * substituting the phrase "implements Runnable" for the phrase
 * "extends Thread"; no other changes are necessary to Step 1. Remember that
 * when extending the Thread class to create a thread of control, extending
 * the Thread class was the first step. Step 2 in this process is a bit more
 * complicated. Since our object is no longer a Thread object, an instance of
 * the Thread class is still needed, but it will be instantiated with a
 * reference to our Runnable object (in this case ThreadCountdownImpRunnable).
 *
 * As in the "extending Thread" example, we have to create an instance of
 * the ThreadCountdownImpRunnable class. However, in this new version, we also
 * need to create an actual thread object. We create this object by passing
 * our Runnable object reference to the constructor of the Thread using
 * a new constructor of the Thread class:
 *
 *      Thread(Runnable target)
 *              [ Constructs a new thread object associated with the given
 *                Runnable object ]
 *
 * The new Thread object's start() method is called to begin execution of the
 * new thread of control.
 *
 * The reason we need to pass the Runnable object to the thread object's
 * constructor is that the thread must have some way to get to the run() method
 * we want the thread to execute. Since we are no longer overriding the run()
 * method of the Thread class, the default run() method of the Thread class
 * is executed; this default run() method looks like this:
 *
 *      public void run() {
 *          if (target != null) {
 *              target.run();
 *          }
 *      }
 *
 * Here, target is the Runnable object we passed to the thread's constructor.
 * So the thread begins execution with the run() method of the Thread class,
 * which immediately calls the run() method of our Runnable object.
 *
 * -----------------------------------------------------------------------------
 */

public class ThreadCountdownImpRunnable implements Runnable {

    private int countDown = 5;
    private static int threadCount = 0;
    private int threadNumber = ++threadCount;

    /**
     * Constructs a ThreadCountdownImpRunnable object that will executed in a
     * separate thread.
     */
    public ThreadCountdownImpRunnable() {
        System.out.println("\nStarting thread number => " + threadCount + "\n");
    }

    /**
     * A callback method that will be called by the start() method of the Thread
     * class. Keep in mind though, that this run() method WILL NOT override the
     * run() method within the Thread class. This method will be called by the
     * run() method of the Thread class as follows:
     *
     * Thread.start() -> Thread.run() -> YourRunnableObject.run()
     *
     * This method is very similar to the main() method of a typical
     * class. A new thread begins execution with the run() method in the same
     * way a program begins execution with the main() method.
     * <p>
     * While the main() method receives its arguments from the argv parameter
     * (which is typically set from the command line), the newly created thread
     * must receive its arguments programmatically from the originating thread.
     * Hence, parameters can be passed in via the constructor, static instance
     * variables, or any other technique designed by the developer.
     */
    public void run() {
        while(true) {

            System.out.println(
                "  - Thread number " +
                threadNumber +
                " ( Current Countdown = " + countDown + " )");

            for (int j = 0; j < 300000000; j++) {
                // This is a test...
            }

            if (--countDown == 0) {
                System.out.println(
                    "\nEnding thread number => " + threadNumber + "\n");
                return;
            }
        }
    }


    /**
     * Starts all five threads. This method will perform a sleep operation
     * before starting each thread to allow the output to reflect how each
     * thread operates.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    private static void doThreadCountdown()
        throws java.lang.InterruptedException {

        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            Runnable ot = new ThreadCountdownImpRunnable();
            Thread th = new Thread(ot);
            th.start();
        }
        System.out.println("\n<< All threads have now been started!!! >>\n");
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException Thrown from the Thread class.
     */
    public static void main(String[] args)
        throws java.lang.InterruptedException {

        System.out.println("\n<< MAIN METHOD (Begin) >>");

        doThreadCountdown();

        System.out.println("<< MAIN METHOD (End) >>\n");

    }

}
