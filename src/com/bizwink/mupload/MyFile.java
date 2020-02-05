package com.bizwink.mupload;

import java.io.File;
import java.net.URI;

public class MyFile extends File
{

    private File currentDirectory;
    private File thisFile;

    public MyFile(String pathname)
    {
        super(pathname);
    }

    public MyFile(URI uri)
    {
        super(uri);
    }

    public MyFile(File parent, String child)
    {
        super(parent, child);
    }

    public MyFile(String parent, String child)
    {
        super(parent, child);
    }

    public void setCurrentDirectory(File file)
    {
        currentDirectory = file;
    }

    public File getCurrentDirectory()
    {
        return currentDirectory;
    }

    public String getRelativeFilename()
    {
        String fullPath = getAbsolutePath();
        String basePath = currentDirectory.toString();
        int basePathLength = basePath.length();
        String relPath = fullPath.substring(basePathLength + 1);
        return relPath;
    }
}
