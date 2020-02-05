package com.bizwink.mupload;

import java.io.PrintStream;
import java.util.EventObject;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Referenced classes of package JUpload:
//            MyFile, Configurator, JUpload

public class MyFileListSelectionListener
    implements ListSelectionListener
{

    private DefaultListModel tModel;
    private JUpload upload;

    public MyFileListSelectionListener(JUpload upload, DefaultListModel tModel)
    {
        this.upload = upload;
        this.tModel = tModel;
    }

    public void valueChanged(ListSelectionEvent e)
    {
        debug("MyFileListSelectionListener() e=" + e);
        if(!e.getValueIsAdjusting())
        {
            JList liste = (JList)e.getSource();
            int index = liste.getSelectedIndex();
            if(index <= tModel.size())
            {
                MyFile myFile = (MyFile)tModel.getElementAt(index);
                debug("Selected file: " + myFile);
            }
        }
    }

    private void debug(String string)
    {
        if(Configurator.getDebug())
            System.out.println(string);
    }
}
