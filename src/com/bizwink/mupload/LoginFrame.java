package com.bizwink.mupload;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class LoginFrame extends JDialog
{

    private String strPassword;
    private String strUsername;

    LoginFrame()
    {
        strUsername = JOptionPane.showInputDialog("Username?");
        strPassword = JOptionPane.showInputDialog("Password?");
    }

    protected String getPassword()
    {
        return strPassword;
    }

    protected String getUsername()
    {
        return strUsername;
    }
}
