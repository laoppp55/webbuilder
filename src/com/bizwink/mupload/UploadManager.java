package com.bizwink.mupload;

import java.io.PrintStream;
import java.util.*;

// Referenced classes of package JUpload:
//            HTTPRequest, Configurator

public class UploadManager extends Thread
{

    private Vector threadPool;
    private boolean runningThread;

    UploadManager()
    {
        threadPool = new Vector();
        runningThread = false;
        setPriority(1);
    }

    public void addThread(HTTPRequest httprequest)
    {
        debug("UploadManager() adding thread " + httprequest);
        threadPool.add(httprequest);
        debug("UploadManager() thread added. there are " + threadPool.size() + " threads.");
    }

    public void run()
    {
        debug("UploadManager() run()/start()");
        do
        {
            for(Iterator iter = threadPool.iterator(); iter.hasNext();)
            {
                HTTPRequest element = (HTTPRequest)iter.next();
                if(element.isFinished())
                {
                    threadPool.remove(element);
                    debug("UploadManager() removing finishes thread...");
                    break;
                }
            }

            runningThread = false;
            for(Iterator iter = threadPool.iterator(); iter.hasNext();)
            {
                HTTPRequest element = (HTTPRequest)iter.next();
                if(element.isRunning())
                    runningThread = true;
            }

            if(threadPool.size() > 0)
                if(!runningThread)
                {
                    debug("UploadManager() no thread is running... starting one");
                    HTTPRequest thread = (HTTPRequest)threadPool.firstElement();
                    thread.start();
                } else
                {
                    debug("UploadManager() there is a thread running. waiting");
                }
            try
            {
                Thread.sleep(2000L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        } while(true);
    }

    private void debug(String string)
    {
        if(Configurator.getDebug())
            System.out.println(string);
    }
}
