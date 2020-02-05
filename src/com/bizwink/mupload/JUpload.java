package com.bizwink.mupload;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.*;

// Referenced classes of package JUpload:
//            Configurator, ProxyConfig, HelpWindow, AboutWindow, 
//            myAddListener, myRemoveListener, myUploadListener, startup, 
//            StatPanel, LogoPanel

public class JUpload extends Applet
    implements ActionListener, MouseListener
{

    protected JPanel controlPanel;
    protected JPanel statuspanel;
    protected StatPanel statpanel;
    DefaultListModel tModel;
    JList list;
    String host;
    startup applet;
    protected JButton add;
    protected JButton remove;
    protected JButton upload;
    protected JPanel buttonPanel;
    protected JPanel infoPanel;
    protected JScrollPane listScroller;
    protected LogoPanel myLogoPanel;
    protected PopupMenu popup;
    protected JSplitPane jsp;

    public JUpload(startup applet)
    {
        host = null;
        Configurator.readConfiguration(applet);
        ProxyConfig.readConfiguration();
        this.applet = applet;
        init();
        start();
    }

    public Applet getStartupApplet()
    {
        return applet;
    }

    public void actionPerformed(ActionEvent e)
    {
        debug(e.toString());
        if(e.getActionCommand().equalsIgnoreCase("recurse_on"))
            Configurator.changeProperty("useRecursivePaths", "true");
        if(e.getActionCommand().equalsIgnoreCase("recurse_off"))
            Configurator.changeProperty("useRecursivePaths", "false");
        if(e.getActionCommand().equalsIgnoreCase("help_applet_tag"))
            new HelpWindow(Configurator.helpPage());
        if(e.getActionCommand().equalsIgnoreCase("help_about"))
            new AboutWindow();
    }

    public void debug(String s)
    {
        if(Configurator.getDebug())
            System.out.println(s);
    }

    public void init()
    {
        debug("JUpload() init()");
        applet.setBackground(Configurator.getBackgroundColor());
        applet.getAppletContext().showStatus(getAppletInfo());
        host = applet.getCodeBase().getHost();
        debug("JUpload() init() setting up actionURL");
        debug("JUpload() init() creating layout");
        debug("JUpload() init() creating defaultlistmodel");
        tModel = new DefaultListModel();
        list = new JList(tModel);
        list.setSelectionMode(1);
        list.setVisibleRowCount(-1);
        debug("JUpload() init() creating scroller");
        listScroller = new JScrollPane(list);
        debug("JUpload() init() creating buttonpanel");
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        createPopupMenu();
        controlPanel.add(popup);
        controlPanel.addMouseListener(this);
        controlPanel.setBackground(Configurator.getBackgroundColor());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 1));
        buttonPanel.setBackground(Configurator.getBackgroundColor());
        add = new JButton(Configurator.getLabelAdd());
        remove = new JButton(Configurator.getLabelRemove());
        upload = new JButton(Configurator.getLabelUpload());
        add.setToolTipText(Configurator.getAddToolTip());
        remove.setToolTipText(Configurator.getRemoveToolTip());
        upload.setToolTipText(Configurator.getUploadToolTip());
        debug("JUpload() init() creating listeners and adding to button");
        add.addActionListener(new myAddListener(this, tModel));
        remove.addActionListener(new myRemoveListener(this, tModel, list));
        upload.addActionListener(new myUploadListener(this, tModel, applet.context));
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(upload);
        add.setAlignmentX(0.5F);
        remove.setAlignmentX(0.5F);
        upload.setAlignmentX(0.5F);
        controlPanel.add(buttonPanel, "North");
        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.setBackground(Configurator.getBackgroundColor());
        statpanel = new StatPanel();
        statpanel.setBackground(Configurator.getBackgroundColor());
        statpanel.updateColor();
        statpanel.setAlignmentX(0.5F);
        statuspanel = new JPanel();
        statuspanel.setBackground(Configurator.getBackgroundColor());
        statuspanel.setAlignmentX(0.5F);
        infoPanel.add(statpanel, "North");
        infoPanel.add(statuspanel, "Center");
        controlPanel.add(infoPanel, "Center");
        myLogoPanel = new LogoPanel(applet);
        myLogoPanel.init();
        myLogoPanel.setBackground(Configurator.getBackgroundColor());
        myLogoPanel.setAlignmentX(0.5F);
        controlPanel.add(myLogoPanel, "South");
        jsp = new JSplitPane();
        jsp.setPreferredSize(new Dimension(applet.getWidth(), applet.getHeight()));
        jsp.setDividerSize(2);
        jsp.setDividerLocation(0.75D);
        applet.add(jsp);
        jsp.add(listScroller, "left");
        jsp.add(controlPanel, "right");
        applet.validate();
        applet.invalidate();
        controlPanel.invalidate();
        listScroller.invalidate();
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.isPopupTrigger())
            debug(e.toString());
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        if(e.isPopupTrigger())
            debug(e.toString());
    }

    public void mouseReleased(MouseEvent e)
    {
        if(e.isPopupTrigger())
        {
            debug(e.toString());
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void start()
    {
        debug("JUpload() start() running from host " + host);
    }

    public void stop()
    {
        debug("JUpload() stop(). ");
        debug("   stop() removing listScroller and buttonpanel");
        controlPanel.remove(myLogoPanel);
        applet.remove(listScroller);
        applet.remove(controlPanel);
        listScroller = null;
        controlPanel = null;
        myLogoPanel = null;
    }

    private void createPopupMenu()
    {
        popup = new PopupMenu("JUpload Settings");
        MenuItem mi = new MenuItem("About JUpload...");
        mi.setActionCommand("help_about");
        mi.addActionListener(this);
        popup.add(mi);
        popup.addSeparator();
        mi = new MenuItem("Help - APPLET Tag");
        mi.setActionCommand("help_applet_tag");
        mi.addActionListener(this);
        popup.add(mi);
    }
}
