package com.bizwink.mupload;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.*;

// Referenced classes of package JUpload:
//            Configurator

public class UploadStatus extends JPanel
{

    private JLabel filenamebar;
    private JProgressBar progressbar;
    private long currentTime;
    private long startTime;

    UploadStatus()
    {
        filenamebar = new JLabel();
        progressbar = new JProgressBar();
        startTime = System.currentTimeMillis();
        setBackground(Configurator.getBackgroundColor());
        progressbar.setBackground(Configurator.getBackgroundColor());
        filenamebar.setBackground(Configurator.getBackgroundColor());
        setLayout(new GridLayout(2, 1));
        add(progressbar);
        add(filenamebar);
    }

    public void setFilename(String filename)
    {
        filenamebar.setText(filename);
        revalidate();
    }

    public void setValue(int i, int max)
    {
        progressbar.setMaximum(max);
        progressbar.setStringPainted(true);
        progressbar.setValue(i);
        double prozent = ((double)i / (double)max) * 100D;
        String strProzent = Math.round(prozent) + "%";
        long duration = (System.currentTimeMillis() - startTime) / 1000L;
        if(duration > 0L)
        {
            int speed = (int)((long)i / duration);
            speed /= 1024;
            String strSpeed = speed + " kb/s";
            long lETA = Math.round((100D / prozent) * (double)duration) - duration;
            String strETA;
            if(lETA > 60L)
            {
                lETA = Math.round(lETA / 60L);
                strETA = lETA + "min";
            } else
            {
                strETA = lETA + "s";
            }
            progressbar.setString(strProzent + " - " + strSpeed + " - " + strETA);
        } else
        {
            progressbar.setString(strProzent);
        }
        validate();
    }

    public void close()
    {
        setVisible(false);
    }
}
