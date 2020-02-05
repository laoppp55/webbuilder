package com.bizwink.mupload;

import java.io.File;
import java.net.URL;

public interface HTTPRequest
{

    public abstract void setActionURL(URL url);

    public abstract void addFile(File file);

    public abstract void setLastRequest(boolean flag);

    public abstract boolean isFinished();

    public abstract boolean isRunning();

    public abstract void start();
}
