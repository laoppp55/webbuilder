package com.bizwink.mupload;

import java.awt.*;
import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.*;

// Referenced classes of package JUpload:
//            Configurator

public class StatPanel extends JPanel
{

    private JLabel statBytesLabel;
    private JLabel statBytesValue;
    private JLabel statFilesLabel;
    private JLabel statFilesValue;

    StatPanel()
    {
        debug("StatPanel() initialized");
        setLayout(new GridLayout(2, 2));
        createFields();
        setVisible(true);
        doLayout();
    }

    public void updateColor()
    {
        statFilesLabel.setBackground(Configurator.getBackgroundColor());
        statFilesValue.setBackground(Configurator.getBackgroundColor());
        statBytesLabel.setBackground(Configurator.getBackgroundColor());
        statBytesValue.setBackground(Configurator.getBackgroundColor());
    }

    public void updateModel(DefaultListModel tModel)
    {
        statFilesLabel.setVisible(true);
        statBytesLabel.setVisible(true);
        long totalSize = 0L;
        for(int i = 0; i < tModel.size(); i++)
        {
            File f = (File)tModel.getElementAt(i);
            totalSize += f.length();
        }

        setFiles(tModel.size());
        setBytes(totalSize);
        getParent().validate();
    }

    private void setBytes(long i)
    {
        double size = i;
        String description = "bytes";
        String output = null;
        if(size > 1024D)
        {
            size /= 1024D;
            description = "KB";
            if(size > 1.0D)
                description = "KB";
        }
        if(size > 1024D)
        {
            size /= 1024D;
            description = "MB";
            if(size > 1.0D)
                description = "MB";
        }
        DecimalFormat myFormatter = new DecimalFormat("###.##");
        output = myFormatter.format(size);
        statBytesValue.setText(output + " " + description);
    }

    private void setFiles(long i)
    {
        statFilesValue.setText(Long.toString(i));
    }

    private void createFields()
    {
        statFilesLabel = new JLabel(Configurator.getLabelFiles());
        statFilesValue = new JLabel();
        statBytesLabel = new JLabel(Configurator.getLabelBytes());
        statBytesValue = new JLabel();
        statFilesLabel.setVisible(false);
        statBytesLabel.setVisible(false);
        updateColor();
        add(statFilesLabel);
        add(statFilesValue);
        add(statBytesLabel);
        add(statBytesValue);
    }

    private void debug(String string)
    {
        if(Configurator.getDebug())
            System.out.println(string);
    }
}
