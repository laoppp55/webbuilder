package com.bizwink.mupload;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.*;

// Referenced classes of package JUpload:
//            JUpload, StatPanel, Configurator

public class myRemoveListener
    implements ActionListener
{

    DefaultListModel tModel;
    JList list;
    JUpload parent;

    myRemoveListener(JUpload parent, DefaultListModel tModel, JList list)
    {
        debug("myRemoveListener()");
        this.parent = parent;
        this.tModel = tModel;
        this.list = list;
    }

    public void actionPerformed(ActionEvent e)
    {
        debug("myRemoveListener() actionPerformed() e=" + e);
        int selected[] = list.getSelectedIndices();
        if(selected.length == 0)
        {
            JOptionPane.showMessageDialog(parent, "No files selected.\nSelect the files you wish to remove from the upload queue", "No files selected", 0);
            return;
        }
        for(int i = selected.length - 1; i >= 0; i--)
        {
            debug(" remove() i=" + i + " selected=" + selected[i] + " count=" + selected.length);
            tModel.remove(selected[i]);
        }

        parent.statpanel.updateModel(tModel);
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }
}
