package com.bizwink.mupload;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JComponent;
import javax.swing.JPanel;

// Referenced classes of package JUpload:
//            Configurator, startup

public class LogoPanel extends JPanel
    implements ImageObserver
{

    Image myLogo;
    URL imageURL;
    startup applet;
    private String imageFileName;

    LogoPanel(startup applet)
    {
        myLogo = null;
        imageURL = null;
        this.applet = null;
        debug("LogoPanel() constructor");
        this.applet = applet;
    }

    public void debug(String s1)
    {
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        debug("LogoPanel() imageUpdate()");
        debug(" img=" + img);
        debug(" infoflags=" + infoflags + " (should be " + 32);
        debug(" x=" + x + " y=" + y + " width=" + width + " height=" + height);
        debug("LogoPanel() imageUpdate() dimension = " + myLogo.getWidth(this) + "x" + myLogo.getHeight(this));
        setSize(myLogo.getWidth(this), myLogo.getHeight(this));
        setPreferredSize(new Dimension(myLogo.getWidth(this), myLogo.getHeight(this)));
        setVisible(true);
        invalidate();
        applet.validate();
        if((infoflags & 0xc0) != 0)
        {
            debug("LogoPanel() image loading is not done yet: " + infoflags);
            return false;
        }
        if(infoflags == 32)
            updateInfos();
        return true;
    }

    public void init()
    {
        debug("LogoPanel() init()");
        myLogo = null;
        imageURL = null;
        try
        {
            imageFileName = Configurator.getImageURL();
            if(imageFileName != null)
            {
                if(!imageFileName.equalsIgnoreCase(""))
                {
                    imageURL = new URL(applet.getDocumentBase(), imageFileName);
                    myLogo = applet.getImage(imageURL);
                    debug("LogoPanel() init() imageURL = " + imageURL);
                    debug("LogoPanel() init() myLogo = " + myLogo);
                    setSize(myLogo.getWidth(this), myLogo.getHeight(this));
                    setVisible(true);
                    doLayout();
                    validate();
                    revalidate();
                    invalidate();
                } else
                {
                    setVisible(false);
                }
            } else
            {
                setVisible(false);
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        debug("LogoPanel() paint() graphics=" + g);
        if(myLogo != null)
        {
            debug("LogoPanel() paint() logo " + myLogo);
            g.drawImage(myLogo, 0, 0, this);
        } else
        {
            debug("LogoPanel() paint() logo is null");
            return;
        }
        debug("LogoPanel() paint() checking size of image");
    }

    private void updateInfos()
    {
        debug("LogoPanel() updateInfos()");
        Dimension dimSize = new Dimension(myLogo.getWidth(this), myLogo.getHeight(this));
        setMinimumSize(dimSize);
        setMaximumSize(dimSize);
        setSize(dimSize);
        setPreferredSize(dimSize);
        setVisible(true);
        invalidate();
        repaint();
        applet.validate();
        applet.repaint();
    }
}
