package com.bizwink.images;

// -----------------------------------------------------------------------------
// ThreadJoin2.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Another example of how to join two threads together. For a detailed example
 * of how the join() method works, see the ThreadJoin class in this section of
 * examples.
 * -----------------------------------------------------------------------------
 */

public class ThreadJoin2 implements Runnable {

    Thread t;

    /**
     * No-arg constructor
     */
    public ThreadJoin2() {
        t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    /**
     * Overriden run() method. This will perform some lon
     */
    public void run() {
        String tmpStr = "";
        for (int i=0; i<20000; i++) {
            tmpStr += Integer.toString(i);
        }
    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        ThreadJoin2 jt = new ThreadJoin2();
        try {
            System.out.println("Before join...");
            jt.t.join();
            System.out.println("After join...");
        } catch (InterruptedException ie) {
            System.out.println("Main exception: " + ie);
            ie.printStackTrace();
        }


    }

}
