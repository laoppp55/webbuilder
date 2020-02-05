package com.bizwink.applets;

import javax.swing.*;
import netscape.javascript.JSObject;
import netscape.javascript.JSException;
import netscape.javascript.JSUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class SwingApplet extends JApplet {
    public String text;

    public void init() {
        String version = System.getProperty("java.version");
        String vendor = System.getProperty("java.vendor");
        text = version + ", " + vendor;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getContentPane().add(new JLabel(text));
                validate();
            }
        });
    }

    public void setText(String s)
    {
        this.text = s;
    }

    public void calljs(String s)
    {
        //获取JavaScript窗口句柄，引用当前文档窗口
        //JSObject win=JSObject.getWindow(this);
        //访问JavaScript document对象
        //JSObject doc=(JSObject)win.getMember("document");
        //访问JavaScript form对象
        //JSObject form=(JSObject)doc.getMember("pasteform");
        //访问JavaScript text对象
        //JSObject textField=(JSObject)form.getMember("cbdata");
        //获取文本区的值
        //text=(String) textField.getMember("value");

        //调用JavaScript的alert（）方法
        //win.eval("alert(\"This alert comes from Java!" + s + text + "\")");

        //调用JavaScript的myFunction（message）方法
        //参数用数组的形势表示。
        //win.call("myFunction", new Object[]{"Hello"});
    }
}
