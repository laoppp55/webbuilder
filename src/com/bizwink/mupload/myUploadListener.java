package com.bizwink.mupload;

import java.applet.AppletContext;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintStream;
import javax.swing.*;

// Referenced classes of package JUpload:
//            UploadManager, JUpload, Configurator, HTTPPutRequest, 
//            HTTPPostRequest, HTTPRequest, StatPanel, MyFile

public class myUploadListener
    implements ActionListener
{

    AppletContext context;
    DefaultListModel tModel;
    JUpload parent;
    String newline;
    int fileSizes;
    private HTTPRequest httprequest;
    private UploadManager uploadmanager;

    myUploadListener(JUpload parent, DefaultListModel tModel, AppletContext context)
    {
        newline = System.getProperty("line.separator");
        fileSizes = 0;
        debug("myUploadListener() created");
        this.parent = parent;
        this.tModel = tModel;
        this.context = context;
        uploadmanager = new UploadManager();
        uploadmanager.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        debug("myUploadListener() user pressed Uploadbutton e=" + e);
        parent.add.setEnabled(false);
        parent.remove.setEnabled(false);
        parent.upload.setEnabled(false);
        if(!checkMaximumUploadSize())
        {
            parent.add.setEnabled(true);
            parent.remove.setEnabled(true);
            parent.upload.setEnabled(true);
            return;
        }
        int i = 0;
        do
        {
            parent.doLayout();
            long iTotalRequestSize = 0L;
            long iMaxTotalRequestSize = Configurator.getMaxTotalRequestSize();
            debug("myUploadListener() creating new HTTPPostRequest");
            try
            {
                debug("myUploadListener() creating new HTTPRequest");
                if(Configurator.getUsePutMethod())
                    httprequest = new HTTPPutRequest(parent, tModel);
                else
                    httprequest = new HTTPPostRequest(parent, tModel);
            }
            catch(Exception e2)
            {
                debug("***PROBLEM CREATING HTTPPOSTREQUEST***");
                e2.printStackTrace();
            }
            httprequest.setActionURL(Configurator.getActionURL());
            debug("myUploadListener() checking iTotalRequestSize<iMaxTotalRequestSize");
            debug("                   checking " + iTotalRequestSize + "<" + iMaxTotalRequestSize);
            while(iTotalRequestSize < iMaxTotalRequestSize || iMaxTotalRequestSize == -1L) 
            {
                File f = (File)tModel.getElementAt(i);
                iTotalRequestSize += f.length();
                debug(" total request size: " + iTotalRequestSize + " maxTotalRequest is " + iMaxTotalRequestSize);
                if(iTotalRequestSize < iMaxTotalRequestSize || iMaxTotalRequestSize == -1L)
                {
                    debug("myUploadListener() moving file from filequeue to uploadqueue file=" + f);
                    httprequest.addFile(f);
                }
                debug("myUploadListener() increasing i=" + i + " tmodel.size=" + tModel.size());
                i++;
                if(tModel.size() <= i)
                {
                    debug("myUploadListener() end of queue reached");
                    httprequest.setLastRequest(true);
                    break;
                }
            }
            debug("myUploadListener() actionPerformed() starting upload thread");
            parent.statpanel.updateModel(tModel);
            uploadmanager.addThread(httprequest);
            try
            {
                Thread.sleep(2000L);
            }
            catch(InterruptedException e1)
            {
                e1.printStackTrace();
            }
        } while(i < tModel.size());
        debug("myUploadListener() finished with filequeue. reenabling buttons");
        parent.add.setEnabled(true);
        parent.remove.setEnabled(true);
        parent.upload.setEnabled(true);
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }

    private boolean checkMaximumUploadSize()
    {
        long totalSize = 0L;
        for(int i = 0; i < tModel.getSize(); i++)
        {
            MyFile f = (MyFile)tModel.getElementAt(i);
            totalSize += f.length();
            if(totalSize > Configurator.getMaxFreeSpaceOnServer() && Configurator.getMaxFreeSpaceOnServer() != -1L)
            {
                String strMessage = Configurator.getMaxFreeSpaceOnServerWarning() + "\n" + f.getName() + "\n" + f.length() + " bytes";
                String strTitle = Configurator.getMaxFreeSpaceOnServerTitle();
                JOptionPane.showMessageDialog(parent, strMessage, strTitle, 0);
                parent.list.setSelectedIndex(i);
                return false;
            }
        }

        return true;
    }
}
