package com.bizwink.mupload;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import java.security.AccessControlException;
import javax.swing.*;

// Referenced classes of package JUpload:
//            JUpload, Configurator, ImageFileFilter, CustomFileFilter, 
//            ImagePreview, StatPanel, MyFile, startup

public class myAddListener
    implements ActionListener
{

    DefaultListModel tModel;
    JUpload parent;
    private File currentDirectory;
    private startup applet;

    myAddListener(JUpload parent, DefaultListModel tModel)
    {
        debug("myAddListener()");
        this.parent = parent;
        applet = this.parent.applet;
        this.tModel = tModel;
    }

    public void actionPerformed(ActionEvent e)
    {
        debug("myAddListener() actionPerformed() e=" + e);
        try
        {
            JFileChooser jfc = new JFileChooser();
            jfc.setMultiSelectionEnabled(true);
            jfc.setFileSelectionMode(2);
            if(Configurator.getImageFileFilter())
            {
                debug("myAddListener() trying to set image file filter");
                ImageFileFilter iff = new ImageFileFilter();
                jfc.setFileFilter(iff);
            }
            if(Configurator.getCustomFileFilter())
            {
                debug("myAddListener() trying to set custom file filter");
                CustomFileFilter cff = new CustomFileFilter();
                jfc.setFileFilter(cff);
            }
            if(!Configurator.getDefaultAddDirectory().equalsIgnoreCase(""))
            {
                debug("myAddListener() trying to set new default directory.");
                File defDir = new File(Configurator.getDefaultAddDirectory());
                jfc.setCurrentDirectory(defDir);
            }
            if(Configurator.getHideShowAll())
            {
                debug("myAddListener() switching off Show-All filefilter");
                jfc.setAcceptAllFileFilterUsed(false);
            }
            if(Configurator.getShowPicturePreview())
            {
                debug("myAddListener() starting up the ImagePreview feature");
                jfc.setAccessory(new ImagePreview(jfc));
            }
            int rVal = jfc.showDialog(parent, Configurator.getAddWindowTitle());
            currentDirectory = jfc.getCurrentDirectory();
            debug("myAddListener() back from file chooser. current directory = " + currentDirectory);
            if(rVal == 0)
                walkFileTree(jfc.getSelectedFiles());
            if(rVal == 1)
                debug("myAddListener() You pressed cancel");
        }
        catch(AccessControlException ace)
        {
            JOptionPane.showMessageDialog(parent, "You clicked NO at the permission request dialog.\nPlease restart your browser, reload the page\nand press YES", "No permission", 0);
            ace.printStackTrace();
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
        debug("myAddListener() Updating file queue");
        parent.statpanel.updateModel(tModel);
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }

    private void walkFileTree(File files[])
    {
        for(int i = 0; i < files.length; i++)
            if(files[i].isDirectory())
            {
                debug("myAddListener() walkFileTree(): Adding Directory: " + files[i].getPath());
                File f_recurse[] = files[i].listFiles();
                walkFileTree(f_recurse);
            } else
            {
                debug("myAddListener() walkFileTree(): Adding File " + files[i].getName() + " size=" + files[i].length());
                MyFile newFile = new MyFile(files[i].toString());
                newFile.setCurrentDirectory(currentDirectory);
                if(newFile.exists())
                {
                    if(newFile.length() > Configurator.getMaxFreeSpaceOnServer() && Configurator.getMaxFreeSpaceOnServer() != -1L)
                    {
                        String strMessage = Configurator.getMaxFreeSpaceOnServerWarning() + "\n" + newFile.getName() + "\n" + newFile.length() + " bytes";
                        String strTitle = Configurator.getMaxFreeSpaceOnServerTitle();
                        JOptionPane.showMessageDialog(applet, strMessage, strTitle, 0);
                    } else
                    if(newFile.length() > Configurator.getMaxTotalRequestSize() && Configurator.getMaxTotalRequestSize() != -1L)
                    {
                        String strMessage = Configurator.getMaxTotalRequestSizeWarning() + "\n" + newFile.getName() + "\n" + newFile.length() + " bytes";
                        String strTitle = Configurator.getMaxTotalRequestSizeTitle();
                        JOptionPane.showMessageDialog(parent, strMessage, strTitle, 0);
                    } else
                    {
                        tModel.addElement(newFile);
                    }
                } else
                {
                    debug("myAddListener() file does not exist:" + newFile);
                }
            }

    }
}
