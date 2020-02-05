package com.bizwink.mupload;

import java.io.File;
import javax.swing.filechooser.FileFilter;

// Referenced classes of package JUpload:
//            Utils

public class ImageFileFilter extends FileFilter
{

    public ImageFileFilter()
    {
    }

    public String getDescription()
    {
        return "Image files";
    }

    public boolean accept(File f)
    {
        if(f.isDirectory())
            return true;
        String extension = Utils.getExtension(f);
        if(extension != null)
            return extension.equals("gif") || extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png") || extension.equals("bmp");
        else
            return false;
    }
}
