package com.bizwink.mupload;

import java.awt.*;
import javax.swing.*;

public class HelpWindow extends JFrame
{

    HelpWindow(String content)
    {
        setTitle("JUpload Help Page");
        setSize(400, 300);
        JTextArea tf = new JTextArea(content);
        tf.setFont(new Font("Courier", 0, 12));
        JScrollPane scroll = new JScrollPane(tf);
        getContentPane().add(scroll);
        setVisible(true);
        show();
    }
}
