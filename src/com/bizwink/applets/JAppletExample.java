package com.bizwink.applets;

import java.applet.Applet;
import java.awt.*;
import java.io.*;

public class JAppletExample extends Applet {
    FileDialog openDialog;
    Button go,stop;
    TextArea t1;

    public void init() {
        setFont(new Font("宋体",Font.BOLD,18));
        this.go = new Button("Go");
        this.stop = new Button();
        this.t1 = new TextArea(10,30);
        add(go);
        add(stop);
        stop.setLabel("Stop");
        add(t1);
    }

    public boolean handleEvent(Event evt) {
        if (evt.target == go) {
            String sourceFile="c:\\1.txt";
            FileInputStream source = null;
            try {
                source = new FileInputStream(sourceFile);
                InputStreamReader inputStreamReader = new InputStreamReader(source);
                BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = inputBufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputBufferedReader.close();
                inputStreamReader.close();
                t1.setText(stringBuilder.toString());
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

        return false;
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
