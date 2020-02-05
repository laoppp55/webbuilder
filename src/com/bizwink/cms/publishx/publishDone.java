package com.bizwink.cms.publishx;

class publishDone {

    /**
     * The number of SpiderWorker object
     * threads that are currently working
     * on something.
     */
    private int activeThreads = 0;

    /**
     * This boolean keeps track of if
     * the very first thread has started
     * or not. This prevents this object
     * from falsely reporting that the spider
     * is done, just because the first thread
     * has not yet started.
     */
    private boolean started = false;
    /**
     * This method can be called to block
     * the current thread until the spider
     * is done.
     */

    synchronized public void waitDone()
    {
        try {
            while ( activeThreads>0 ) {
                wait();
            }
        } catch ( InterruptedException e ) {
        }
    }
    /**
     * Called to wait for the first thread to
     * start. Once this method returns the
     * spidering process has begun.
     */

    synchronized public void waitBegin()
    {
        try {
            while ( !started ) {
                wait();
            }
        } catch ( InterruptedException e ) {
        }
    }


    /**
     * Called by a SpiderWorker object
     * to indicate that it has begun
     * working on a workload.
     */
    synchronized public void workerBegin()
    {
        activeThreads++;
        started = true;
        notify();
    }

    /**
     * Called by a SpiderWorker object to
     * indicate that it has completed a
     * workload.
     */
    synchronized public void workerEnd()
    {
        activeThreads--;
        notify();
    }

    /**
     * Called to reset this object to
     * its initial state.
     */
    synchronized public void reset()
    {
        activeThreads = 0;
    }
}
