package com.bizwink.mupload;

import java.io.File;
import java.util.StringTokenizer;
import javax.swing.filechooser.FileFilter;

// Referenced classes of package JUpload:
//            Configurator, Utils

public class CustomFileFilter extends FileFilter
{

    public CustomFileFilter()
    {
    }

    public String getDescription()
    {
        return Configurator.getCustomFileFilterDescription();
    }

    public boolean accept(File f)
    {
        if(f.isDirectory())
            return true;
        String extension = Utils.getExtension(f);
        boolean status = false;
        if(extension != null)
        {
            for(StringTokenizer st = new StringTokenizer(Configurator.getCustomFileFilterExtensions(), "/:,; \\"); st.hasMoreTokens();)
            {
                String ext = st.nextToken();
                if(extension.equalsIgnoreCase(ext))
                    status = true;
            }

        }
        return status;
    }
}
