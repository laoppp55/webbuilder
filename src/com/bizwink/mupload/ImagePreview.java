package com.bizwink.mupload;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintStream;
import javax.swing.*;

// Referenced classes of package JUpload:
//            Configurator

public class ImagePreview extends JComponent
    implements PropertyChangeListener
{

    File file;
    ImageIcon thumbnail;

    public ImagePreview(JFileChooser fc)
    {
        file = null;
        thumbnail = null;
        debug("ImagePreview() created");
        setPreferredSize(new Dimension(100, 50));
        if(fc != null)
        {
            debug("ImagePreview() adding ImagePreview listener to filechooser");
            fc.addPropertyChangeListener(this);
        }
    }

    public void loadImage()
    {
        debug("ImagePreview() Loading image file " + file);
        if(file == null)
            return;
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if(tmpIcon.getIconWidth() > 90)
            thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, 1));
        else
            thumbnail = tmpIcon;
    }

    public void paintComponent(Graphics g)
    {
        debug("ImagePreview() paintComponent()");
        if(thumbnail == null)
        {
            debug("ImagePreview() paintComponent() loading image");
            loadImage();
        }
        if(thumbnail != null)
        {
            debug("ImagePreview() paintComponent() calculating thumbnail");
            int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
            int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;
            if(y < 0)
                y = 0;
            if(x < 5)
                x = 5;
            thumbnail.paintIcon(this, g, x, y);
        }
    }

    public void propertyChange(PropertyChangeEvent e)
    {
        debug("ImagePreview() propertyChange() event = " + e);
        String prop = e.getPropertyName();
        if(prop.equals("SelectedFileChangedProperty"))
        {
            file = (File)e.getNewValue();
            if(isShowing())
            {
                loadImage();
                repaint();
            }
        }
    }

    private void debug(String string)
    {
        if(Configurator.getDebug())
            System.out.println(string);
    }
}
